'use strict';

angular.module('dominionFrontendApp')
  .controller('SignupCtrl', function ($scope, $http, $resource, $route, baseUrl) {
    let self = $sope;

    self.signup = function() {
      let obj = {
        username: self.username,
        firstname: self.firstname,
        email: self.email,
        password: self.password
      };

      console.log(JSON.stringify(obj));
    }
  });
