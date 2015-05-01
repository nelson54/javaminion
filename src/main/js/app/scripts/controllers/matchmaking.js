'use strict';

angular.module('dominionFrontendApp')
  .controller('GameCtrl', function ($scope, $http, $resource, $route, $timeout, GameData, game, playerId, baseUrl) {

    var availableGames = [];

    function getOpenGames(){
      var Game = $resource(baseUrl+'/dominion/');
      Game.get({status: 'OPEN'}, function(game){
        updateData(game);
      });
    }

    function join(id){
      var Game = $resource(baseUrl+'/dominion/');
      Game.post({game: id}, function(game){
        updateData(game);
      });
    }

  });
