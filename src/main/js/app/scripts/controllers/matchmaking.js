'use strict';

angular.module('dominionFrontendApp')
  .controller('MatchesCtrl', function ($scope, $http, $resource, $route, baseUrl, recommendedCards, jwtService) {

    $http.defaults.headers.common.Authorization = jwtService.getBearer();
    let matchHttpConfig = {
      headers: {'Authorization': jwtService.getBearer()}
    };

    let Match = $resource(baseUrl + '/dominion/matches',
      {
      },
      {
        get: matchHttpConfig,
        save: {
          method: 'post',
          headers: matchHttpConfig.headers
        },
        put: matchHttpConfig,
        post: matchHttpConfig,
        query: {
          method: 'get',
          headers: matchHttpConfig.headers
        },
        join: {
          url: '/dominion/matches/:matchId',
          action: 'join',
          method: 'patch',
          headers: matchHttpConfig.headers,
        }
    });

    $scope.games = [];

    $scope.cards = 'First Game';
    $scope.newGame = false;
    $scope.playerNumbers = [2, 3, 4];
    $scope.recommendedCards = recommendedCards;
    $scope.players = [{id: 0, ai: true}, {id: 1, ai: true}];

    $scope.filters = {
      waitingForOpponent: true,
      finished: false,
      inProgress: false,
    };

    $scope.setCards = function (cards) {
      $scope.cards = cards;
    };

    $scope.getMatches = function () {
      return Match.query($scope.filters).$promise;
    };

    $scope.createGame = function () {
      let count = $scope.players.length,
      numberOfAiPlayers = $scope.players.filter((player)=> player.ai).length;

      let match = new Match({
        cards: $scope.cards.trim(),
        numberOfHumanPlayers: count - numberOfAiPlayers,
        numberOfAiPlayers: numberOfAiPlayers,
        count: $scope.players.length + 1
      });

      match.$save()
        .then($route.reload, $route.reload);
    };

    $scope.joinGame = function(matchObj) {
      let match = new Match();
      match.$join({matchId: matchObj.id});
    };

    $scope.cancel = function(){
    
    };

    $scope.updatePlayerCount = function (count) {
      $scope.players = [];
      for (count; count !== 1; count--) {
        $scope.players.push({id: count, ai: true});
      }
    };

    $scope.updateMatches = ()=> {
      $scope.getMatches().then((response)=> {
        $scope.games = response.content;
      });
    };

    $scope.updateMatches();

  });
