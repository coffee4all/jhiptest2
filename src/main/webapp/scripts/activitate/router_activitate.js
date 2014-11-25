'use strict';

bizplaceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/activitate', {
                    templateUrl: 'views/activitates.html',
                    controller: 'ActivitateController',
                    resolve:{
                        resolvedActivitate: ['Activitate', function (Activitate) {
                            return Activitate.query().$promise;
                        }],
                        resolvedCompanie: ['Companie', function (Companie) {
                            return Companie.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
