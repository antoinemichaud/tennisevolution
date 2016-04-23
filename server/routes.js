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

var competitorsWithTries = {};
var stepQuestions =
  [
    [
      {candidate: 'displayScore', ref: 'classic/displayScore'}
    ],
    [
      {candidate: 'displayScore', ref: 'classic/displayScore'},
      {candidate: 'displayScore/french', ref: 'classic/displayScore/french'}
    ],
    [
      {candidate: 'displayScore', ref: 'classic/displayScore'},
      {candidate: 'displayScore/french', ref: 'classic/displayScore/french'},
      {candidate: 'displayScore/german', ref: 'classic/displayScore/german'}
    ],
    [
      {candidate: 'displayScore', ref: 'classic/displayScore'},
      {candidate: 'displayScore/albanese', ref: 'classic/displayScore/albanese'},
      {candidate: 'displayScore/azeri', ref: 'classic/displayScore/azeri'},
      {candidate: 'displayScore/bosniac', ref: 'classic/displayScore/bosniac'},
      {candidate: 'displayScore/catalan', ref: 'classic/displayScore/catalan'},
      {candidate: 'displayScore/cebuano', ref: 'classic/displayScore/cebuano'},
      {candidate: 'displayScore/chichewa', ref: 'classic/displayScore/chichewa'},
      {candidate: 'displayScore/creole', ref: 'classic/displayScore/creole'},
      {candidate: 'displayScore/english', ref: 'classic/displayScore/english'},
      {candidate: 'displayScore/german', ref: 'classic/displayScore/german'},
      {candidate: 'displayScore/french', ref: 'classic/displayScore/french'},
      {candidate: 'displayScore/hungarian', ref: 'classic/displayScore/hungarian'}
    ],
    [
      {candidate: 'displayScore', ref: 'withoutAdvantage/displayScore'},
      {candidate: 'displayScore/french', ref: 'withoutAdvantage/displayScore/french'},
      {candidate: 'displayScore/german', ref: 'withoutAdvantage/displayScore/german'}
    ],
    [
      {candidate: 'displayScore', ref: 'withLifeScoring/displayScore'},
      {candidate: 'displayScore/french', ref: 'withLifeScoring/displayScore/french'},
      {candidate: 'displayScore/german', ref: 'withLifeScoring/displayScore/german'}
    ],
    [
      {candidate: 'sets/displayScore', ref: 'sets/displayScore'}
    ]
  ];
var stepGenerators = ['generateGame', 'generateGame', 'generateGame', 'generateGame', 'generateNoAvantageGame',
  'generateGame',
  'generateSet'];

var rotateScoringRepartition = {
  source: 0.5,
  destination: 0.5
};
var rotateStep = "sets/displayScore";
var rotatedRegisteredPlayers = {};

const TRIALS_NUMBER_BY_PARTICIPANT = 2;

var availablePoints = {
  1: [0],
  2: [20, 18, 16, 14, 12, 10, 5],
  3: [20, 18, 16, 14, 12, 10, 5],
  4: [70, 44, 30, 20, 8],
  5: [55, 34, 21, 13, 8],
  6: [70, 44, 30, 20, 8],
  7: [100, 75, 50, 35, 30]
};

var scoreBoard = {};

function sendQuestion(response, remoteAddress) {
  var errors = [];
  var stepQuestion = stepQuestions[turn - 1];
  var stepGenerator = stepGenerators[turn - 1];
  console.log("stepQuestion: ", stepQuestion);
  console.log("stepGenerator: ", stepGenerator);

  return requestAsync('http://localhost:8081/generateTest/' + stepGenerator)
      .spread(function (questionsQueryResponse, questionsQueryBody) {
        return JSON.parse(questionsQueryBody);
      })
      .map(function (questionAsObject) {
        var questionAsQueryParam;
        //if (turn < 4) {
          questionAsQueryParam = '?player1Name=' + questionAsObject.player1GameScore.playerName + '&player1Score=' + questionAsObject.player1GameScore.playerScore +
              '&player2Name=' + questionAsObject.player2GameScore.playerName + '&player2Score=' + questionAsObject.player2GameScore.playerScore;
        //} else {
        //  questionAsQueryParam =
        //      '?scores=' + questionAsObject;
        //
        //}

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
        var comparisonResult = result.candidateResult === result.referenceResult;
        if (!comparisonResult) {
          errors.push("expected ", result.referenceResult + " got " + result.candidateResult);
          console.log('query to candidate server: ', result.candidateQuestion);
          console.log('query to ref server: ', result.refQuestion);
          console.log("candidate response: ", result.candidateResult + " reference response: " + result.referenceResult);
        }
        return comparisonResult;
      })
      .reduce(function (aggregation, comparisonResult) {
        /*console.log('aggregation success ' + aggregation.success);
         console.log('comparaison result  ' + comparisonResult);*/

        if(aggregation.success === undefined){
          return {errors : errors, success : comparisonResult};
        } else {
          return {errors : errors, success : aggregation.success && comparisonResult};
        }
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
    return availablePoints[turn][0];
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
  return turn === 5;
}

function decrementTrialsLeft(remoteAddress) {
  if (typeof competitorsWithTries[remoteAddress] == 'undefined') {
    initializeTrials(remoteAddress);
  }
  competitorsWithTries[remoteAddress] = competitorsWithTries[remoteAddress] - 1;
  console.log("competitorsWithTries[remoteAddress] : ", competitorsWithTries[remoteAddress]);
}

function initializeTrials(remoteAddress) {
  competitorsWithTries[remoteAddress] = TRIALS_NUMBER_BY_PARTICIPANT;
}

function initScoresOfPlayerIfNeeded(currentUser) {
  if (typeof scoreBoard[currentUser.name] == 'undefined') {
    scoreBoard[currentUser.name] = {details: {scoresByTurn: []}, total: 0};
  }
}

function extractIp(req) {
  return cleanRemoteAddress(requestIp.getClientIp(req) != '::1' ? requestIp.getClientIp(req) : "127.0.0.1");
}

function cleanRemoteAddress(remoteAddress) {
  return remoteAddress.replace('::ffff:', '');
}
function scoreWithRotation(currentUser) {
  var scoredPoints = nextScoredPoints();

  var destinationName = currentUser.name;
  var sourceName = _.invert(rotatedRegisteredPlayers)[destinationName];

  var destinationScoredPoints = scoredPoints * rotateScoringRepartition.destination;
  var sourceScorePoints = scoredPoints * rotateScoringRepartition.source;

  console.log('rotation : dest ', destinationName + " - " + destinationScoredPoints);
  console.log('rotation : source ', sourceName + " - " + sourceScorePoints);

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

  console.log("New array of registeredClients: ", JSON.stringify(registeredClients));
  console.log("New array of rotatedRegisteredPlayers: ", JSON.stringify(rotatedRegisteredPlayers));
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
              console.log("ping result : ", JSON.stringify(result));
            })
            .catch(function (error) {
              console.log("ping error : ", error);
            });
        }
      }
    )
  }, 2000);
}

function registerClient(clientIp, name, io) {
  if (!_.contains(_.pluck(registeredClients, 'ip'), clientIp) && !_.contains(_.pluck(registeredClients, 'name'), name)) {
    var newUser = {name: name, ip: clientIp};
    registeredClients.push(newUser);
  }
  io.emit('client', registeredClients);
}

function unregisterClient(clientIp, io) {
  console.log("looked ip :", clientIp);
  _.remove(registeredClients, function(user) {
    console.log("elt ip :", user.ip);
    return user.ip == clientIp;
  });
  console.log("registered clients : ", registeredClients);

  io.emit('client', registeredClients);
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
      console.log("availablePoints: ", availablePoints[turn]);
      var remoteAddress = extractIp(req);
      remoteAddress = cleanRemoteAddress(remoteAddress);
      console.log('remote address : ', remoteAddress);
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
            console.log('scoreBoard: ', scoreBoard);
            console.log("remoteaddress: ", remoteAddress);

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
      console.log ('turn : ', turn);
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
      registerClient(clientIp, name, io);
    },

    registerRest: function (req, res) {
      var clientIp = extractIp(req);
      console.log(req.body.name, clientIp);
      registerClient(clientIp, req.body.name, io);
      res.send('OK');
    },

    unregister: function (req, res) {
      var clientIp = req.body.clientIp;
      console.log(clientIp);
      unregisterClient(clientIp, io);
      res.send('OK');
    },

    resetTrials: function (req, res) {
      var ip = req.body.clientIp;
      initializeTrials(ip);
      res.send('OK');
    },

    saveGame: function (req, res) {
      res.send({
        registeredClients: registeredClients,
        competitorsWithTries: competitorsWithTries,
        rotatedRegisteredPlayers: rotatedRegisteredPlayers,
        availablePoints: availablePoints,
        scoreBoard: scoreBoard,
        turn: turn
      });
    },

    restoreGame: function (req, res) {
      registeredClients = req.body.registeredClients;
      competitorsWithTries = req.body.competitorsWithTries;
      rotatedRegisteredPlayers = req.body.rotatedRegisteredPlayers;
      availablePoints = req.body.availablePoints;
      scoreBoard = req.body.scoreBoard;
      turn = req.body.turn;

      io.emit('refreshScores', scoreBoard);
      io.emit('turn', turn);
      io.emit('rotatedPlayers', rotatedRegisteredPlayers);
      io.emit('client', registeredClients);

      res.send('OK');
    },

    addScoring: function (req, res) {
      console.log(req.body);
      var scoring = req.body.scoring;
      var turn = req.body.turn;
      var ip = req.body.ip;

      var currentUser = _.find(registeredClients, function (registeredClient) {
        return registeredClient.ip === ip;
      });

      console.log(scoreBoard[currentUser.name].details.scoresByTurn[turn - 1].score);
      var currentScore = scoreBoard[currentUser.name].details.scoresByTurn[turn - 1].score;
      scoreBoard[currentUser.name].details.scoresByTurn[turn - 1] = {score: currentScore + scoring};

      io.emit('refreshScores', scoreBoard);
      res.send('OK');
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
