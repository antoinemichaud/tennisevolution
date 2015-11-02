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
      scoresBis: new HashMap()
    };
  },

  componentDidMount: function() {
    var socket = io.connect();
    socket.on('connect',function(){

    });
    socket.on('client', this.addClient);
    socket.on('initClients', this.initClients);
    socket.on('refreshScores', this.refreshScores);
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

  refreshScores: function(scores){
    this.setState({
      scoresBis: scores
    });
  },

  handleSubmit: function(e) {
    e.preventDefault();
    this.state.socket.emit('register', this.refs.author.getDOMNode().value);
  },

  // Render the component
  render: function() {

    var content = this.state.clients.map(function(client) {
      return (< li > {client.name} with ip : {client.ip} < /li>);
    });

   /* var contentScores = this.state.scores.map(function(score) {
      return (< li > {score.player}  for tour {score.score.tour} has score {score.score.value} < /li>);
    });*/
    var contentScores = [];
    new HashMap().copy(this.state.scoresBis).forEach(function(value, player){
      var localScore = new HashMap().copy(value);
      localScore.forEach(function(score, tour){
        contentScores.push( < li > {player} - {tour}  : {score}< /li> );
      });
    });

    return (
      <div>
      <form className="addParticipant" onSubmit={this.handleSubmit}>
    <input type="text" placeholder="Your name" ref="author" />
      <input type="submit" value="Register" />
      </form>

      <h2> Participants: </h2>
    <ul> {content} </ul>

    <h2> Scores: </h2>
    <ul> {contentScores} </ul>

    </div>
    )

  }

});
