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

var dontPingAnymore = [];
var timerRef = {};

var turn = 1;
var stackPoints = [1000, 500, 100, 50, 25, 13, 1];

var competitorsWithTries = {};
var stepQuestions =
  [
    [{candidate: 'displayScore', ref: 'classic/displayScore'}, {
      candidate: 'displayScore/alternative',
      ref: 'classic/displayScore/alternative'
    }],
  //[{candidate: 'displayScore', ref: 'classic/displayScore'},
  //  {candidate: 'displayScore/alternative', ref: 'classic/displayScore/alternative'},
  //  {candidate: 'displayScore/french', ref: 'classic/displayScore/french'},
  //  {candidate: 'displayScore/german', ref: 'classic/displayScore/german'}],
    [{candidate: 'displayScore', ref: 'withoutAdvantage/displayScore'},
      {candidate: 'displayScore/alternative', ref: 'withoutAdvantage/displayScore/alternative'},
      {candidate: 'displayScore/french', ref: 'withoutAdvantage/displayScore/french'},
      {candidate: 'displayScore/german', ref: 'withoutAdvantage/displayScore/german'}],
    [{candidate: 'displayScore', ref: 'withLifeScoring/displayScore'},
      {candidate: 'displayScore/alternative', ref: 'withLifeScoring/displayScore/alternative'},
      {candidate: 'displayScore/french', ref: 'withLifeScoring/displayScore/french'},
      {candidate: 'displayScore/german', ref: 'withLifeScoring/displayScore/german'}],
    [{candidate: 'sets/displayScore', ref: 'sets/displayScore'}],
    //[{candidate: 'servicesScoring', ref: 'servicesScoring'}]
    []
  ];
var stepGenerators = ['generateGame', 'generateNoAvantageGame', 'generateGame', 'generateSet', 'generateServicesSet'];

var rotateScoringRepartition = {
  source: 0.5,
  destination: 0.5
};
var rotateStep = "sets/displayScore";
var rotatedRegisteredPlayers = {};


var availablePoints = {
  1: [20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1],
  2: [55, 34, 21, 13, 8, 5, 3, 2, 1],
  3: [55, 34, 21, 13, 8, 5, 3, 2, 1],
  4: [55, 34, 21, 13, 8, 5, 3, 2, 1],
  //5: [55, 34, 21, 13, 8, 5, 3, 2, 1],
  5: [0]
};

var scoreBoard = {};

function sendQuestion(response, remoteAddress) {
  var errors = [];
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
        if (turn < 4) {
          questionAsQueryParam = '?player1Name=' + questionAsObject.player1GameScore.playerName + '&player1Score=' + questionAsObject.player1GameScore.playerScore +
              '&player2Name=' + questionAsObject.player2GameScore.playerName + '&player2Score=' + questionAsObject.player2GameScore.playerScore;
        } else {
          questionAsQueryParam =
              '?scores=' + questionAsObject;

        }

        var responsesAgainstRef = [];
        stepQuestion.forEach(function(stepQuestionElt) {
          var candidateUrl = 'http://' + remoteAddress + ':8080/' + stepQuestionElt.candidate + questionAsQueryParam;
          var refUrl = 'http://localhost:8083/' + stepQuestionElt.ref + questionAsQueryParam;
          responsesAgainstRef.push(Promise.props({
            candidateQuestion: candidateUrl,
            refQuestion: refUrl,
            candidateResult: requestAsync({
              url: candidateUrl,
              timeout: 3000
            })
            //candidateResult: requestAsync({
            //  url: 'http://' + remoteAddress + ':8080/' + stepQuestionElt.candidate + questionAsQueryParam,
            //  timeout: 3000
            //})
              .spread(function (candidateResultResponse, candidateResultBody) {
                return candidateResultBody;
              })
              .catch(function (exception) {
                throw new Exception(exception);
              }),
            referenceResult: requestAsync({
              //url: 'http://localhost:8080/' + stepQuestionElt.ref + questionAsQueryParam,
              url: refUrl,
              timeout: 3000
            })
              .spread(function (referenceResultResponse, referenceResultBody) {
                return referenceResultBody;
              })
          }));
        });

        return responsesAgainstRef;
      })
      .then(_.flatten)
      .map(function (result) {
        //console.log('query to server: ' + result.question);
        //console.log("candidate response: " + result.candidateResult + " reference response: " + result.referenceResult);
        var comparisonResult = result.candidateResult === result.referenceResult;
        if (!comparisonResult) {
          errors.push("expected " + result.referenceResult + " got " + result.candidateResult);
          console.log('query to candidate server: ' + result.candidateQuestion);
          console.log('query to ref server: ' + result.refQuestion);
          console.log("candidate response: " + result.candidateResult + " reference response: " + result.referenceResult);
        }
        return comparisonResult;
      })
      .reduce(function (aggregation, comparisonResult) {
        return {errors : errors, success : aggregation && comparisonResult};
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
  //var stepQuestion = stepQuestions[turn - 1];
  return turn === 3;
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
      remoteAddress = cleanRemoteAddress(remoteAddress);
      console.log('remote address : ' + remoteAddress);
      sendQuestion(res, remoteAddress)
          .then(function (result) {
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

            if (result.success) {
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
            responseBody.errors = result.errors;
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
      clientIp = cleanRemoteAddress(clientIp);
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
        clientIp = cleanRemoteAddress(clientIp);
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
