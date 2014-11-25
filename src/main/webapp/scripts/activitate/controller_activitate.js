'use strict';

bizplaceApp.controller('ActivitateController', function ($scope, resolvedActivitate, Activitate, resolvedCompanie) {

        $scope.activitates = resolvedActivitate;
        $scope.companies = resolvedCompanie;

        $scope.create = function () {
            Activitate.save($scope.activitate,
                function () {
                    $scope.activitates = Activitate.query();
                    $('#saveActivitateModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.activitate = Activitate.get({id: id});
            $('#saveActivitateModal').modal('show');
        };

        $scope.delete = function (id) {
            Activitate.delete({id: id},
                function () {
                    $scope.activitates = Activitate.query();
                });
        };

        $scope.clear = function () {
            $scope.activitate = {nume: null, id: null};
        };
    });
