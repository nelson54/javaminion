'use strict';

angular.module('dominionFrontendApp')
  .controller('MatchesCtrl', function ($scope, $http, $resource, $route, baseUrl, recommendedCards) {

    var Game = $resource(baseUrl + '/dominion/');

    $scope.games = [];

    $scope.cards = 'First Game';
    $scope.newGame = false;
    $scope.playerNumbers = [2, 3, 4];
    $scope.recommendedCards = recommendedCards;
    $scope.players = [{id: 0, ai: true}, {id: 1, ai: true}];

    $scope.setCards = function (cards) {
      $scope.cards = cards;
    };

    $scope.getMatches = function () {
      return $http.get(baseUrl + '/dominion/matches')
        .success(function (response) {
          $scope.games = response;
        });
    };

    $scope.createGame = function () {
      var game = new Game({
        cardSet: $scope.cards,
        players: $scope.players,
        count: $scope.players.length + 1
      });
      game.$save(game, $route.reload);
    };

    $scope.joinGame = function() {

    };

    $scope.updatePlayerCount = function (count) {
      $scope.players = [];
      for (count; count != 1; count--) {
        $scope.players.push({id: count, ai: true});
      }
    };

    $scope.getMatches();

  });
