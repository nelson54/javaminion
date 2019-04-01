'use strict';

let dominionFrontendApp = angular.module('dominionFrontendApp', ['$http', '$resource', '$route', '$location', 'baseUrl', 'jwtService'])
dominionFrontendApp.component('home', {
  template: '<h1>Home</h1><p>Hello, {{ $ctrl.user.name }} !</p>',
  controller: function() {
    this.user = {name: 'world'};
  }
})
  .controller('LoginCtrl', function ($scope, $http, $resource, $route, $location, baseUrl, jwtService) {
    let self = $scope;
    let Authentication = $resource(baseUrl+'/api/authentication');

    self.user = new Authentication({});

    self.login = function() {
      self.user.$save().then(function(response) {
        jwtService.setToken(response.token);
        $location.path('/games');
      });
    };


  });
