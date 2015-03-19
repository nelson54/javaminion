'use strict';

angular.module('dominionFrontendApp')
  .controller('GameCtrl', function ($scope, $http, $resource, $route, $interval, game, playerId, baseUrl) {

    var gameId = game.id,
      players,
      player,
      hand,
      deck,
      turn,
      play,
      discard,
      interval;

    $scope.test = false;
    $scope.game = game;

    var repeat = function() {
      if (!interval && player && player.currentTurn.phase === 'WAITING_FOR_OPPONENT') {
        interval = $interval(function () {
          //console.log("reloaded")
          reload(gameId);//gameId);
        }, 1000);
      } else if (interval && player.currentTurn.phase !== 'WAITING_FOR_OPPONENT') {
        $interval.cancel(interval);
        interval = undefined;
      }
    };

    var updateData = function(game){
      players = $scope.players = game.players;
      player = $scope.player = game.players[playerId];
      hand  = $scope.hand = game.players[playerId].hand;
      deck  = $scope.deck = game.players[playerId].deck;
      turn  = $scope.turn = game.players[playerId].currentTurn;
      discard = $scope.discard = game.players[playerId].discard;
      play  = $scope.play = game.turn.play;

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
      $scope.$apply();
      $scope.commonCards = Object.keys(game.kingdom.cardMarket)
        .filter(function(id){return !game.kingdom.cardMarket[id][0].isKingdom})
        .sort(commonComparator);

      $scope.kingdomCards = Object.keys(game.kingdom.cardMarket)
        .filter(function(id){return game.kingdom.cardMarket[id][0].isKingdom})
        .sort(kingdomComparator);

      repeat();
    };
    if(playerId) updateData(game);

    $scope.shuffle = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId/:playerId/shuffle');
      Game.get({gameId : game.id, playerId : playerId}, function(){
        reload();
      });
    };

    $scope.drawHand = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId/:playerId/draw');
      Game.get({gameId : game.id, playerId : playerId}, function(){
        reload();
      });
    };

    $scope.discardHand = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId/:playerId/discard');
      Game.get({gameId : game.id, playerId : playerId}, reload);
    };

    $scope.canAfford = function(card){
      return player && game.turn.money >= card.cost.money;
    };

    $scope.purchase = function(card){
      var Purchase = $resource(baseUrl+'/dominion/:gameId/:playerId/purchase');

      var purchase = new Purchase(card);

      purchase.$save({gameId : game.id, playerId : playerId},reload);
    };

    $scope.playCard = function(card){
      var Play = $resource(baseUrl+'/dominion/:gameId/:playerId/play');

      var play = new Play(card);

      play.$save({gameId : game.id, playerId : playerId},reload);
    }

    $scope.nextPhase = function(card){
      var EndPhase = $resource(baseUrl+'/dominion/:gameId/next-phase');

      var endPhase = new EndPhase(card);

      endPhase.$save({gameId : game.id},reload);
    };

    $scope.choose = function(game, player, choose, response){
      var Choice = $resource(baseUrl+'/dominion/:gameId/:playerId/choice');

      var choice = new Choice();

      choice.targetChoice = choose.id;

      switch (choose.expectedAnswerType) {
        case 'CARD':
          choice.card = response;
          break;
        case 'YES_OR_NO':
          choice.isYesOrNo = response;
          break;
      }
      if(choose.expectedAnswerType == 'CARD') {

      }

      choice.$save({gameId : game.id, playerId:player.id},reload);
    };

    $scope.chooseDone = function(game, player, choose){
      var Choice = $resource(baseUrl+'/dominion/:gameId/:playerId/choice');

      var choice = new Choice();
      choice.targetChoice = choose.id;
      choice.done = true;

      choice.$save({gameId : game.id, playerId:player.id},reload);
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

    $scope.getChoices = function(){
      return $scope.getCurrentPlayer().choices;
    };

    $scope.getImagePath = function(card) {
      if(card && card.name) {
        return '/images/' + card.name.toLowerCase().replace(/\s/g, '') + '.jpg';
      } else {
        return '/images/empty-card-back.jpg';
      }
    };

    $scope.isCardInPlay = function(card){
      return Object.keys(play)
          .map(function(i){play[i].id})
          .indexOf(card.id) < 0;
    };

    var reload = function(){
      /*var Game = $resource(baseUrl+'/dominion/:gameId');

      Game.get({gameId : gameId}, function(response){
        updateData(response, playerId);
      });*/
      $route.reload();
    };

  });
