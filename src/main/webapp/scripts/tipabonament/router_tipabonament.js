'use strict';

bizplaceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/tipabonament', {
                    templateUrl: 'views/tipabonaments.html',
                    controller: 'TipabonamentController',
                    resolve:{
                        resolvedTipabonament: ['Tipabonament', function (Tipabonament) {
                            return Tipabonament.query().$promise;
                        }],
                        resolvedAbonament: ['Abonament', function (Abonament) {
                            return Abonament.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
