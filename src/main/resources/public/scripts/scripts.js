'use strict';

/**
 * @ngdoc overview
 * @name dominionFrontendApp
 * @description
 * # dominionFrontendApp
 *
 * Main module of the application.
 */

//var baseUrl = "http://localhost:9001";
var baseUrl = "";

angular
  .module('dominionFrontendApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(["$routeProvider", function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/matchmaking.html',
        controller: 'MatchesCtrl',
        resolve : {
          baseUrl: function() {
            return baseUrl;
          },
          recommendedCards: ["$http", "$resource", "jwtService", function($http, $resource, jwtService){

            $http.defaults.headers.common.Authorization = jwtService.getBearer();

            let RecommendedCards = $resource(baseUrl+'/com.github.nelson54.dominion/recommended',
              {
              },
              {
                get: {
                  headers: {'Authorization': jwtService.getBearer()}
                }
              });

            return RecommendedCards.query();
          }]
        }
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        resolve : {
          baseUrl: function() {
            return baseUrl;
          }
        }
      })
      .when('/signup', {
        templateUrl: 'views/signup.html',
        controller: 'SignupCtrl',
        resolve : {
          baseUrl: function() {
            return baseUrl;
          }
        }
      })
      .when('/games/:game', {
        templateUrl: 'views/game.html',
        controller: 'GameCtrl',
        resolve: {
          game : ["$route", "$http", "$resource", "$q", "jwtService", function($route, $http, $resource, $q, jwtService){
            let defer = $q.defer();
            let game = $route.current.params.game;

            let Game = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId',
              {
              },
              {
                get: {
                  headers: {'Authorization': jwtService.getBearer()}
                }
              });

            Game.get({gameId : game}, function(response){
              defer.resolve(response);
            });

            return defer.promise;
          }],
          playerId : ["$route", "UserServiceFactory", "$q", function($route, UserServiceFactory, $q){
            let defer = $q.defer();
            let userService = new UserServiceFactory();
            userService.get(function(response){
              defer.resolve(response.id);
            });

            return defer.promise;
          }],
          baseUrl: function() {
            return baseUrl;
          }
        }
      })
      .when('/games/:game/players/:player', {
        templateUrl: 'views/game.html',
        controller: 'GameCtrl',
        resolve: {
          game : ["$route", "$resource", "$q", function($route, $resource, $q){
            var defer = $q.defer();
            var Game = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId');
            var game = $route.current.params.game;

            Game.get({gameId : game}, function(response){
              defer.resolve(response);
            });

            return defer.promise;
          }],
          playerId : ["$route", function($route) {
            return $route.current.params.player;
          }],
          baseUrl : function() {
            return baseUrl;
          }
        }
      })
      .otherwise({
        redirectTo: '/'
      });
  }])
  .factory('GameData', ["$q", "$route", "$resource", "jwtService", function($q, $route, $resource, jwtService){
    let defer = $q.defer();
    let game = $route.current.params.game;

    let Game = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId',
      {
      },
      {
        get: {
          headers: {'Authorization': jwtService.getBearer()}
        }
      });

    Game.get({gameId : game}, function(response){
      defer.resolve(response);
    });

    return defer.promise;
  }])
  .factory('jwtService', function(){
    return {
      setToken(token) {
        localStorage.setItem('jwtToken', token);
      },
      getBearer() {
        let token = localStorage.getItem('jwtToken');
        if(token) {
          return `Bearer ${token}`;
        }
        return null;
        
      }
    };


  })

  .factory('UserServiceFactory', ['$resource', 'jwtService', function($resource, jwtService){
    return function() {
      return $resource(baseUrl + '/api/account', {}, {
        get: {
          headers: {'Authorization': jwtService.getBearer()}
        },
        post: {
          headers: {'Authorization': jwtService.getBearer()}
        },
        patch: {
          headers: {'Authorization': jwtService.getBearer()}
        }
      });
    };
  }])
  .config(['$resourceProvider', function($resourceProvider) {
    // Don't strip trailing slashes from calculated URLs
    $resourceProvider.defaults.stripTrailingSlashes = false;
  }]);

'use strict';

/**
 * @ngdoc function
 * @name dominionFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the dominionFrontendApp
 */
angular.module('dominionFrontendApp')
  .controller('MainCtrl', ["$scope", "$http", "$resource", "$route", "baseUrl", "recommendedCards", function ($scope, $http, $resource, $route, baseUrl, recommendedCards) {

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
}]);

'use strict';

angular.module('dominionFrontendApp')
  .controller('GameCtrl', ["$scope", "$http", "$resource", "$route", "$interval", "game", "playerId", "baseUrl", "jwtService", function ($scope, $http, $resource, $route, $interval, game, playerId, baseUrl, jwtService) {

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
      let Game = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId/shuffle');
      Game.get({gameId : game.id, playerId : playerId}, function(game){
        updateData(game);
      });
    };

    $scope.drawHand = function(){
      let Game = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId/draw');
      Game.get({gameId : game.id, playerId : playerId}, updateData);
    };

    $scope.discardHand = function(){
      let Game = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId/discard');
      Game.get({gameId : game.id, playerId : playerId}, updateData);
    };

    $scope.canAfford = function(card){
      return player && turn.money >= card.cost.money;
    };

    $scope.purchase = function(card){
      let Purchase = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId/purchase',
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
      let Play = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId/play',
        {},
        {post: {headers: {'Authorization': jwtService.getBearer()}}});

      let play = new Play(card);

      play.$save({gameId : game.id, playerId : playerId},updateData);
    };

    $scope.nextPhase = function(card){
      let EndPhase = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId/next-phase',
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
      let Choice = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId/choice',
        {},
        {
          post: {
              headers: {'Authorization': jwtService.getBearer()}
          }
        }
       );

      let choice = new Choice();

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
      let Choice = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId/choice',
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
      let Game = $resource(baseUrl+'/com.github.nelson54.dominion/:gameId', {
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
  }]);

'use strict';

angular.module('dominionFrontendApp')
  .controller('UserCtrl', ["$scope", "$http", "$resource", "UserServiceFactory", function ($scope, $http, $resource, UserServiceFactory) {
    let UserService = UserServiceFactory();
    UserService.get(function(account){
      $scope.user = account.user;
    });

  }]);

'use strict';

angular.module('dominionFrontendApp')
  .controller('LoginCtrl', ["$scope", "$http", "$resource", "$route", "$location", "baseUrl", "jwtService", function ($scope, $http, $resource, $route, $location, baseUrl, jwtService) {
    let self = $scope;
    let Authentication = $resource(baseUrl+'/api/authentication');

    self.user = new Authentication({});

    self.login = function() {
      self.user.$save().then(function(response) {
        jwtService.setToken(response.token);
        $location.path('/games');
      });
    };


  }]);

'use strict';

angular.module('dominionFrontendApp')
  .controller('SignupCtrl', ["$scope", "$http", "$resource", "$route", "$location", "baseUrl", function ($scope, $http, $resource, $route, $location, baseUrl) {
    let self = $scope;

    self.signup = function() {
      let obj = {
        username: self.username,
        firstname: self.firstname,
        email: self.email,
        password: self.password
      };

      console.log(JSON.stringify(obj));
      var User = $resource(baseUrl+'/api/register');

      let user = new User(obj);

      user.$save().then(function() {
        console.log(`Saved! {${JSON.stringify(user)}}`)
      });

      $location.path('/login');
    }
  }]);

'use strict';

angular.module('dominionFrontendApp')
  .controller('MatchesCtrl', ["$scope", "$http", "$resource", "$route", "baseUrl", "recommendedCards", "jwtService", function ($scope, $http, $resource, $route, baseUrl, recommendedCards, jwtService) {
    $http.defaults.headers.common.Authorization = jwtService.getBearer();
    let matchHttpConfig = {
      headers: {'Authorization': jwtService.getBearer()}
    };

    let Match = $resource(baseUrl + '/com.github.nelson54.dominion/matches',
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
          url: '/com.github.nelson54.dominion/matches/:matchId',
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

    $scope.setCards = function (cards) {
      $scope.cards = cards;
    };

    $scope.getMatches = function () {
      return Match.query().$promise;
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

    $scope.getMatches().then((response)=> {
      $scope.games = response.content;
    });

  }]);
