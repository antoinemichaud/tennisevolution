var JSX = require('node-jsx').install(),
  React = require('react'),
  _ = require('lodash'),
 APP = require('./app'),

 registeredClients = [];

module.exports = function(io) {
  return {
  firstStep: function(req, res) {
    //business code goes here
  },

  register: function(name, clientIp) {
    console.log(name, clientIp);
    if (!_.contains(_.pluck(registeredClients, 'ip'), clientIp)){
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
      if(registeredClients.length > 0){
        socket.emit('initClients', registeredClients)
      };
      socket.on('register', function (name) {
        self.register(name, clientIp);
      });
    });
  }
};
}
