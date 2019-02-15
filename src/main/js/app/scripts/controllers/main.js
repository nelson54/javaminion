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

    var Match = $resource(baseUrl+'/com.github.nelson54.dominion/matches/', null, {'update': { method:'PATCH' }});
    var Game = $resource(baseUrl+'/com.github.nelson54.dominion/games/');

    $scope.matches = [];
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

    $scope.getMatches = function(){
      return Match.get(function(response){
        $scope.matches = response.content;
      });
    };

    $scope.createGame = function(){
      var match = new Match();
      match.$save({
        cards: $scope.cards,
        numberOfHumanPlayers: getNumberOfHumanPlayers(),
        numberOfAiPlayers: getNumberOfAiPlayers()
      }, $route.reload);
    };

    $scope.join = function(id){
      Match.update({matchId: id}, {matchId: id}, $scope.update)
    };

    $scope.cancel = function(){
      $scope.newGame = false;
    };

    $scope.updatePlayerCount = function(count){
      $scope.players = [];
      for(count; count != 1; count--){
        $scope.players.push({id:count, ai:true});
      }
    };

    function getNumberOfAiPlayers() {
      return $scope.players.filter(function(player){
        return player.ai;
      }).length
    }

    function getNumberOfHumanPlayers() {
      return $scope.players.length + 1 - getNumberOfAiPlayers();
    }

    $scope.update = function () {
      $scope.getGames();
      $scope.getMatches();
    };

    $scope.update();
});
