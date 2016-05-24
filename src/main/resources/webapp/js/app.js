'use strict';

var crossoverApp = angular.module('crossoverApp', [
  'ngRoute',
  'crossoverControllers',
  'crossoverServices',
  'crossoverFilters'
]);

crossoverApp.config(['$routeProvider', '$locationProvider',
  function($routeProvider, $locationProvider) {

    $routeProvider.
      when('/', {
        templateUrl: 'views/home.html',
        controller: 'HomeCtrl'
      }).
      when('/journal/:journalId', {
        templateUrl: 'views/journal-detail.html',
        controller: 'JournalDetailCtrl'
      }).
      when('/topic/:topicId', {
        templateUrl: 'views/category-grid.html',
        controller: 'CategoryCtrl'
      }).
      otherwise({
        redirectTo: '/'
      });

      // use the HTML5 History API
      $locationProvider.html5Mode(true);
  }]
);
