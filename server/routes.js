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
var competitorsWithPoints = [];
var stepQuestions = ['displayScore', 'displayAlternativeScore', 'displayFrenchScore', 'noAvantageScoring', 'servicesScoring', 'withLifeScoring', 'sets/displayScore'];
var stepGenerators = ['generateGame', 'generateGame', 'generateGame', 'generateGame', 'generateGame', 'generateGame', 'generateSet'];

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
  console.log(stepQuestion);
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

module.exports = function (io) {
  return {
    compare: function (req, res) {
      var remoteAddress = requestIp.getClientIp(req) != '::1' ? requestIp.getClientIp(req) : "127.0.0.1";
      console.log('remote address : ' + remoteAddress);
      sendQuestion(res, remoteAddress).then(function (success) {
        var scoredPoints;
        var currentUser = _.find(registeredClients, function (registeredClient) {
          return registeredClient.ip === remoteAddress;
        });
        if (_.contains(competitorsWithPoints, remoteAddress)) {
          res.send(scoreBoard[currentUser.name]);
          return;
        }
        if (success) {
          if (availablePoints[turn].length > 1) {
            scoredPoints = availablePoints[turn].shift();
          } else {
            scoredPoints = availablePoints[turn];
          }

          if (scoreBoard[currentUser.name]) {
            if (!_.contains(_.pluck(scoreBoard[currentUser.name].details, 'turn'), turn)) {
              scoreBoard[currentUser.name].details.push({turn: turn, score: scoredPoints});
              scoreBoard[currentUser.name].total = scoredPoints + scoreBoard[currentUser.name].total;
            }
          } else {
            scoreBoard[currentUser.name] = {details: [], total: 0};
            scoreBoard[currentUser.name].details.push({turn: turn, score: scoredPoints});
            scoreBoard[currentUser.name].total = scoredPoints;
          }
        }
        io.emit('refreshScores', scoreBoard);
        console.log('result will be sent : ');
        console.log(currentUser);
        console.log(currentUser.name);
        console.log(scoreBoard);
        console.log(scoreBoard[currentUser.name]);
        res.send(scoreBoard[currentUser.name]);
      });

    },

    turn: function (req, res) {
      turn = req.body.turn;
      console.log ('turn : ' + turn);
      io.emit('turn', turn);
      res.send('OK');
    },

    register: function (name, clientIp) {
      console.log(name, clientIp);
      if (!_.contains(_.pluck(registeredClients, 'ip'), clientIp)) {
        var newUser = {name: name, ip: clientIp};
        registeredClients.push(newUser);
        io.emit('client', newUser);
      }
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
