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
                url: "api/admin/Services"
            }).then(function (response){
                $scope.Services = response.data;
            });
            
            $scope.delete = function(id, index){
                $http({
                method: 'Delete',
                url: "api/admin/Service/"+id
            }).then(function (response){
                $scope.Services = response.data;
                $scope.users.splice(index,1);
            });
            };
            
            $scope.newService = {
            };
            $scope.postNewService = function () {
                $http({
                    method: 'POST',
                    url: 'api/admin/Service',
                    data: $scope.newService
                }).then(function successCallback(res) {
                    $scope.isRegistered = true;
                    $scope.Services = res.data;
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });
            };
            
        });