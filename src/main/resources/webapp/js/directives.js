'use strict';

/* Directives */

crossoverApp.directive('sidebarCategories', function() {
  return {
    restrict: 'E',
    scope: false,
    templateUrl: 'directives/sidebar-categories.html'
  }
});

crossoverApp.directive('boxJournal', function() {
  return {
    restrict: 'E',
    scope: false,
    templateUrl: 'directives/box-journal.html'
  }
});