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
           $scope.runit = function () {
                $http({method: "GET",
                    url: "/SafeLuft/api/admin/score/" + document.getElementById("scoreLine").value
                })
                        .then(function successCallback(response) {
                            $scope.res = response.data;
                            $scope.isFound = true;
                        });
            };

        });