'use strict';

let dominionFrontendApp = angular.module('dominionFrontendApp');

dominionFrontendApp.component('login', {
  template: `
    <div class="form" class="navbar-form" ng-if="isLoggedIn()">
      <input type="text" id="username" ng-model="user.username" class="form-control mr-sm-2" placeholder="Username" aria-label="Username">
      <input type="password" id="password" ng-model="user.password" class="form-control mr-sm-2" placeholder="Password" aria-label="Password">
      <button ng-click="login()">Login</button>
    </div>`,
  controller: function($http, $resource, $route, $location, $q, UserServiceFactory, baseUrl, jwtService) {

    let Authentication = $resource(baseUrl+'/api/authentication');
    this.userId = -1;
    this.user = new Authentication({});

    this.isLoggedIn = ()=>{
      return this.userId > -1;
    };

    this.login = ()=>{
      this.user.$save().then(function(response) {
        jwtService.setToken(response.token);
        $location.path('/games');
      });
    };

    (() => {
      let defer = $q.defer();
      let userService = new UserServiceFactory();

      userService.get(
        (response) => defer.resolve(response.id),
        () => defer.resolve(-1)
      );

      return defer.promise;
    })().then((userId) => {
      this.userId = userId;
    });
  }
});
