'use strict';

/**
 * @ngdoc overview
 * @name dominionFrontendApp
 * @description
 * # dominionFrontendApp
 *
 * Main module of the application.
 */
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
        controller: 'MainCtrl'
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
          playerId : function(){return null}
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
