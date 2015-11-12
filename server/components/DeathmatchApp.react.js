/** @jsx React.DOM */

var React = require('react');
var _ = require('lodash');
var HashMap = require('hashmap');

// Export the TweetsApp component
module.exports = DeathmatchApp = React.createClass({

  // Set the initial component state
  getInitialState: function() {
    return {
      clients: [],
      scores: [],
      scoresBis: new HashMap(),
      total: new HashMap(),
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
    socket.on('refreshTotal', this.refreshTotal);
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
      scoresBis: scores
    });
  },

  refreshTotal: function(total) {
    this.setState({
      total: total
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

    var contentScoreTable = [];
    new HashMap().copy(this.state.scoresBis).forEach(function(value, player) {
      var localScore = new HashMap().copy(value);
      contentScoreTable.push(
        <tr>
          <td>{player.name}</td>
          <td>{localScore.get(1)}</td>
          <td>{localScore.get(2)}</td>
          <td>{localScore.get(3)}</td>
          <td>{localScore.get(4)}</td>
          <td>{localScore.get(5)}</td>
          <td>XXX</td>
          <td>XXX</td>
        </tr>
      );
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

        <h2> Scores: </h2>
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
          {contentScoreTable}
        </table>

      </div>
    )

  }

});
