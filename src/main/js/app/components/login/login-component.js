'use strict';

let dominionFrontendApp = angular.module('dominionFrontendApp');

dominionFrontendApp.component('login', {
  template: `
    <div class="form" class="navbar-form">
      <input type="text" id="username" ng-model="user.username" class="form-control mr-sm-2" placeholder="Username" aria-label="Username">
      <input type="password" id="password" ng-model="user.password" class="form-control mr-sm-2" placeholder="Password" aria-label="Password">
      <button ng-click="login()">Login</button>
    </div>`,
  controller: function($http, $resource, $route, $location, baseUrl, jwtService) {

    let Authentication = $resource(baseUrl+'/api/authentication');

    this.user = new Authentication({});

    this.isLoggedIn = ()=>{
      return !!jwtService.getBearer();
    };

    this.login = ()=>{
      this.user.$save().then(function(response) {
        jwtService.setToken(response.token);
        $location.path('/games');
      });
    };
  }
});
