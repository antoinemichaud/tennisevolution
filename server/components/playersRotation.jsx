var React = require('react');

var _ = require('lodash');

var PlayersRotation = React.createClass({

  propTypes: {
    playersChanges: React.PropTypes.object.isRequired
  },

  getInitialState: function () {
    return {}
  },

  render: function () {
    var self = this;
    return (
      <div>
        <h2>Rotations à effectuer</h2>
        <table>
          <thead>
          <tr>
            <th>Joueur</th>
            <th> -&gt; </th>
            <th>Nouvel emplacement</th>
          </tr>
          </thead>
          <tbody>
          {Object.keys(this.props.playersChanges).map(function(playerName) {
            return (
            <tr>
              <td>{playerName}</td>
              <td> -&gt; </td>
              <td>{self.props.playersChanges[playerName]}</td>
            </tr>)}
            )}
          </tbody>
        </table>
      </div>
    )
  }
});

module.exports = PlayersRotation;