
var React = require('react');
var DeathmatchApp = require('./components/DeathmatchApp.react');

var APP = React.createClass({

  render: function() {

    return (
      <html>
        <head>
          <title>Deathmatch Participants</title>
        </head>
        <body>
          <h1> Deathmatch Refactoring </h1>
          <DeathmatchApp/>
          <script src='bundle.js'></script>
          <script src="https://cdn.socket.io/socket.io-1.1.0.js"></script>
        </body>
      </html>
    );
  }
});

module.exports = APP;

if(typeof window !== 'undefined') {
  window.onload = function() {
    React.render(APP(), document);
  }
};
