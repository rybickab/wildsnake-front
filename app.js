var express = require('express');
var exphbs = require('express-handlebars');

var app = express();

app.use(express.static('static'));
app.engine('.html', exphbs({extname: '.html'}));
app.set('view engine', '.html');

app.get('/', function (req, res) {
  res.render('index');
});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});
