'use strict';

angular.module('dominionFrontendApp')
  .controller('GameCtrl', function ($scope, $http, $resource, $route, $timeout, game, playerId) {
    var baseUrl = "";
    $scope.game = game;
    $scope.test = false;

    var commonComparator = function(id1, id2){
      var c1 = game.kingdom.cardMarket[id1][0];
      var c2 = game.kingdom.cardMarket[id2][0];

      if(c1.kingdomSortOrder == c2.kingdomSortOrder){
        return 0;
      } else if (c1.kingdomSortOrder > c2.kingdomSortOrder) {
        return 1;
      } else {
        return -1;
      }
    };

    var kingdomComparator = function(id1, id2){
      var c1 = game.kingdom.cardMarket[id1][0];
      var c2 = game.kingdom.cardMarket[id2][0];

      if(c1.cost.money == c2.cost.money){
        return 0;
      } else if (c1.cost.money > c2.cost.money) {
        return 1;
      } else {
        return -1;
      }
    };

    $scope.commonCards = Object.keys(game.kingdom.cardMarket)
      .filter(function(id){return !game.kingdom.cardMarket[id][0].isKingdom})
      .sort(commonComparator);

    $scope.kingdomCards = Object.keys(game.kingdom.cardMarket)
      .filter(function(id){return game.kingdom.cardMarket[id][0].isKingdom})
      .sort(kingdomComparator);

    $scope.shuffle = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId/:playerId/shuffle');
      Game.get({gameId : game.id, playerId : playerId}, function(){
        $route.reload();
      });
    };

    $scope.drawHand = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId/:playerId/draw');
      Game.get({gameId : game.id, playerId : playerId}, function(){
        $route.reload();
      });
    };

    $scope.discardHand = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId/:playerId/discard');
      Game.get({gameId : game.id, playerId : playerId}, $route.reload);
    };

    $scope.canAfford = function(card){
      return $scope.getCurrentPlayer() && $scope.game.turn.money >= card.cost.money;
    };


    $scope.purchase = function(card){
      var Purchase = $resource(baseUrl+'/dominion/:gameId/:playerId/purchase');

      var purchase = new Purchase(card);

      purchase.$save({gameId : game.id, playerId : playerId},$route.reload);
    };

    $scope.play = function(card){
      var Play = $resource(baseUrl+'/dominion/:gameId/:playerId/play');

      var play = new Play(card);

      play.$save({gameId : game.id, playerId : playerId},$route.reload);
    }

    $scope.nextPhase = function(card){
      var EndPhase = $resource(baseUrl+'/dominion/:gameId/next-phase');

      var endPhase = new EndPhase(card);

      endPhase.$save({gameId : game.id},$route.reload);
    };

    $scope.choose = function(game, player, choose, response){
      var Choice = $resource(baseUrl+'/dominion/:gameId/:playerId/choice');

      var choice = new Choice();

      switch (choose.expectedAnswerType) {
        case 'CARD':
          choice.targetChoice = choose.id;
          choice.card = response;
          break;
        case 'YES_OR_NO':
          choice.isYesOrNo = response;
          break;
      }
      if(choose.expectedAnswerType == 'CARD') {

      }

      choice.$save({gameId : game.id, playerId:player.id},$route.reload);
    };

    $scope.chooseDone = function(game, player, choose, response){
      var Choice = $resource(baseUrl+'/dominion/:gameId/:playerId/choice');

      var choice = new Choice();

      choice.done = true;

      choice.$save({gameId : game.id, playerId:player.id},$route.reload);
    };

    $scope.isCurrentPlayer = function(player) {
      return player.id === playerId;
    };

    $scope.isActivePlayer = function(player) {
      return game.turn.playerId === player.id;
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
    };

    $scope.getChoices = function(player){
      for ( var choice in game.choices ) {
        if (choice.target == player.id){
          return choice;
        }
      }
    };

    $scope.getImagePath = function(card) {
      return '/images/'+ card.name.toLowerCase().replace(/\s/g, '') + '.jpg';
    }

    if($scope.getCurrentPlayer() && $scope.getCurrentPlayer().currentTurn.phase === 'WAITING_FOR_OPPONENT'){
        $timeout(function() {
          $route.reload();
        }, 1000);
    }
  });
