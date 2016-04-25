'use strict';

angular.module('myApp.view3', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view3', {
                    templateUrl: 'app/view3/view3.html',
                    controller: 'View3Ctrl',
                    isUser: 'true'
                });
            }])

        .controller('View3Ctrl', function ($http, $scope, $location) {

            $scope.isFound = false;
            $scope.searchText = null;
            $scope.selectedOption = null;
            $scope.searchOptions = [
                {call: "search", name: "Standard"},
                {call: "vat", name: "CVR Number"},
                {call: "name", name: "Company Name"},
                {call: "produ", name: "Production Unit"},
                {call: "phone", name: "Phone Number"}
            ];
            $scope.selectedCountry = null;
            $scope.country = [
                {call: "dk", name: "DK"},
                {call: "no", name: "NO"}
            ];

            $scope.search = function () {
                $http({
                    method: "GET",
                    url: "http://cvrapi.dk/api?" + $scope.selectedOption + "=" + $scope.searchText + "&country=" + $scope.selectedCountry,
                    skipAuthorization: true,
                    headers: {
                        'User-Agent': "CVR API-CA3 SCHOOL Exercise-David Samuelsen-cph-ds118@cphbusiness.dk"
                    }
                }).then(function successCallback(res) {
                    $scope.foundCompany = res.data;
                    $scope.isFound = true;
                    $scope.isOwners = true;
                    if($scope.foundCompany.owners === null){
                        $scope.isOwners = false;
                    }
                    if (res.data.error === "NO_SEARCH") {
                        $scope.isFound = false;
                        $scope.openErrorModal("Please input a valid search text");
                    }
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });
            };

            $scope.$watch('isUser', function () {
                if ($scope.isUser === false) {
                    $scope.openErrorModal("You are not authorized to perform the requested operation. Please login as a user.");
                    $location.path("#/view1");
                }
            });


        });