var express = require('express');
var exphbs = require('express-handlebars');

var app = express();
var port = process.env.PORT || 8080;


app.use(express.static('static'));
app.engine('.html', exphbs({extname: '.html'}));
app.set('view engine', '.html');

app.get('/', function (req, res) {
  res.render('index');
});

app.listen(port, function () {
  console.log('Wildsnake listening on port ' + port);
});
