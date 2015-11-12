var JSX     = require('node-jsx').install(),
    React   = require('react'),
    _       = require('lodash'),
    APP     = require('./app');s

var registeredClients = [];

var turn = 1;
var stackPoints = [1000, 500, 100, 50, 25, 13, 1];

var availablePoints = {
  1: _.clone(stackPoints),
  2: _.clone(stackPoints),
  3: _.clone(stackPoints),
  4: _.clone(stackPoints),
  5: _.clone(stackPoints)
};

var scoreBoard = {};

module.exports = function(io) {
  return {
    compare: function(req, res) {
      var scoredPoints = availablePoints[turn].shift();

      var currentUser = _.find(registeredClients, function(registeredClients) {
        return registeredClients.ip === req.connection.remoteAddress;
      });

      if(scoreBoard[currentUser.name]){
        if(!_.contains(_.pluck(scoreBoard[currentUser.name].details, 'turn'), turn)){
          scoreBoard[currentUser.name].details.push({turn: turn, score: scoredPoints});
          scoreBoard[currentUser.name].total = scoredPoints + scoreBoard[currentUser.name].total;
        }
      }else {
        scoreBoard[currentUser.name] = {details: [], total: 0};
        scoreBoard[currentUser.name].details.push({turn: 1, score: scoredPoints});
        scoreBoard[currentUser.name].total = scoredPoints;
      }

      io.emit('refreshScores', scoreBoard);
      res.send(scoreBoard[currentUser.name]);
    },

    turn: function(req, res) {
      turn = req.body.turn;
      io.emit('turn', turn);
      res.send('OK');
    },

    register: function(name, clientIp) {
      console.log(name, clientIp);
      if(!_.contains(_.pluck(registeredClients, 'ip'), clientIp)) {
        var newUser = {name: name, ip: clientIp};
        registeredClients.push(newUser);
        io.emit('client', newUser);
      }
    },

    index: function(req, res) {
      var markup = React.renderToString(APP());
      res.send(markup);
    },

    init: function() {
      var self = this;
      io.on('connection', function(socket) {
        var clientIp = socket.handshake.address;
        if(registeredClients.length > 0) {
          socket.emit('initClients', registeredClients);
          socket.emit('refreshScores', scoreBoard);
        }

        socket.on('register', function(name) {
          self.register(name, clientIp);
        });
      });
    }
  };
};
