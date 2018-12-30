'use strict';

angular.module('dominionFrontendApp')
  .controller('UserCtrl', function ($scope, $http, $resource, UserServiceFactory) {
    let UserService = UserServiceFactory();
    UserService.get(function(user){
      $scope.user = user;
    });

  });
