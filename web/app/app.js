'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngAutocomplete',
    'ngRoute',
    'ngAnimate',
    'angular-jwt',
    'ui.bootstrap',
    'myApp.security',
    'myApp.view1',
    'myApp.view2',
    'myApp.view3',
    'myApp.view4',
    'myApp.view5',
    'myApp.view6',
    'myApp.filters',
    'myApp.directives',
    'myApp.factories',
    'myApp.services',
    'ngTable'
])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.otherwise({redirectTo: '/view1'});
            }])
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('AuthInterceptor');
        })



        .controller("SirchController", function ($scope, $http) {
            $scope.results = false;

            $scope.expand = function(id){
                document.getElementById('box'+id).click();
            };

            $scope.findflights = function () {

                var year = $scope.flightdate.getFullYear();
                var month = $scope.flightdate.getMonth();
                var day = $scope.flightdate.getDate();
                $scope.date = new Date(year, month, day, 2);

                $http({
                    method: "GET",
                    url: "/SafeLuft/api/Service/" + $scope.flightfrom + "/" + $scope.date.toISOString() + "/" + $scope.flighttickets

                }).then(function successCallback(res) {
                    $scope.foundFlights = res.data;
                    $scope.allflights = [];
                    angular.forEach($scope.foundFlights, function (airline) {
                        var name = airline.airline;
                        angular.forEach(airline.flights, function (flight) {
                            flight.airline = name;
                            var date = new Date(flight.date);
                            var min = date.getMinutes();
                            if (min.toString().length == 1) {
                                min = "0" + min;
                            }
                            var h = date.getHours();
                            if (h.toString().length == 1) {
                                h = "0" + h;
                            }
                            var date2 = date;
                            date2.setMinutes(date.getMinutes() + flight.traveltime)
                            var min2 = date2.getMinutes();
                            if (min2.toString().length == 1) {
                                min2 = "0" + min2;
                            };
                            var h2 = date2.getHours();
                            if (h2.toString().length == 1) {
                                h2 = "0" + h2;
                            };
                            flight.time = h + ":" + min;
                            flight.arrival = h2 + ":" + min2;
                            
                            $scope.allflights.push(flight);
                        });
                    });

                    $scope.results = true;

                    if (res.data.error === "NO_SEARCH") {
                        $scope.isFound = false;
                        $scope.openErrorModal("Please input a valid search text");
                    }
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });
            };





        })
        .filter('time', function () {

            var conversions = {
                'ss': angular.identity,
                'mm': function (value) {
                    return value * 60;
                },
                'hh': function (value) {
                    return value * 3600;
                }
            };

            var padding = function (value, length) {
                var zeroes = length - ('' + (value)).length,
                        pad = '';
                while (zeroes-- > 0)
                    pad += '0';
                return pad + value;
            };

            return function (value, unit, format, isPadded) {
                var totalSeconds = conversions[unit || 'ss'](value),
                        hh = Math.floor(totalSeconds / 3600),
                        mm = Math.floor((totalSeconds % 3600) / 60),
                        ss = totalSeconds % 60;

                format = format || 'hh:mm:ss';
                isPadded = angular.isDefined(isPadded) ? isPadded : true;
                hh = isPadded ? padding(hh, 2) : hh;
                mm = isPadded ? padding(mm, 2) : mm;
                ss = isPadded ? padding(ss, 2) : ss;
                if(mm == 0){
                    return format.replace(/hh/, hh).replace(/mm/, mm).replace(/ss/, ss);
                }else{
                    return format.replace(/hh/, hh).replace(/mm/, mm).replace(/ss/, ss);
                }
                
            };
        });