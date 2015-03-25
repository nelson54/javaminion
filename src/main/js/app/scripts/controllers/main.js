'use strict';

/**
 * @ngdoc function
 * @name dominionFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the dominionFrontendApp
 */
angular.module('dominionFrontendApp')
  .controller('MainCtrl', function ($scope, $http, $resource, $route, baseUrl, recommendedCards) {

    var Game = $resource(baseUrl+'/dominion/');

    $scope.games = [];
    $scope.recommendedCards = recommendedCards;

    $scope.getGames = function(){
      return $http.get(baseUrl+'/dominion/')
        .success(function(response){
          $scope.games = response;
        });
    };

    $scope.createGame = function(){
      var game = new Game({cardSet: $scope.cardSet});
      game.$save(game, $route.reload);
    };

    $scope.getGames();
  });
