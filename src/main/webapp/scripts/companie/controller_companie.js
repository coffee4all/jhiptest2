'use strict';

bizplaceApp.controller('CompanieController', function ($scope, resolvedCompanie, Companie, resolvedJudet, resolvedActivitate, resolvedAbonament) {

        $scope.companies = resolvedCompanie;
        $scope.judets = resolvedJudet;
        $scope.activitates = resolvedActivitate;
        $scope.abonaments = resolvedAbonament;

        $scope.create = function () {
            Companie.save($scope.companie,
                function () {
                    $scope.companies = Companie.query();
                    $('#saveCompanieModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.companie = Companie.get({id: id});
            $('#saveCompanieModal').modal('show');
        };

        $scope.delete = function (id) {
            Companie.delete({id: id},
                function () {
                    $scope.companies = Companie.query();
                });
        };

        $scope.clear = function () {
            $scope.companie = {cui: null, nume: null, adresa: null, telefon: null, email: null, website: null, creatala: null, id: null};
        };
    });
