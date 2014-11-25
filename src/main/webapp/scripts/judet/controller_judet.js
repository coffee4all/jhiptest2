'use strict';

bizplaceApp.controller('JudetController', function ($scope, resolvedJudet, Judet, resolvedCompanie) {

        $scope.judets = resolvedJudet;
        $scope.companies = resolvedCompanie;

        $scope.create = function () {
            Judet.save($scope.judet,
                function () {
                    $scope.judets = Judet.query();
                    $('#saveJudetModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.judet = Judet.get({id: id});
            $('#saveJudetModal').modal('show');
        };

        $scope.delete = function (id) {
            Judet.delete({id: id},
                function () {
                    $scope.judets = Judet.query();
                });
        };

        $scope.clear = function () {
            $scope.judet = {nume: null, id: null};
        };
    });
