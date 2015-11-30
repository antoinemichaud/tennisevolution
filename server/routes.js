var JSX = require('node-jsx').install(),
  React = require('react'),
  _ = require('lodash'),
  Promise = require('bluebird'),
  request = require('request'),
  requestIp = require('request-ip'),
  APP = require('./app');

var requestAsync = Promise.promisify(request);

var registeredClients = [];

var turn = 1;
var stackPoints = [1000, 500, 100, 50, 25, 13, 1];
var competitorsWithTries = {};
var stepQuestions = ['displayScore', 'displayAlternativeScore', 'displayFrenchScore', 'noAvantageScoring', 'withLifeScoring', 'sets/displayScore', 'servicesScoring'];
var stepGenerators = ['generateGame', 'generateGame', 'generateGame', 'generateNoAvantageGame', 'generateGame', 'generateSet', 'generateServicesSet'];

var availablePoints = {
  1: _.clone(stackPoints),
  2: _.clone(stackPoints),
  3: _.clone(stackPoints),
  4: _.clone(stackPoints),
  5: _.clone(stackPoints),
  6: _.clone(stackPoints),
  7: _.clone(stackPoints),
  8: _.clone(stackPoints)
};

var scoreBoard = {};

function sendQuestion(response, remoteAddress) {
  var stepQuestion = stepQuestions[turn - 1];
  var stepGenerator = stepGenerators[turn - 1];
  console.log("stepQuestion: " + stepQuestion);
  console.log("stepGenerator: " + stepGenerator);

  return requestAsync('http://localhost:8081/generateTest/' + stepGenerator)
    .spread(function (questionsQueryResponse, questionsQueryBody) {
      return JSON.parse(questionsQueryBody);
    })
    .map(function (questionAsObject) {
      var questionAsQueryParam;
      if (turn < 7) {
        questionAsQueryParam = '?player1Name=' + questionAsObject.player1GameScore.playerName + '&player1Score=' + questionAsObject.player1GameScore.playerScore +
          '&player2Name=' + questionAsObject.player2GameScore.playerName + '&player2Score=' + questionAsObject.player2GameScore.playerScore;
      } else {
        questionAsQueryParam =
          '?scores=' + questionAsObject;

      }
      console.log('query to serveur: ' + 'http://' + remoteAddress + ':8080/' + stepQuestion + questionAsQueryParam);

      return Promise.props({
        question: questionAsObject,
        candidateResult: requestAsync('http://' + remoteAddress + ':8080/' + stepQuestion + questionAsQueryParam)
        //candidateResult: requestAsync('http://' + remoteAddress + ':8083/' + stepQuestion + questionAsQueryParam)
          .spread(function (candidateResultResponse, candidateResultBody) {
            return candidateResultBody;
          }),
        referenceResult: requestAsync('http://localhost:8080/' + stepQuestion + questionAsQueryParam)
          .spread(function (referenceResultResponse, referenceResultBody) {
            return referenceResultBody;
          })
      });
    })
    .map(function (result) {
      console.log("candidate response : " + result.candidateResult);
      console.log("reference response :" + result.referenceResult);
      return result.candidateResult === result.referenceResult;
    })
    .reduce(function (aggregation, comparisonResult) {
      return aggregation && comparisonResult;
    }, true)
    ;
}

function playerHasNotScoreForTurnYet(currentUser) {
  return typeof scoreBoard[currentUser.name].details.scoresByTurn[turn - 1] === 'undefined';
}

function nextScoredPoints() {
  if (availablePoints[turn].length > 1) {
    return availablePoints[turn].shift();
  } else {
    return availablePoints[turn];
  }
}

function playerOvertried(remoteAddress) {
  return competitorsWithTries[remoteAddress] < 0;
}

function playerCanStillPlayForThisTurn(currentUser, remoteAddress) {
  return playerHasNotScoreForTurnYet(currentUser) && !playerOvertried(remoteAddress);
}

function decrementTrialsLeft(remoteAddress) {
  if (typeof competitorsWithTries[remoteAddress] == 'undefined') {
    competitorsWithTries[remoteAddress] = 3;
  }
  competitorsWithTries[remoteAddress] = competitorsWithTries[remoteAddress] - 1;
  console.log("competitorsWithTries[remoteAddress] : " + competitorsWithTries[remoteAddress]);
}

function initScoresOfPlayerIfNeeded(currentUser) {
  if (typeof scoreBoard[currentUser.name] == 'undefined') {
    scoreBoard[currentUser.name] = {details: {scoresByTurn: []}, total: 0};
  }
}

module.exports = function (io) {
  return {
    compare: function (req, res) {
      var responseBody = {success: false};
      console.log("availablePoints: " + availablePoints[turn]);
      var remoteAddress = requestIp.getClientIp(req) != '::1' ? requestIp.getClientIp(req) : "127.0.0.1";
      console.log('remote address : ' + remoteAddress);
      sendQuestion(res, remoteAddress)
        .then(function (success) {
          // Initialization
          var scoredPoints = 0;
          var currentUser = _.find(registeredClients, function (registeredClient) {
            return registeredClient.ip === remoteAddress;
          });

          if (!currentUser) {
            res.status(400).send("Veuillez vous enregistrer avant de participer");
            return;
          }

          initScoresOfPlayerIfNeeded(currentUser);
          decrementTrialsLeft(remoteAddress);

          if (success) {
            responseBody.success = true;
            if (playerCanStillPlayForThisTurn(currentUser, remoteAddress)) {
              scoredPoints = nextScoredPoints();
              scoreBoard[currentUser.name].details.scoresByTurn[turn - 1] = {score: scoredPoints};
              scoreBoard[currentUser.name].total = scoredPoints + scoreBoard[currentUser.name].total;
            }
          }

          responseBody.scoreInfo = scoreBoard[currentUser.name];
          responseBody.trialNumberLeft = competitorsWithTries[remoteAddress];
          io.emit('refreshScores', scoreBoard);
          res.send(responseBody);
          console.log('scoreBoard: ' + scoreBoard);
        });
    },

    turn: function (req, res) {
      console.log(req.body);
      turn = req.body.turn;
      competitorsWithTries = {};
      console.log ('turn : ' + turn);
      io.emit('turn', turn);
      res.send('OK');
    },

    rotatePlayers: function (req, res) {
      var rotatedRegisteredPlayers = [];
      for (var i = 0; i < registeredClients.length; i++) {
        var playerNameToCopyIndex = (i + 1) % registeredClients.length;
        var playerNameToCopy = registeredClients[playerNameToCopyIndex].name;
        var mixedUpPlayer = {name: playerNameToCopy, ip: registeredClients[i].ip};
        rotatedRegisteredPlayers.push(mixedUpPlayer);
      }

      registeredClients = rotatedRegisteredPlayers;
      console.log("New array of registeredClients: " + JSON.stringify(registeredClients));
      io.emit('client', registeredClients);
      res.send('OK')
    },

    register: function (name, clientIp) {
      console.log(name, clientIp);
      if (!_.contains(_.pluck(registeredClients, 'ip'), clientIp)) {
        var newUser = {name: name, ip: clientIp};
        registeredClients.push(newUser);
      } else {
        var userToChangeName = _.find(registeredClients, function(client) {
          return client.ip === clientIp;
        });

        userToChangeName.name = name;
      }
      io.emit('client', registeredClients);
    },

    index: function (req, res) {
      var markup = React.renderToString(APP());
      res.send(markup);
    },

    init: function () {
      var self = this;
      io.on('connection', function (socket) {
        var clientIp = socket.handshake.address != '::1' ? socket.handshake.address : '127.0.0.1';
        if (registeredClients.length > 0) {
          socket.emit('initClients', registeredClients);
          socket.emit('refreshScores', scoreBoard);
        }

        socket.on('register', function (name) {
          self.register(name, clientIp);
        });
      });
    }
  };
};
