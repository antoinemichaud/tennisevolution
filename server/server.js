var express = require('express'),
  http = require('http'),
  config = require('./config'),
 bodyParser = require('body-parser'),
  _ = require('lodash');


var app = express();

var PORT = 8082;

app.enable('trust proxy');

var server = http.createServer(app).listen(PORT, function() {
  console.log('Express server listening on port ' + PORT);
});


var io = require('socket.io').listen(server);
var routes = require('./routes')(io);
routes.init();


//be able to load the files under the public directory
app.use("/", express.static(__dirname + "/public/"));
app.use(bodyParser.json());


app.post('/compare', routes.compare);
app.post('/turn', routes.turn);
app.get('/', routes.index);
