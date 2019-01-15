'use strict';

angular.module('dominionFrontendApp')
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
