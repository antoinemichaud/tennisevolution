var
  _ = require('lodash'),
  Promise = require('bluebird'),
  request = require('request'),
  requestIp = require('request-ip'),
  ping = require('ping')
  ;

var requestAsync = Promise.promisify(request);

var registeredClients =
    [];

var dontPingAnymore = ['456.789.123.1'];
var timerRef = {};

var turn = 1;
var stackPoints = [1000, 500, 100, 50, 25, 13, 1];

var competitorsWithTries = {};
var stepQuestions = [{candidate: 'displayAlternativeScore', ref: 'displayAlternativeScore'}, {candidate: 'noAvantageScoring', ref: 'noAvantageScoring'}, {candidate: 'withLifeScoring', ref: 'withLifeScoring'}, {candidate: 'sets/displayScore', ref: 'sets/displayScore'},
  {candidate: 'servicesScoring', ref: 'servicesScoring'}];
var stepGenerators = ['generateGame', 'generateNoAvantageGame', 'generateGame', 'generateSet', 'generateServicesSet'];

var rotateScoringRepartition = {
  source: 0.5,
  destination: 0.5
};
var rotateStep = "sets/displayScore";
var rotatedRegisteredPlayers = {};


var availablePoints = {
  1: _.clone(stackPoints),
  2: _.clone(stackPoints),
  3: _.clone(stackPoints),
  4: _.clone(stackPoints),
  5: _.clone(stackPoints),
  6: _.clone(stackPoints)
};

var scoreBoard = {};

function sendQuestion(response, remoteAddress) {
  var stepQuestion = stepQuestions[turn - 1].ref;
  var stepGenerator = stepGenerators[turn - 1];
  console.log("stepQuestion: " + stepQuestion);
  console.log("stepGenerator: " + stepGenerator);

  return requestAsync('http://localhost:8081/generateTest/' + stepGenerator)
      .spread(function (questionsQueryResponse, questionsQueryBody) {
        return JSON.parse(questionsQueryBody);
      })
      .map(function (questionAsObject) {
        var questionAsQueryParam;
        if (turn < 4) {
          questionAsQueryParam = '?player1Name=' + questionAsObject.player1GameScore.playerName + '&player1Score=' + questionAsObject.player1GameScore.playerScore +
              '&player2Name=' + questionAsObject.player2GameScore.playerName + '&player2Score=' + questionAsObject.player2GameScore.playerScore;
        } else {
          questionAsQueryParam =
              '?scores=' + questionAsObject;

        }
        console.log('query to server: ' + 'http://' + remoteAddress + ':8080/' + stepQuestion + questionAsQueryParam);

        return Promise.props({
          question: questionAsObject,
          candidateResult: requestAsync({url: 'http://' + remoteAddress + ':8080/' + stepQuestion + questionAsQueryParam, timeout: 3000})
          //candidateResult: requestAsync('http://' + remoteAddress + ':8083/' + stepQuestion + questionAsQueryParam)
              .spread(function (candidateResultResponse, candidateResultBody) {
                return candidateResultBody;
              })
              .catch(function (exception) {
                throw new Exception(exception);
              }),
          referenceResult: requestAsync({url: 'http://localhost:8080/' + stepQuestion + questionAsQueryParam, timeout: 3000})
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
      .catch(function (exception) {
        throw new Exception(exception);
      });
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

function isRotatePlayerStep() {
  var stepQuestion = stepQuestions[turn - 1];
  return stepQuestion.ref === rotateStep;
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

function cleanRemoteAddress(remoteAddress) {
  if (_.contains(remoteAddress, '::ffff:')) {
    return remoteAddress.replace('::ffff:', '');
  } else {
    return remoteAddress;
  }
}
function scoreWithRotation(currentUser) {
  var scoredPoints = nextScoredPoints();

  var destinationName = currentUser.name;
  var sourceName = rotatedRegisteredPlayers[destinationName];

  var destinationScoredPoints = scoredPoints * rotateScoringRepartition.destination;
  var sourceScorePoints = scoredPoints * rotateScoringRepartition.source;

  console.log('rotation : dest ' + destinationName + " - " + destinationScoredPoints);
  console.log('rotation : source ' + sourceName + " - " + sourceScorePoints);

  scoreBoard[destinationName].details.scoresByTurn[turn - 1] = {score: destinationScoredPoints};
  scoreBoard[destinationName].total = destinationScoredPoints + scoreBoard[destinationName].total;

  initScoresOfPlayerIfNeeded(_.find(registeredClients, 'name', sourceName));
  scoreBoard[sourceName].details.bonus = {score: sourceScorePoints};
  scoreBoard[sourceName].total = sourceScorePoints + scoreBoard[sourceName].total;
}

function _rotatePlayers() {
  rotatedRegisteredPlayers = {};
  for (var i = 0; i < registeredClients.length; i++) {
    var indexOfPlayerNameToCopy = (i + 1) % registeredClients.length;
    var playerNameToCopy = registeredClients[indexOfPlayerNameToCopy].name;
    rotatedRegisteredPlayers[playerNameToCopy] = registeredClients[i].name;
  }

  console.log("New array of registeredClients: " + JSON.stringify(registeredClients));
  console.log("New array of rotatedRegisteredPlayers: " + JSON.stringify(rotatedRegisteredPlayers));
}

function _refreshRanking() {
  var totaux = _.pluck(scoreBoard, 'total');
  totaux = _.sortBy(totaux, function (num) {
    return num * -1;
  });
  _.each(scoreBoard, function (user) {
    user.ranking = _.indexOf(totaux, user.total) + 1;
  });
}

function triggerPings() {
  console.log('ping triggered');
  timerRef = setInterval(function () {
    registeredClients.forEach(function (registeredClient) {
      console.log("will try ping");
      if (dontPingAnymore.indexOf(registeredClient.ip) === -1) {
          console.log("will ping");
          ping.promise
            .probe(registeredClient.ip, {timeout: 3})
            .then(function (result) {
              console.log("ping result : " + JSON.stringify(result));
            })
            .catch(function (error) {
              console.log("ping error : " + error);
            });
        }
      }
    )
  }, 2000);
}
module.exports = function (io) {
  return {
    pingClients: function (req, res) {
      triggerPings();
      res.send();
    },

    stopPingClients: function (req, res) {
      clearInterval(timerRef);
      res.send();
    },

    pingThisClientAgain: function (req, res) {
      var clientIp = req.body.clientIp;
      dontPingAnymore = dontPingAnymore.filter(function (elt) {
        return clientIp != elt
      });
      clearInterval(timerRef);
      triggerPings();
      res.send();
    },

    dontPingThisClient: function (req, res) {
      var clientIp = req.body.clientIp;
      dontPingAnymore.push(clientIp);
      clearInterval(timerRef);
      triggerPings();
      res.send();
    },

    compare: function (req, res) {
      var responseBody = {success: false};
      console.log("availablePoints: " + availablePoints[turn]);
      var remoteAddress = requestIp.getClientIp(req) != '::1' ? requestIp.getClientIp(req) : "127.0.0.1";
      console.log('remote address : ' + remoteAddress);
      sendQuestion(res, cleanRemoteAddress(remoteAddress))
          .then(function (success) {
            // Initialization
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
                if (isRotatePlayerStep()) {
                  scoreWithRotation(currentUser);
                } else {
                  var scoredPoints = nextScoredPoints();
                  scoreBoard[currentUser.name].details.scoresByTurn[turn - 1] = {score: scoredPoints};
                  scoreBoard[currentUser.name].total = scoredPoints + scoreBoard[currentUser.name].total;
                }

                _refreshRanking();
              }
            }

            responseBody.scoreInfo = scoreBoard[currentUser.name];
            responseBody.trialNumberLeft = competitorsWithTries[remoteAddress];
            io.emit('refreshScores', scoreBoard);
            res.send(responseBody);
            console.log('scoreBoard: ' + scoreBoard);
            console.log("remoteaddress: " + remoteAddress);

          })
          .catch(function () {
            res.status(500).send('Your server timed out.');
          });
    },

    turn: function (req, res) {
      console.log(req.body);
      turn = req.body.turn;
      competitorsWithTries = {};
      if (isRotatePlayerStep()) {
        _rotatePlayers();
      } else {
        rotatedRegisteredPlayers = {};
      }
      console.log ('turn : ' + turn);
      io.emit('rotatedPlayers', rotatedRegisteredPlayers);
      io.emit('turn', turn);
      res.send('OK');
    },

    changeip: function (req, res) {
      _.each(registeredClients, function (user) {
        if (user.ip === req.body.oldIp) {
          user.ip = req.body.newIp;
          console.log("change ip of " + user.name + " - " + req.body.oldIp + " to " + req.body.newIp);
        }
      });
      io.emit('client', registeredClients);
      res.send('OK');
    },

    register: function (name, clientIp) {
      console.log(name, clientIp);
      if (!_.contains(_.pluck(registeredClients, 'ip'), clientIp) && !_.contains(_.pluck(registeredClients, 'name'), name)) {
        var newUser = {name: name, ip: clientIp};
        registeredClients.push(newUser);
      }
      io.emit('client', registeredClients);
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
