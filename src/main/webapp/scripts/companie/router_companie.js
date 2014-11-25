'use strict';

bizplaceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/companie', {
                    templateUrl: 'views/companies.html',
                    controller: 'CompanieController',
                    resolve:{
                        resolvedCompanie: ['Companie', function (Companie) {
                            return Companie.query().$promise;
                        }],
                        resolvedJudet: ['Judet', function (Judet) {
                            return Judet.query().$promise;
                        }],
                        resolvedActivitate: ['Activitate', function (Activitate) {
                            return Activitate.query().$promise;
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
