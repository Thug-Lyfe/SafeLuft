'use strict';

angular.module('myApp.view4', ['ngRoute', 'ngTable'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view4', {
                    templateUrl: 'app/view4/view4.html',
                    controller: 'View4Ctrl'
                });
            }])

        .controller('View4Ctrl', function ($http, $scope, NgTableParams) {
             $scope.isFound = false;





//ca-mb1337.rhcloud.com/SemesterSeed/api/Currency/getRates

            $http({method: "GET",
                url: "/SafeLuft/api/admin/tickets"
            })
                    .then(function successCallback(response) {
                        $scope.res = response.data;
                        $scope.isFound = true;
                        
//                        $scope.tableParams = new NgTableParams({page: 1, count: 10}, {dataset: data});
                    });
//            $scope.calculate = function () {
//                if ($scope.fromAmount != null || $scope.fromAmount.length !== 0) {
//                    $http({
//                        method: "GET",
//                        url: "/CA-3/api/demouser/calculator/" + $scope.fromAmount + "/" + $scope.fromValuta + "/" + $scope.toValuta
//                    }).then(function (response) {
//                        $scope.toAmount = response.data.amount;
//                    });
//                } 
//            };
//            $scope.calculate2 = function () {
//                if ($scope.toAmount != null || $scope.fromAmount !== 0) {
//                    $http({
//                        method: "GET",
//                        url: "/CA-3/api/demouser/calculator/" + $scope.toAmount + "/" + $scope.toValuta + "/" + $scope.fromValuta
//                    }).then(function (response) {
//                        $scope.fromAmount = response.data.amount;
//                    });
//                } 
//            };
$scope.DATE = function newDate(date){
    var DATE = new Date(date)
    return DATE.getYear()+"-"+DATE.getMonth()+"-"+DATE.getDay()
}
        });
        