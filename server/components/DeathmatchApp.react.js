/** @jsx React.DOM */

var React = require('react');
var _ = require('lodash');

// Export the TweetsApp component
module.exports = DeathmatchApp = React.createClass({

  // Set the initial component state
  getInitialState: function() {
    return {
      clients: []
    };
  },

  componentDidMount: function() {
    var socket = io.connect();
    socket.on('connect',function(){

    });
    socket.on('client', this.addClient);
    socket.on('initClients', this.initClients);
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

  handleSubmit: function(e) {
    e.preventDefault();
    this.state.socket.emit('register', this.refs.author.getDOMNode().value);
  },

  // Render the component
  render: function() {

    console.log("render " + this.state.clients)
    var content = this.state.clients.map(function(client) {
      return ( < li > {client.name} with ip : {client.ip} < /li>);
    });

    return (
       <div>
         <form className="addParticipant" onSubmit={this.handleSubmit}>
            <input type="text" placeholder="Your name" ref="author" />
            <input type="submit" value="Register" />
         </form>

          <h2> Participants: </h2>
          <ul> {content} </ul>

      </div>
    )

  }

});
