var JSX     = require('node-jsx').install(),
    React   = require('react'),
    HashMap = require('hashmap'),
    _       = require('lodash'),
    APP     = require('./app');


var registeredClients = [];
var scores = new HashMap();
var total = new HashMap();

var turn = 1;
var stack = [1000, 500, 100, 50, 25, 13, 1];


var scoring = new HashMap().set(1, _.clone(stack))
  .set(2, _.clone(stack))
  .set(3, _.clone(stack))
  .set(4, _.clone(stack))
  .set(5, _.clone(stack));

module.exports = function(io) {
  return {
    compare: function(req, res) {
      var score = scoring.get(turn).shift();
      var user = _.find(registeredClients, function(registeredClients) {
        return registeredClients.ip === req.connection.remoteAddress;
      });
      if(scores.has(user)) {
        scores.get(user).set(turn, score);
        total.set(user, total.get(user) + score);
      } else {
        scores.set(user, new HashMap().set(turn, score));
        total.set(user, score);
      }
      io.emit('refreshScores', scores);
      io.emit('refreshTotal', total);
      res.send('OK');
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
          socket.emit('refreshScores', scores);
        }
        ;
        socket.on('register', function(name) {
          self.register(name, clientIp);
        });
      });
    }
  };
}
