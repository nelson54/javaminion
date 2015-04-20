"use strict";var baseUrl="";angular.module("dominionFrontendApp",["ngAnimate","ngAria","ngCookies","ngMessages","ngResource","ngRoute","ngSanitize","ngTouch","js-data"]).config(["$routeProvider",function(a){a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl",resolve:{baseUrl:function(){return baseUrl},recommendedCards:["$q","$http",function(a,b){var c=a.defer();return b.get(baseUrl+"/dominion/recommended").success(function(a){c.resolve(a)}),c.promise}]}}).when("/games/:game",{templateUrl:"views/game.html",controller:"GameCtrl",resolve:{game:["$route","$resource","$q",function(a,b,c){var d=c.defer(),e=b(baseUrl+"/dominion/:gameId"),f=a.current.params.game;return e.get({gameId:f},function(a){d.resolve(a)}),d.promise}],playerId:function(){return null},baseUrl:function(){return baseUrl}}}).when("/games/:game/players/:player",{templateUrl:"views/game.html",controller:"GameCtrl",resolve:{game:["$route","$resource","$q",function(a,b,c){var d=c.defer(),e=b(baseUrl+"/dominion/:gameId"),f=a.current.params.game;return e.get({gameId:f},function(a){d.resolve(a)}),d.promise}],playerId:["$route",function(a){return a.current.params.player}],baseUrl:function(){return baseUrl}}}).otherwise({redirectTo:"/"})}]).factory("GameData",["DS",function(a){return a.defineResource("dominion")}]).config(["DSProvider",function(a){a.defaults.basePath=baseUrl}]).config(["$resourceProvider",function(a){a.defaults.stripTrailingSlashes=!1}]),angular.module("dominionFrontendApp").controller("MainCtrl",["$scope","$http","$resource","$route","baseUrl","recommendedCards",function(a,b,c,d,e,f){var g=c(e+"/dominion/");a.games=[],a.cards="First Game",a.newGame=!1,a.playerNumbers=[2,3,4],a.recommendedCards=f,a.players=[{id:0,ai:!0},{id:1,ai:!0}],a.setCards=function(b){a.cards=b},a.getGames=function(){return b.get(e+"/dominion/").success(function(b){a.games=b})},a.createGame=function(){var b=new g({cardSet:a.cards,players:a.players,count:a.players.length+1});b.$save(b,d.reload)},a.updatePlayerCount=function(b){for(a.players=[],b;1!=b;b--)a.players.push({id:b,ai:!0})},a.getGames()}]),angular.module("dominionFrontendApp").controller("GameCtrl",["$scope","$http","$resource","$route","$timeout","GameData","game","playerId","baseUrl",function(a,b,c,d,e,f,g,h,i){var j,k,l,m,n,o,p,q,r,s=g.id;a.test=!1,a.game=g;var t=function(){!l||"WAITING_FOR_OPPONENT"!==l.currentTurn.phase&&"WAITING_FOR_CHOICE"!==l.currentTurn.phase||e(function(){v(s)},1e3)},u=function(b){k=a.players=b.players,j=a.logs=b.logs,h&&(l=a.player=b.players[h],m=a.hand=b.players[h].hand,n=a.deck=b.players[h].deck,o=a.turn=b.players[h].currentTurn,q=a.discard=b.players[h].discard,p=a.play=b.turn.play,b.players[h]&&b.players[h].choices&&(r=a.choice=b.players[h].choices[0])),a.commonComparator=function(a,b){return a.kingdomSortOrder==b.kingdomSortOrder?0:a.kingdomSortOrder>b.kingdomSortOrder?1:-1},a.kingdomComparator=function(a,b){try{return a.cost.money==b.cost.money?0:a.cost.money>b.cost.money?1:-1}catch(c){return 1}},a.commonCards=[],Object.keys(b.kingdom.cardMarket).filter(function(a){return!b.kingdom.cardMarket[a][0].isKingdom}).forEach(function(c){var d=b.kingdom.cardMarket[c],e=d[0];e.remaining=d.length,a.commonCards.push(e)}),a.kingdomCards=[],Object.keys(b.kingdom.cardMarket).filter(function(a){return b.kingdom.cardMarket[a][0].isKingdom}).forEach(function(c){var d=b.kingdom.cardMarket[c],e=d[0];e.remaining=d.length,a.kingdomCards.push(e)}),t()};a.shuffle=function(){var a=c(i+"/dominion/:gameId/:playerId/shuffle");a.get({gameId:g.id,playerId:h},function(a){u(a)})},a.drawHand=function(){var a=c(i+"/dominion/:gameId/:playerId/draw");a.get({gameId:g.id,playerId:h},u)},a.discardHand=function(){var a=c(i+"/dominion/:gameId/:playerId/discard");a.get({gameId:g.id,playerId:h},u)},a.canAfford=function(a){return l&&o.money>=a.cost.money},a.purchase=function(a){var b=c(i+"/dominion/:gameId/:playerId/purchase"),d=new b(a);d.$save({gameId:g.id,playerId:h},u)},a.playCard=function(a){var b=c(i+"/dominion/:gameId/:playerId/play"),d=new b(a);d.$save({gameId:g.id,playerId:h},u)},a.nextPhase=function(a){var b=c(i+"/dominion/:gameId/next-phase"),d=new b(a);d.$save({gameId:g.id},u)},a.selectCard=function(b){"ACTION"===o.phase?a.playCard(b):"WAITING_FOR_CHOICE"===o.phase?a.choose(g,l,r,b):"BUY"===o.phase&&a.purchase(b)},a.choose=function(a,b,d,e){var f=c(i+"/dominion/:gameId/:playerId/choice"),g=new f;switch(g.targetChoice=d.id,d.expectedAnswerType){case"CARD":g.card=e;break;case"YES_OR_NO":g.yes=e}"CARD"==d.expectedAnswerType,g.$save({gameId:a.id,playerId:b.id},u)},a.chooseDone=function(a,b,d){var e=c(i+"/dominion/:gameId/:playerId/choice"),f=new e;f.targetChoice=d.id,f.done=!0,f.$save({gameId:a.id,playerId:b.id},u)},a.isCurrentPlayer=function(a){return a.id===h},a.isActivePlayer=function(a){return h===a.id},a.hasCurrentPlayer=function(){return g&&g.players&&g.players.hasOwnProperty(h)},a.getCurrentPlayer=function(){return a.hasCurrentPlayer()?k[h]:void 0},a.hasCardType=function(a,b){b.cardTypes.includes(a)},a.getChoices=function(){return a.getCurrentPlayer().choices},a.getImagePath=function(a){return a&&a.name?"/images/cards/"+a.name.toLowerCase().replace(/\s/g,"")+".jpg":"/images/empty-card-back.jpg"},a.isCardInPlay=function(a){return Object.keys(p).map(function(a){p[a].id}).indexOf(a.id)<0},a.numberOfCardsInKingdom=function(a){return g.kingdom.cardMarket[a].length},a.isOption=function(a){return r&&r.options&&r.options.indexOf(a)>=0};var v=function(){c(i+"/dominion/:gameId");f.find(s,{bypassCache:!0}).then(function(a){u(a,h)})};u(g)}]);