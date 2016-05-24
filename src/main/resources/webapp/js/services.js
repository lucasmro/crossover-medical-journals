'use strict';

/* Services */

var crossoverServices = angular.module('crossoverServices', []);

crossoverServices.factory('Journal', ['$http',
  function($http) {
    return {
      get: function(id, success, error) {
        $http.get('/api/journals/' + id + '').success(success).error(error)
        // $http.get('journals/' + id + '.json').success(success).error(error)
      },
      latest: function(success, error) {
        $http.get('/api/journals').success(success).error(error)
        // $http.get('journals/journals-latest.json').success(success).error(error)
      }
    };
  }]
);

crossoverServices.factory('Category', ['$http',
  function($http) {
    return {
      getByTopicId: function(topicId, success, error) {
        $http.get('/api/topics/' + topicId + '').success(success).error(error)
        // $http.get('categories/' + topicId + '.json').success(success).error(error)
      },
      getRootCategories: function(success, error) {
        $http.get('/api/topics').success(success).error(error)
        // $http.get('categories/parent.json').success(success).error(error)
      },
      getJournalsByTopicId: function(topicId, success, error) {
        $http.get('/api/topics/' + topicId + '/journals').success(success).error(error)
        // $http.get('categories/' + slug + '-journals.json').success(success).error(error)
      }
    };
  }]
);
