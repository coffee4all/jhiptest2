'use strict';

bizplaceApp.controller('AbonamentController', function ($scope, resolvedAbonament, Abonament, resolvedCompanie, resolvedTipabonament) {

        $scope.abonaments = resolvedAbonament;
        $scope.companies = resolvedCompanie;
        $scope.tipabonaments = resolvedTipabonament;

        $scope.create = function () {
            Abonament.save($scope.abonament,
                function () {
                    $scope.abonaments = Abonament.query();
                    $('#saveAbonamentModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.abonament = Abonament.get({id: id});
            $('#saveAbonamentModal').modal('show');
        };

        $scope.delete = function (id) {
            Abonament.delete({id: id},
                function () {
                    $scope.abonaments = Abonament.query();
                });
        };

        $scope.clear = function () {
            $scope.abonament = {anunturi: null, companii: null, valabilitate: null, creatla: null, id: null};
        };
    });
