function wildsnakeCtrl($http) {
  $http.get('https://wildsnake-jjdlugosz.herokuapp.com/api/v1/products').then(response => this.products = response.data);
}

function wildsnakeConfig ($interpolateProvider) {
  $interpolateProvider.startSymbol('[[').endSymbol(']]');
}

angular.module('wildsnake', [])
  .config(wildsnakeConfig)
  .controller('WildsnakeCtrl', wildsnakeCtrl);

