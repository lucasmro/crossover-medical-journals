'use strict';

/* Controllers */

var crossoverControllers = angular.module('crossoverControllers', []);

crossoverControllers.controller('HomeCtrl', ['$scope', 'Journal',
  function($scope, Journal) {

    // Get latest journals
    Journal.latest(function(data) {
      $scope.latestJournals = data;
    });

  }]
);

crossoverControllers.controller('JournalDetailCtrl', ['$scope', '$routeParams', 'Journal', 'Category',
  function($scope, $routeParams, Journal, Category) {

    // Get journal Details
    Journal.get($routeParams.journalId, function(data) {
      $scope.journal = data;
    });

    // Get categories (root)
    Category.getRootCategories(function(data) {
      $scope.categories = data;
    });

  }]
);

crossoverControllers.controller('CategoryCtrl', ['$scope', '$routeParams', 'Category',
  function($scope, $routeParams, Category) {

    // Get category details
    Category.getByTopicId($routeParams.topicId, function(data) {
      $scope.category = data;
    });

    // Get categories (root)
    Category.getRootCategories(function(data) {
      $scope.categories = data;
    });

    // Get journal of a category
    Category.getJournalsByTopicId($routeParams.topicId, function(data) {
      $scope.journals = data;
    });

  }]
);
