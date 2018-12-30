'use strict';

angular.module('dominionFrontendApp')
  .controller('SignupCtrl', function ($scope, $http, $resource, $route, baseUrl) {
    let self = $scope;

    self.signup = function() {
      let obj = {
        username: self.username,
        firstname: self.firstname,
        email: self.email,
        password: self.password
      };

      console.log(JSON.stringify(obj));
      var User = $resource(baseUrl+'/api/register');

      let user = new User(obj);

      user.$save().then(function() {
        console.log(`Saved! {${JSON.stringify(user)}}`)
      });
    }
  });
