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
    'ngTable',
    'conditionize.jquery',
    'jquery.min',
    'bootstrap-datetimepicker.min',
    'moment-with-locales',
    'bootstrap.min',
    'custom'
])
        .config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/view1'});
}])
        .config(function ($httpProvider) {
   $httpProvider.interceptors.push('AuthInterceptor');
})



        .controller("AutoCtrl", function ($scope) {

            $scope.result1 = '';
            $scope.options1 = null;
            $scope.details1 = '';



            



            $scope.result3 = '';
            $scope.options3 = {
                country: 'gb',
                types: 'establishment'
            };
            $scope.details3 = '';
        });