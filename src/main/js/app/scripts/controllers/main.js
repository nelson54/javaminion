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

    var Match = $resource(baseUrl+'/dominion/matches/');
    var Game = $resource(baseUrl+'/dominion/');

    $scope.games = [];

    $scope.cards = 'First Game';
    $scope.newGame = false;
    $scope.playerNumbers = [2,3,4];
    $scope.recommendedCards = recommendedCards;
    $scope.players = [{id:0, ai:true}, {id:1, ai:true}];

    $scope.setCards = function(cards) {
      $scope.cards = cards;
    };

    $scope.getGames = function(){
      return Game.get(function(response){
          $scope.games = response.content;
        });
    };

    $scope.createGame = function(){
      var match = new Match({
        cardSet: $scope.cards,
        players: $scope.players,
        count: $scope.players.length + 1
      });
      match.$save($route.reload);
    };

    $scope.updatePlayerCount = function(count){
      $scope.players = [];
      for(count; count != 1; count--){
        $scope.players.push({id:count, ai:true});
      }
    };

    $scope.getGames();
  });
