'use strict';

bizplaceApp.controller('TipabonamentController', function ($scope, resolvedTipabonament, Tipabonament, resolvedAbonament) {

        $scope.tipabonaments = resolvedTipabonament;
        $scope.abonaments = resolvedAbonament;

        $scope.create = function () {
            Tipabonament.save($scope.tipabonament,
                function () {
                    $scope.tipabonaments = Tipabonament.query();
                    $('#saveTipabonamentModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.tipabonament = Tipabonament.get({id: id});
            $('#saveTipabonamentModal').modal('show');
        };

        $scope.delete = function (id) {
            Tipabonament.delete({id: id},
                function () {
                    $scope.tipabonaments = Tipabonament.query();
                });
        };

        $scope.clear = function () {
            $scope.tipabonament = {nume: null, anunturi: null, companii: null, valabilitate: null, epublic: null, id: null};
        };
    });
