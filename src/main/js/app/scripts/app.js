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
          baseUrl: function() {
            return baseUrl;
          },
          recommendedCards: function($http){
            return $http.get(baseUrl+'/dominion/recommended')
              .then((response)=> response.data);
          }
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
          game : function($route, $resource, $q){
            var defer = $q.defer();
            var Game = $resource(baseUrl+'/dominion/:gameId');
            var game = $route.current.params['game'];

            Game.get({gameId : game}, function(response){
              defer.resolve(response);
            });
            return defer.promise
          },
          playerId : function($route, UserService, $q){
            var defer = $q.defer();
            var game = $route.current.params['game'];

            UserService.get(function(response){
              defer.resolve(response.id);
            });

            return defer.promise;
          },
          baseUrl: function() {
            return baseUrl;
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
            var game = $route.current.params['game'];

            Game.get({gameId : game}, function(response){
              defer.resolve(response)
            });

            return defer.promise
          },
          playerId : function($route) {
            return $route.current.params['player'];
          },
          baseUrl : function() {
            return baseUrl;
          }
        }
      })
      .otherwise({
        redirectTo: '/'
      });
  })
  .factory('GameData', function(){
    return {}.defineResource('dominion');
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
    }


  })

  .factory('UserServiceFactory', ["$resource", "jwtService", function($resource, jwtService){
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
