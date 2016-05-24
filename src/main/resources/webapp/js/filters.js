'use strict';

/* Filters */

var crossoverFilters = angular.module('crossoverFilters', []);

crossoverFilters.filter('enumConditionType', function() {
    return function(input, uppercase) {
      switch(input){
        case 'BIOLOGY':
          return 'Biology';
        case 'CARDIOLOGY':
          return 'Cardiology';
        case 'ENDOCRINOLOGY':
          return 'Endocrinology';
        case 'GENETICS':
          return 'Genetics';
        case 'INFECTIOUS_DISEASES':
          return 'Infectious Diseases';
        case 'MEDICAL':
          return 'Medical';
        case 'NEUROLOGY':
          return 'Neurology';
        case 'ONCOLOGY':
          return 'Oncology';
        case 'PEDIATRICS':
          return 'Pediatrics';
        case 'PHYSIOLOGY':
          return 'Physiology';
         default:
          return 'Medical'; 
      }
    }
});