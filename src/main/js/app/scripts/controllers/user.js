'use strict';

angular.module('dominionFrontendApp')
  .controller('UserCtrl', function ($scope, $http, $resource, UserService) {

    UserService.get(function(user){
      $scope.user = user;
    });

  });
