'use strict';

let dominionFrontendApp = angular.module('dominionFrontendApp');

dominionFrontendApp.component('login', {
  template: `
    <div class="form">
      <p>
        <label for="username">Username</label><br>
        <input type="text" id="username" ng-model="user.username">
      </p>
      <p>
        <label for="password">password</label><br>
        <input type="password" id="password" ng-model="user.password">
      </p>
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
