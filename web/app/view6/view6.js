'use strict';

angular.module('myApp.view6', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view6', {
                    templateUrl: 'app/view6/view6.html',
                    controller: 'View6Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View6Ctrl', function ($http, $scope) {
            $scope.newuser = {
            };
            $scope.postnewuser = function () {
                $http({
                    method: 'POST',
                    url: 'api/newuser',
                    data: $scope.newuser
                }).then(function successCallback(res) {
                    $scope.isRegistered = true;
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });
            };

        });