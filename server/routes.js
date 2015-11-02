var JSX               = require('node-jsx').install(),
    React             = require('react'),
    HashMap           = require('hashmap'),
    _                 = require('lodash'),
    APP               = require('./app'),

    registeredClients = [];
scores = new HashMap();

module.exports = function(io) {
  return {
    savescores: function(req, res) {
      if(scores.has(req.connection.remoteAddress)){
        scores.get(req.connection.remoteAddress).set(req.body.tour, req.body.score);
      }else{
        scores.set(req.connection.remoteAddress, new HashMap().set(req.body.tour, req.body.score));
      }
      io.emit('refreshScores', scores);
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
