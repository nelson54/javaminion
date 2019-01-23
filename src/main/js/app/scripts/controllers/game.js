'use strict';

angular.module('dominionFrontendApp')
  .controller('GameCtrl', function ($scope, $http, $resource, $route, $interval, game, playerId, baseUrl, jwtService) {

    let gameId = $route.current.params.game,
      logs,
      players,
      player,
      hand,
      deck,
      turn,
      play,
      discard,
      choice,
      playBack = false,
      playBackTurn = 0,
      playBackPointer = 0;


    $scope.playBackTurn = playBackTurn;
    $scope.test = true;
    $scope.game = game;

    $scope.playBackGame = function(){
      playBack = true;
    };

    $scope.playTurn = function(t, i) {
      let playTurn = $scope.game.pastTurns[t];


      if(!playTurn || playTurn.play && playTurn.play.length < i){
        playBackTurn++;
        playBackPointer = 0;
      } else if(playTurn.play && playTurn.play.length) {
        $scope.play.append(playTurn.play[i]);
      }

    };

    let updateData = function(game){
      $scope.game = game;
      players = $scope.players = game.players;
      logs = $scope.logs = game.logs;
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
        if(c1.kingdomSortOrder === c2.kingdomSortOrder){
          return 0;
        } else if (c1.kingdomSortOrder > c2.kingdomSortOrder) {
          return 1;
        } else {
          return -1;
        }
      };

      $scope.kingdomComparator = function(c1, c2){
        try {
          if (c1.cost.money === c2.cost.money) {
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
          .filter((id)=> !game.kingdom.cardMarket[id][0].isKingdom )
          .forEach((id) => {
          let stack = game.kingdom.cardMarket[id],
            card = stack[0];
          card.remaining = stack.length;
          $scope.commonCards.push(card);
          });

      $scope.kingdomCards = [];
      Object.keys(game.kingdom.cardMarket)
          .filter((id) => game.kingdom.cardMarket[id][0].isKingdom)
          .forEach((id) => {
            let stack = game.kingdom.cardMarket[id],
              card = stack[0];
            card.remaining = stack.length;
            $scope.kingdomCards.push(card);
          });
      //$scope.$digest();
    };

    $scope.shuffle = function(){
      let Game = $resource(baseUrl+'/dominion/:gameId/shuffle');
      Game.get({gameId : game.id, playerId : playerId}, function(game){
        updateData(game);
      });
    };

    $scope.drawHand = function(){
      let Game = $resource(baseUrl+'/dominion/:gameId/draw');
      Game.get({gameId : game.id, playerId : playerId}, updateData);
    };

    $scope.discardHand = function(){
      let Game = $resource(baseUrl+'/dominion/:gameId/discard');
      Game.get({gameId : game.id, playerId : playerId}, updateData);
    };

    $scope.canAfford = function(card){
      return player && turn.money >= card.cost.money;
    };

    $scope.purchase = function(card){
      let Purchase = $resource(baseUrl+'/dominion/:gameId/purchase',
        {},
        {
          get: {
            headers: {'Authorization': jwtService.getBearer()}
          }
        }
      );

      let purchase = new Purchase(card);

      purchase.$save({gameId : game.id, playerId : playerId},updateData);
    };

    $scope.playCard = function(card){
      let Play = $resource(baseUrl+'/dominion/:gameId/play',
        {},
        {post: {headers: {'Authorization': jwtService.getBearer()}}});

      let play = new Play(card);

      play.$save({gameId : game.id, playerId : playerId},updateData);
    };

    $scope.nextPhase = function(card){
      let EndPhase = $resource(baseUrl+'/dominion/:gameId/next-phase',
        {},
        {post:
            {
              headers: {'Authorization': jwtService.getBearer()}
            }
        });

      let endPhase = new EndPhase(card);

      endPhase.$save({gameId : game.id}, updateData);
    };

    $scope.selectCard = function(card){
      if(turn.phase === 'ACTION'){
        $scope.playCard(card);
      } else if (turn.phase === 'WAITING_FOR_CHOICE'){
        $scope.choose(game, player, choice, card);
      } else if (turn.phase === 'BUY'){
        $scope.purchase(card);
      }
    };

    $scope.choose = function(game, player, choose, response){
      let Choice = $resource(baseUrl+'/dominion/:gameId/choice',
        {},
        {
          get: {
              headers: {'Authorization': jwtService.getBearer()}
          }
        }
       );

      var choice = new Choice();

      choice.targetChoice = choose.id;

      switch (choose.expectedAnswerType) {
        case 'CARD':
          choice.card = response;
          break;
        case 'YES_OR_NO':
          choice.yes = response;
          break;
      }

      choice.$save({gameId : game.id, playerId:player.id},updateData);
    };

    $scope.chooseDone = function(game, player, choose){
      let Choice = $resource(baseUrl+'/dominion/:gameId/choice',
        {},
        {get: {headers: {'Authorization': jwtService.getBearer()}}});

      let choice = new Choice();
      choice.targetChoice = choose.id;
      choice.done = true;

      choice.$save({gameId : game.id, playerId:player.id},updateData);
    };

    $scope.isCurrentPlayer = function(player) {
      return player.id === playerId;
    };

    $scope.isActivePlayer = function(player) {
      return playerId === player.id;
    };

    $scope.hasCurrentPlayer = function(){
      return game && game.players && game.players.hasOwnProperty(playerId);
    };

    $scope.getCurrentPlayer = function(){
      if ($scope.hasCurrentPlayer()) {
        return players[playerId];
      }
    };

    $scope.hasCardType = function(type, card){
      card.cardTypes.includes(type);
    };

    $scope.getChoices = function(){
      return $scope.getCurrentPlayer().choices;
    };

    $scope.getImagePath = function(card) {
      if(card && card.name) {
        return '/images/cards/' + card.name.toLowerCase().replace(/\s/g, '') + '.jpg';
      } else {
        return '/images/empty-card-back.jpg';
      }
    };

    $scope.isCardInPlay = function(card){
      return Object.keys(play)
          .map((i)=> play[i].id )
          .indexOf(card.id) < 0;
    };

    $scope.numberOfCardsInKingdom = function(name){
      return game.kingdom.cardMarket[name].length;
    };

    $scope.isOption = function(id){
      return choice && choice.options && choice.options.indexOf(id) >= 0;
    };

    $interval(() => {
      let Game = $resource(baseUrl+'/dominion/:gameId', {
        get: {
          headers: {'Authorization': jwtService.getBearer()}
        }
      });

      Game.get({gameId : gameId}).$promise
        .then(function(response){
          if(!playBack && player && (player.currentTurn.phase === 'WAITING_FOR_OPPONENT' || player.currentTurn.phase === 'WAITING_FOR_CHOICE') && !playBack && $scope.game.hashCode !== response.hashCode) {
            updateData(response, playerId);
            return;
          } else if(playBack) {
            $scope.playTurn(playBackTurn, playBackPointer);
            playBackPointer++;
          }
        });
    }, 2000);

    updateData(game);
  });
