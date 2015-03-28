'use strict';

angular.module('dominionFrontendApp')
  .controller('GameCtrl', function ($scope, $http, $resource, $route, $timeout, game, playerId, baseUrl) {

    var gameId = game.id,
      players,
      player,
      hand,
      deck,
      turn,
      play,
      discard,
      choice;

    $scope.test = false;
    $scope.game = game;

    var repeat = function() {
      if (player &&
        (player.currentTurn.phase === 'WAITING_FOR_OPPONENT' ||
        player.currentTurn.phase === 'WAITING_FOR_CHOICE')
      ) {

        $timeout(function () {
          reload(gameId);//gameId);
        }, 1000);
      }
    };

    var updateData = function(game){
      players = $scope.players = game.players;

      if(playerId) {
        player = $scope.player = game.players[playerId];

        hand = $scope.hand = game.players[playerId].hand;
        deck = $scope.deck = game.players[playerId].deck;
        turn = $scope.turn = game.players[playerId].currentTurn;
        discard = $scope.discard = game.players[playerId].discard;
        play = $scope.play = game.turn.play;

        if(game.players[playerId] && game.players[playerId].choices){
          choice = $scope.choice = game.players[playerId].choices[0];
        }
      }

      $scope.commonComparator = function(c1, c2){
        if(c1.kingdomSortOrder == c2.kingdomSortOrder){
          return 0;
        } else if (c1.kingdomSortOrder > c2.kingdomSortOrder) {
          return 1;
        } else {
          return -1;
        }
      };

      $scope.kingdomComparator = function(c1, c2){
        try {
          if (c1.cost.money == c2.cost.money) {
            return 0;
          } else if (c1.cost.money > c2.cost.money) {
            return 1;
          } else {
            return -1;
          }
        } catch(e) {
          return 1;
        }
      };

      $scope.commonCards = [];
      Object.keys(game.kingdom.cardMarket)
          .filter(function(id){return !game.kingdom.cardMarket[id][0].isKingdom})
          .forEach(function(id){
          var stack = game.kingdom.cardMarket[id],
            card = stack[0];
          card.remaining = stack.length;
          $scope.commonCards.push(card);
          });

      $scope.kingdomCards = [];
      Object.keys(game.kingdom.cardMarket)
          .filter(function(id){return game.kingdom.cardMarket[id][0].isKingdom})
          .forEach(function(id){
            var stack = game.kingdom.cardMarket[id],
              card = stack[0];
            card.remaining = stack.length;
            $scope.kingdomCards.push(card);
          });
      //$scope.$digest();
      repeat();
    };

    $scope.shuffle = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId/:playerId/shuffle');
      Game.get({gameId : game.id, playerId : playerId}, function(game){
        updateData(game);
      });
    };

    $scope.drawHand = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId/:playerId/draw');
      Game.get({gameId : game.id, playerId : playerId}, updateData);
    };

    $scope.discardHand = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId/:playerId/discard');
      Game.get({gameId : game.id, playerId : playerId}, updateData);
    };

    $scope.canAfford = function(card){
      return player && turn.money >= card.cost.money;
    };

    $scope.purchase = function(card){
      var Purchase = $resource(baseUrl+'/dominion/:gameId/:playerId/purchase');

      var purchase = new Purchase(card);

      purchase.$save({gameId : game.id, playerId : playerId},updateData);
    };

    $scope.playCard = function(card){
      var Play = $resource(baseUrl+'/dominion/:gameId/:playerId/play');

      var play = new Play(card);

      play.$save({gameId : game.id, playerId : playerId},updateData);
    }

    $scope.nextPhase = function(card){
      var EndPhase = $resource(baseUrl+'/dominion/:gameId/next-phase');

      var endPhase = new EndPhase(card);

      endPhase.$save({gameId : game.id},updateData);
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

      choice.$save({gameId : game.id, playerId:player.id},updateData);
    };

    $scope.chooseDone = function(game, player, choose){
      var Choice = $resource(baseUrl+'/dominion/:gameId/:playerId/choice');

      var choice = new Choice();
      choice.targetChoice = choose.id;
      choice.done = true;

      choice.$save({gameId : game.id, playerId:player.id},updateData);
    };

    $scope.isCurrentPlayer = function(player) {
      return player.id === playerId;
    };

    $scope.isActivePlayer = function(player) {
      return playerId === player.id;
    }

    $scope.hasCurrentPlayer = function(){
      return game && game.players && game.players.hasOwnProperty(playerId);
    };

    $scope.getCurrentPlayer = function(){
      if ($scope.hasCurrentPlayer())
        return players[playerId];
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

    $scope.numberOfCardsInKingdom = function(name){
      return game.kingdom.cardMarket[name].length;
    };


    var reload = function(){
      var Game = $resource(baseUrl+'/dominion/:gameId');

      Game.get({gameId : gameId}, function(response){
        updateData(response, playerId);
      });
    };

    updateData(game);
  });
