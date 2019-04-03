'use strict';

let dominionFrontendApp = angular.module('dominionFrontendApp');

dominionFrontendApp.component('login', {
  template: `
    <div class="form" class="navbar-form" ng-if="isLoggedIn()">
      <input type="text" id="username" ng-model="user.username" class="form-control mr-sm-2" placeholder="Username" aria-label="Username">
      <input type="password" id="password" ng-model="user.password" class="form-control mr-sm-2" placeholder="Password" aria-label="Password">
      <button ng-click="login()">Login</button>
    </div>`,
  resolve: {
    userId: function($route, UserServiceFactory, $q){
      let defer = $q.defer();
      let userService = new UserServiceFactory();

      userService.get(function(response){
        defer.resolve(response.id);
      });

      return defer.promise;
    }
  },
  controller: function($http, $resource, $route, $location, $q, baseUrl, jwtService, userId) {

    let Authentication = $resource(baseUrl+'/api/authentication');

    this.user = new Authentication({});

    this.isLoggedIn = ()=>{
      return userId > -1;
    };

    this.login = ()=>{
      this.user.$save().then(function(response) {
        jwtService.setToken(response.token);
        $location.path('/games');
      });
    };
  }
});
