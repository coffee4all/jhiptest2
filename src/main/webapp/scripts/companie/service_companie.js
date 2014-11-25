'use strict';

bizplaceApp.factory('Companie', function ($resource) {
        return $resource('app/rest/companies/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
