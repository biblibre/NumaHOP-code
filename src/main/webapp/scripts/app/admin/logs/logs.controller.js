(function () {
    'use strict';

    angular.module('numaHopApp').controller('LogsController', function ($scope, LogsService, $http, $httpParamSerializer, FileSaver, ModalSrvc) {
        $scope.loggers = LogsService.findAll();

        $scope.changeLevel = function (name, level) {
            LogsService.changeLevel({ name: name, level: level }, function () {
                $scope.loggers = LogsService.findAll();
            });
        };
    });
})();
