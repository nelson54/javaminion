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
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/matchmaking.html',
        controller: 'MatchesCtrl',
        resolve : {
          recommendedCards: function($http, $resource, jwtService){

            $http.defaults.headers.common.Authorization = jwtService.getBearer();

            let RecommendedCards = $resource(baseUrl+'/dominion/recommended',
              {
              },
              {
                get: {
                  headers: {'Authorization': jwtService.getBearer()}
                }
              });

            return RecommendedCards.query();
          }
        }
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      })
      .when('/signup', {
        templateUrl: 'views/signup.html',
        controller: 'SignupCtrl'
      })
      .when('/games/:game', {
        templateUrl: 'views/game.html',
        controller: 'GameCtrl',
        resolve: {
          game : function($route, $http, $resource, $q, jwtService){
            let defer = $q.defer();
            let game = $route.current.params.game;

            let Game = $resource(baseUrl+'/dominion/:gameId',
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
          },
          playerId : function($route, UserServiceFactory, $q){
            let defer = $q.defer();
            let userService = new UserServiceFactory();
            userService.get(function(response){
              defer.resolve(response.id);
            });

            return defer.promise;
          }
        }
      })
      .when('/games/:game/players/:player', {
        templateUrl: 'views/game.html',
        controller: 'GameCtrl',
        resolve: {
          game : function($route, $resource, $q){
            var defer = $q.defer();
            var Game = $resource(baseUrl+'/dominion/:gameId');
            var game = $route.current.params.game;

            Game.get({gameId : game}, function(response){
              defer.resolve(response);
            });

            return defer.promise;
          },
          playerId : function($route) {
            return $route.current.params.player;
          }
        }
      })
      .otherwise({
        redirectTo: '/'
      });
  })
  .factory('GameData', function($q, $route, $resource, jwtService){
    let defer = $q.defer();
    let game = $route.current.params.game;

    let Game = $resource(baseUrl+'/dominion/:gameId',
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
  })
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
  .value('BaseUrlProvider', baseUrl)
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
