var http = require('http');
var url = require('url');
var parse = require('parse');
//parse.initialize("mSjyVccMyMBh8fxcY6zkIYlDEXiXgwjngbKoiFM8", "IA5wobRXq295jg2YucJ7TavgM8pD4848K2FlOknj");
var qs = require('querystring');
var request = require('request');

var answers = [];
var sessions = ["sample1", "sample2"];
var curques = [];
var curans = [];

    var sessionExists = function(sess) {
      for (i = 0; i < sessions.length; i++) {
        if (sessions[i] == sess) {
          return true;
        }
      }
      return false;
    };

    var sessionIndex = function(sess) {
      for (i = 0; i < sessions.length; i++) {
        if (sessions[i] == sess) {
          return i;
        }
      }
      return "no session found";
    };

var server = http.createServer( function(req, res) {

    res.writeHead(200, {
        'content-type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, OPTIONS, PUT, PATCH, DELETE',
        'Access-Control-Allow-Headers': 'X-Requested-With,content-type'
    });
    
    var parsedUrl = url.parse(req.url, true);
    var resp = {};

    var body = '';
    req.on('data', function (data) {
      body += data;
      console.log("here");
      // Too much POST data, kill the connection!
      if (body.length > 1e6) {
        console.log("destroyed");
        request.connection.destroy();
      }
    });

    var resp = {};

    req.on('end', function () {

      console.log("Entered end");

      var post = qs.parse(body);
      console.log(post);
      console.log("Advanced");

      if (post.requestType == "webApp") {
        if (sessionExists(post.session)) {
          console.log(post);
          resp.consoleLog = post;
          console.log(req.method);
          resp.success = true;
          curans[sessionIndex(post.session)] = post.curans;
          curques[sessionIndex(post.session)] = post.question;
          resp.vals = answers[sessionIndex(post.session)];    
          res.end(JSON.stringify(resp));
          answers[sessionIndex(post.session)] = [];
        }
        else {
          sessions.push(post.session);
          answers.push([]);
          curans.push([]);
          curques.push("");
          console.log(post);
          resp.consoleLog = post;
          console.log(req.method);
          resp.success = true;     
          res.end(JSON.stringify(resp));
        }
      }
      else if (post.requestType == "client") {
        if (post.session == "clientStart") {
          console.log(post);
          console.log(req.method);
          resp.success = false;
          resp.sessionvar = "ngrok";     
          res.end(JSON.stringify(resp));
        }

        if (sessionExists(post.session)) {
          answers[sessionIndex(post.session)].push(post.answer);
  
          console.log(post);
          console.log(req.method);
          resp.curans = curans[sessionIndex(post.session)];
          resp.curques = curques[sessionIndex(post.session)];
          resp.success = true;     
          res.end(JSON.stringify(resp));
        }
        else {
          console.log(post);
          console.log(req.method);
          resp.success = false;     
          res.end(JSON.stringify(resp));
        }
      }

    });
});

server.listen(8080);


// var io = require('socket.io').listen(server);
// //creating a new websocket to keep the content updated without any AJAX request
// io.sockets.on('connection', function(socket) {
//   var json = {
//     "answer": "a"
//   };
  
//   socket.volatile.emit('notification', json);
// });


