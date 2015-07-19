var express = require('express');
var app = express();
var _ = require('lodash');

var PORT = 8082;

//registered clients
var registeredClients = [];
app.enable('trust proxy');

//be able to load the files under the public directory
app.use(express.static('public'));

app.get('/page1', function (req, res) {
  if(_.contains(registeredClients, req.ip)){
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('Page One');
  }else{
    res.writeHead(403, {'Content-Type': 'text/plain'});
    res.end("You are not registered !");
  }
});

app.post("/register", function(req, res) {
  if(!_.contains(registeredClients, req.ip)){
    console.log(req.ip + " wasn't registered yet");
    registeredClients.push(req.ip);
  }
  res.writeHead(202, {'Content-Type': 'text/plain'});
  res.end();
});

var server = app.listen(PORT, function() {
  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
});
