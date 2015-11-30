/** @jsx React.DOM */

var React = require('react');
var _ = require('lodash');

// Export the TweetsApp component
module.exports = DeathmatchApp = React.createClass({

  // Set the initial component state
  getInitialState: function() {
    return {
      clients: [],
      scores: {},
      turn: 1
    };
  },

  componentDidMount: function() {
    var socket = io.connect();
    socket.on('connect', function() {

    });
    socket.on('client', this.addClient);
    socket.on('initClients', this.initClients);
    socket.on('refreshScores', this.refreshScores);
    socket.on('turn', this.turn);
    this.setState({socket: socket});
  },

  addClient: function(client) {
    var updated = this.state.clients;
    updated.push(client);
    this.setState({
      clients: updated
    });
  },

  initClients: function(clients) {
    this.setState({
      clients: clients
    });
  },


  refreshScores: function(scores) {
    this.setState({
      scores: scores
    });
  },

  turn: function(turn) {
    this.setState({
      turn: turn
    })
  },

  handleSubmit: function(e) {
    e.preventDefault();
    this.state.socket.emit('register', this.refs.author.getDOMNode().value);
  },

  // Render the component
  render: function() {

    var content = this.state.clients.map(function(client) {
      return (<li> {client.name} with ip : {client.ip} </li>);
    });


    var scoresTable = [];
    var findScoreByTurn = function (scoresByTurn, turn) {
      var scoreAtTurn = scoresByTurn[turn - 1];
      return scoreAtTurn ? scoreAtTurn : 0;
    };

    _.forOwn(this.state.scores, function (value, key) {
      scoresTable.push(<tr>
        <td>{key}</td>
        <td>{findScoreByTurn(value.details.scoresByTurn, 1)}</td>
        <td>{findScoreByTurn(value.details.scoresByTurn, 2)}</td>
        <td>{findScoreByTurn(value.details.scoresByTurn, 3)}</td>
        <td>{findScoreByTurn(value.details.scoresByTurn, 4)}</td>
        <td>{findScoreByTurn(value.details.scoresByTurn, 5)}</td>
        <td>{value.total}</td>
        <td>XXX</td>
      </tr>);
    });


    return (
      <div>
        <h2> Turn: {this.state.turn}</h2>

        <form className="addParticipant" onSubmit={this.handleSubmit}>
          <input type="text" placeholder="Your name" ref="author"/>
          <input type="submit" value="Register"/>
        </form>

        <h2> Participants: </h2>
        <ul> {content} </ul>

        <h2> Alternate Scores: </h2>
        <table>
          <tr>
            <th>Player</th>
            <th>Turn 1</th>
            <th>Turn 2</th>
            <th>Turn 3</th>
            <th>Turn 4</th>
            <th>Turn 5</th>
            <th>Total Score</th>
            <th>Classement</th>
          </tr>
          {scoresTable}
        </table>

      </div>
    )

  }

});
