//Dependencies
var express     = require('express');
var mongoose    = require('mongoose');
var bodyParser  = require('body-parser');
var logger      = require('morgan');
//var jwt         = require('jsonwebtoken');

//var config      = require('./config');
//var User        = require('./models/user');

var app         = express();
var server      = require('http').createServer(app);
var port        = process.env.PORT || 3000;

server.listen(port, function(){
    console.log('Server listening on port %d', port);
});

//MongoDB
mongoose.connect('mongodb://localhost/yellowtest');

app.use(bodyParser.urlencoded({ extended: true}));
app.use(bodyParser.json());
app.use(logger('dev'));

app.use('/static', express.static(__dirname+ '/public'));

//app.set('superSecret', config.secret);

//Routes
app.use('/', require('./routes/index'));

