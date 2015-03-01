'use strict';

/**
 * @ngdoc function
 * @name dominionFrontendApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the dominionFrontendApp
 */
angular.module('dominionFrontendApp')
  .controller('GameCtrl', function ($scope, $http, $resource, $route, game, playerId) {

    $scope.game = game;

    $scope.shuffle = function(){
      var Game = $resource('/dominion/:gameId/:playerId/shuffle');
      Game.get({gameId : game.id, playerId : playerId}, function(){
        $route.reload();
      });
    };

    $scope.drawHand = function(){
      var Game = $resource('/dominion/:gameId/:playerId/draw');
      Game.get({gameId : game.id, playerId : playerId}, function(){
        $route.reload();
      });
    };

    $scope.discardHand = function(){
      var Game = $resource('/dominion/:gameId/:playerId/discard');
      Game.get({gameId : game.id, playerId : playerId}, $route.reload);
    };

    $scope.purchase = function(card){
      var Purchase = $resource('/dominion/:gameId/:playerId/purchase');

      var purchase = new Purchase(card);

      purchase.$save({gameId : game.id, playerId : playerId},$route.reload);
    };

    $scope.play = function(card){
      var Play = $resource('/dominion/:gameId/:playerId/play');

      var play = new Play(card);

      play.$save({gameId : game.id, playerId : playerId},$route.reload);
    }

    $scope.nextPhase = function(card){
      var EndPhase = $resource('/dominion/:gameId/next-phase');

      var endPhase = new EndPhase(card);

      endPhase.$save({gameId : game.id},$route.reload);
    };

    $scope.canAfford = function(card){
      return $scope.getCurrentPlayer() && $scope.getCurrentPlayer().money >= card.cost.money;
    };

    $scope.isCurrentPlayer = function(player) {
      return player.id === playerId;
    };

    $scope.isActivePlayer = function(player) {
      return game.active.id === player.id;
    }

    $scope.hasCurrentPlayer = function(){
      return game && game.players && game.players.hasOwnProperty(playerId);
    };

    $scope.getCurrentPlayer = function(){
      if ($scope.hasCurrentPlayer())
        return $scope.game.players[playerId];
    };

    $scope.hasCardType = function(type, card){
      card.cardTypes.includes(type);
    }

  });
