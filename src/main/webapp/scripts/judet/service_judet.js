'use strict';

bizplaceApp.factory('Judet', function ($resource) {
        return $resource('app/rest/judets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
