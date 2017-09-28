// This loads the environment variables from the .env file
require('dotenv-extended').load();
var logger = require('./log4js').logger;
var moment = require('moment');

var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var prorigoRest = require('./bot/prorigoRest');

// Web app
var app = express();
var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');
app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(express.static(path.join(__dirname, 'public')));

// Register your web app routes here
app.get('/', function (req, res, next) {
    res.render('index', { title: 'ProBOT' });
});

app.get('/login', function (req, res, next) {
    res.render('login', { title: 'ProBOT' });
});

// Register Bot
var bot = require('./bot');
app.post('/api/messages', bot.listen());

app.post('/login', function (req, res) {
    var userId = req.query.userId;
    var channelId = req.query.channelId;
    var username = req.body.username;
    var password = req.body.password;

    prorigoRest.saveUser(function (json) {
        res.status(200).send("User credentials saved successfully. You can continue to use the bot.");
    },
    function (err) {
        res.status(500).send("Could not authenticate with provided username & password.");
    },
    {'channelId': channelId, 'userId': userId, 'username': username, 'password': password });
});

app.post('/api/sendmessage', function(req, res){
    var txtMsg = req.body.msg;
    var addressString = req.body.addressString;
    bot.sendMessage(txtMsg, JSON.parse(addressString));
    res.status(200).send();
});

    // Catch 404 and forward to error handler
    app.use(function (req, res, next) {
        var err = new Error('Not Found');
        err.status = 404;
        next(err);
    });

    // Error handlers

    // Development error handler, will print stacktrace
    if (app.get('env') === 'development') {
        app.use(function (err, req, res, next) {
            res.status(err.status || 500);
            res.render('error', {
                message: err.message,
                error: err
            });
        });
    }

    // Production error handler, no stacktraces leaked to user
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: {}
        });
    });

    // Start listening
    var port = process.env.port || process.env.PORT || 3978;
    app.listen(port, function () {
        console.log('Web Server listening on port %s', port);
        logger.debug('Server started on: ' + moment().format('YYYY-MM-DD hh:mm:ss A Z'));
    });