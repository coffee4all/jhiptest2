'use strict';

bizplaceApp.factory('Abonament', function ($resource) {
        return $resource('app/rest/abonaments/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
