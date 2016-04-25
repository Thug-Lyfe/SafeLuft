'use strict';

angular.module('myApp.view5', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view5', {
                    templateUrl: 'app/view5/view5.html',
                    controller: "View5Ctrl"
                });
            }])

        .controller("View5Ctrl", function ($http, $scope) {
            $http({
                method: 'GET',
                url: "/CA-3/api/admin/users"
            }).then(function (response){
                $scope.users = response.data.users;
            });
            
            $scope.delete = function(username, index){
                $http({
                method: 'Delete',
                url: "/CA-3/api/admin/user/"+username
            }).then(function (response){
                $scope.users.splice(index,1);
            });
            };
            
        });