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
    if (Object.keys(this.props.playersChanges).length === 0) {
      return <div></div>;
    }

    return (
      <div>
        <h1>Rotations à effectuer</h1>
        <table>
          <thead>
          <tr>
            <th>Joueur</th>
            <th> -&gt; </th>
            <th>Nouvel emplacement</th>
          </tr>
          </thead>
          <tbody>
          {Object.keys(this.props.playersChanges).map(playerName =>
            <tr>
              <td>{playerName}</td>
              <td> -&gt; </td>
              <td>{self.props.playersChanges[playerName]}</td>
            </tr>
            )}
          </tbody>
        </table>
      </div>
    )
  }
});

module.exports = PlayersRotation;