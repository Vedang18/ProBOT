var express = require('express');
var bodyParser = require('body-parser');
var path = require('path');

app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'admin', 'build', 'static')));

app.get('/hello', (req, res) => {
    res.send({ msg: 'hello' });
});

module.exports = app;