var React = require('react');
var DeathmatchApp = require('./components/DeathmatchApp.jsx');


var APP = React.createClass({

  render: function () {
    return (
      <div>
        <DeathmatchApp/>
      </div>
    );
  }
});

module.exports = APP;

if (typeof window !== 'undefined') {
  window.onload = function () {
    React.render(APP(), document.getElementById('react'));
  }
}
