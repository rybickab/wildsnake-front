function wildsnakeCtrl($http) {
  $http.get('https://wildsnake-jjdlugosz.herokuapp.com/api/v1/products').then(response => this.products = response.data);
}

function wildsnakeConfig ($interpolateProvider) {
  $interpolateProvider.startSymbol('[[').endSymbol(']]');
}

angular.module('wildsnake', [])
  .config(wildsnakeConfig)
  .controller('WildsnakeCtrl', wildsnakeCtrl)
  .filter('unique', function() {
    return function(collection, keyname1, keyname2, keyname3, keyname4) {
      var output = [],
          keys = [];

      angular.forEach(collection, function(item) {
        var key1 = item[keyname1];
        var key2 = item[keyname2];
        var key3 = item[keyname3];
        var key4 = item[keyname4];
        var key = key1 + key2 + key3 + key4;
        if(keys.indexOf(key) === -1) {
          keys.push(key);
          output.push(item);
        }
      });

      return output;
    };
  });

