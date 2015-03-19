"use strict";var baseUrl="";angular.module("dominionFrontendApp",["ngAnimate","ngAria","ngCookies","ngMessages","ngResource","ngRoute","ngSanitize","ngTouch"]).config(["$routeProvider",function(a){a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl"}).when("/games/:game",{templateUrl:"views/game.html",controller:"GameCtrl",resolve:{game:["$route","$resource","$q",function(a,b,c){var d=c.defer(),e=b(baseUrl+"/dominion/:gameId"),f=a.current.params.game;return e.get({gameId:f},function(a){d.resolve(a)}),d.promise}],playerId:function(){return null},baseUrl:baseUrl}}).when("/games/:game/players/:player",{templateUrl:"views/game.html",controller:"GameCtrl",resolve:{game:["$route","$resource","$q",function(a,b,c){var d=c.defer(),e=b(baseUrl+"/dominion/:gameId"),f=a.current.params.game;return e.get({gameId:f},function(a){d.resolve(a)}),d.promise}],playerId:["$route",function(a){return a.current.params.player}],baseUrl:baseUrl}}).otherwise({redirectTo:"/"})}]).config(["$resourceProvider",function(a){a.defaults.stripTrailingSlashes=!1}]),angular.module("dominionFrontendApp").controller("MainCtrl",["$scope","$http","$resource","$route","baseUrl",function(a,b,c,d,e){var f=c(e+"/dominion/");a.games=[],a.getGames=function(){return b.get(e+"/dominion/").success(function(b){a.games=b})},a.createGame=function(){var b=new f({});b.$save(a.getGames,d.reload)},a.getGames()}]),angular.module("dominionFrontendApp").controller("GameCtrl",["$scope","$http","$resource","$route","$interval","game","playerId","baseUrl",function(a,b,c,d,e,f,g,h){var i,j,k,l,m,n,o,p=f.id;a.test=!1,a.game=f;var q=function(){!o&&i&&"WAITING_FOR_OPPONENT"===i.currentTurn.phase?o=e(function(){u(p)},1e3):o&&"WAITING_FOR_OPPONENT"!==i.currentTurn.phase&&(e.cancel(o),o=void 0)},r=function(b){i=a.player=b.players[g],j=a.hand=b.players[g].hand,k=a.deck=b.players[g].deck,l=a.turn=b.players[g].currentTurn,n=a.discard=b.players[g].discard,m=a.play=b.turn.play,a.commonCards=Object.keys(b.kingdom.cardMarket).filter(function(a){return!b.kingdom.cardMarket[a][0].isKingdom}).sort(s),a.kingdomCards=Object.keys(b.kingdom.cardMarket).filter(function(a){return b.kingdom.cardMarket[a][0].isKingdom}).sort(t),q()};g&&r(f);var s=function(a,b){var c=f.kingdom.cardMarket[a][0],d=f.kingdom.cardMarket[b][0];return c.kingdomSortOrder==d.kingdomSortOrder?0:c.kingdomSortOrder>d.kingdomSortOrder?1:-1},t=function(a,b){var c=f.kingdom.cardMarket[a][0],d=f.kingdom.cardMarket[b][0];return c.cost.money==d.cost.money?0:c.cost.money>d.cost.money?1:-1};a.shuffle=function(){var a=c(h+"/dominion/:gameId/:playerId/shuffle");a.get({gameId:f.id,playerId:g},function(){u()})},a.drawHand=function(){var a=c(h+"/dominion/:gameId/:playerId/draw");a.get({gameId:f.id,playerId:g},function(){u()})},a.discardHand=function(){var a=c(h+"/dominion/:gameId/:playerId/discard");a.get({gameId:f.id,playerId:g},u)},a.canAfford=function(a){return i&&f.turn.money>=a.cost.money},a.purchase=function(a){var b=c(h+"/dominion/:gameId/:playerId/purchase"),d=new b(a);d.$save({gameId:f.id,playerId:g},u)},a.playCard=function(a){var b=c(h+"/dominion/:gameId/:playerId/play"),d=new b(a);d.$save({gameId:f.id,playerId:g},u)},a.nextPhase=function(a){var b=c(h+"/dominion/:gameId/next-phase"),d=new b(a);d.$save({gameId:f.id},u)},a.choose=function(a,b,d,e){var f=c(h+"/dominion/:gameId/:playerId/choice"),g=new f;switch(g.targetChoice=d.id,d.expectedAnswerType){case"CARD":g.card=e;break;case"YES_OR_NO":g.isYesOrNo=e}"CARD"==d.expectedAnswerType,g.$save({gameId:a.id,playerId:b.id},u)},a.chooseDone=function(a,b,d){var e=c(h+"/dominion/:gameId/:playerId/choice"),f=new e;f.targetChoice=d.id,f.done=!0,f.$save({gameId:a.id,playerId:b.id},u)},a.isCurrentPlayer=function(a){return a.id===g},a.isActivePlayer=function(a){return f.turn.playerId===a.id},a.hasCurrentPlayer=function(){return f&&f.players&&f.players.hasOwnProperty(g)},a.getCurrentPlayer=function(){return a.hasCurrentPlayer()?a.game.players[g]:void 0},a.hasCardType=function(a,b){b.cardTypes.includes(a)},a.getChoices=function(){return a.getCurrentPlayer().choices},a.getImagePath=function(a){return a&&a.name?"/images/"+a.name.toLowerCase().replace(/\s/g,"")+".jpg":"/images/empty-card-back.jpg"};var u=function(){var a=c(h+"/dominion/:gameId");a.get({gameId:p},function(a){r(a,g)})}}]);