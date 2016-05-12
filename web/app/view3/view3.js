'use strict';

angular.module('myApp.view3', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view3', {
                    templateUrl: 'app/view3/view3.html',
                    controller: 'View3Ctrl',
                    isUser: 'true',
                });

            }])

        .controller('View3Ctrl', function ($http, $scope) {


            $scope.runit = function () {
                $http({method: "GET",
                    url: "/SafeLuft/api/demouser/ticket/" + document.getElementById("ticketHolder").value
                })
                        .then(function successCallback(response) {
                            $scope.res = response.data;
                            $scope.isFound = true;
                        });
            };

        });