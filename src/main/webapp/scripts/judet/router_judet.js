'use strict';

bizplaceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/judet', {
                    templateUrl: 'views/judets.html',
                    controller: 'JudetController',
                    resolve:{
                        resolvedJudet: ['Judet', function (Judet) {
                            return Judet.query().$promise;
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
