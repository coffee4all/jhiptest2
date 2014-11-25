'use strict';

bizplaceApp.factory('Tipabonament', function ($resource) {
        return $resource('app/rest/tipabonaments/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
