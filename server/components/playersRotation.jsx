var React = require('react');

var _ = require('lodash');

var PlayersRotation = React.createClass({

  propTypes: {
    playersChanges: React.PropTypes.arrayOf(
      React.PropTypes.shape({
        player: React.PropTypes.string,
        toPcOfPlayer: React.PropTypes.string
      })
    ).isRequired
  },

  getInitialState: function () {
    return {}
  },

  render: function() {
    return <h2>Rotations à effectuer</h2>
  }
});

module.exports = PlayersRotation;