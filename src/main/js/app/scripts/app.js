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
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        resolve : {
          baseUrl: function() {
            return baseUrl;
          },
          recommendedCards: function($q, $http){
            var defer = $q.defer();
            $http.get(baseUrl+'/dominion/recommended')
              .success(function(response){
                defer.resolve(response);
              });
            return defer.promise;
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
          playerId : function(){return null},
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
  .config(['$resourceProvider', function($resourceProvider) {
    // Don't strip trailing slashes from calculated URLs
    $resourceProvider.defaults.stripTrailingSlashes = false;
  }]);
