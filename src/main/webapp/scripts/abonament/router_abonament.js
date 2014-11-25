'use strict';

bizplaceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/abonament', {
                    templateUrl: 'views/abonaments.html',
                    controller: 'AbonamentController',
                    resolve:{
                        resolvedAbonament: ['Abonament', function (Abonament) {
                            return Abonament.query().$promise;
                        }],
                        resolvedCompanie: ['Companie', function (Companie) {
                            return Companie.query().$promise;
                        }],
                        resolvedTipabonament: ['Tipabonament', function (Tipabonament) {
                            return Tipabonament.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
