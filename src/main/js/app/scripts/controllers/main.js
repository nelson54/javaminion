'use strict';

/**
 * @ngdoc function
 * @name dominionFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the dominionFrontendApp
 */
angular.module('dominionFrontendApp')
  .controller('MainCtrl', function ($scope, $http, $resource, $route) {
    var baseUrl = "";
    var Game = $resource(baseUrl+'/dominion/');

    $scope.games = [];

    $scope.getGames = function(){
      return $http.get(baseUrl+'/dominion/')
        .success(function(response){
          $scope.games = response;
        });
    };

    $scope.createGame = function(){
      var game = new Game({});
      game.$save($scope.getGames, $route.reload);
    };

    $scope.getGames();
  });
