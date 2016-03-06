var express = require('express'),
  http = require('http'),
  config = require('./config'),
 bodyParser = require('body-parser'),
  browserify = require('browserify-middleware'),
  _ = require('lodash');


var app = express();

var packages = ['react'];

app.get('/js/lib.js', browserify(packages, {
  cache: true,
  precompile: true
}));

app.get('/js/bundle.js', browserify('./scripts/app.jsx', {
  external: packages,
  transform: [[
    "reactify",
    {
      "es6": true,
      "strip-types": true
    }
  ]]
}));

var PORT = 8082;

app.enable('trust proxy');

var server = http.createServer(app).listen(PORT, function() {
  console.log('Express server listening on port ' + PORT);
});


var io = require('socket.io').listen(server);
var routes = require('./routes')(io);
routes.init();


//be able to load the files under the public directory
app.use("/", express.static(__dirname + "/"));
app.use(bodyParser.json());

app.post('/registerRest', routes.registerRest);
app.post('/compare', routes.compare);
app.post('/turn', routes.turn);
app.post('/changeip', routes.changeip);
app.post('/ping', routes.pingClients);
app.post('/stopPing', routes.stopPingClients);
app.post('/pingThisClientAgain', routes.pingThisClientAgain);
app.post('/dontPingThisClient', routes.dontPingThisClient);
