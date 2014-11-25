'use strict';

bizplaceApp.factory('Activitate', function ($resource) {
        return $resource('app/rest/activitates/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
