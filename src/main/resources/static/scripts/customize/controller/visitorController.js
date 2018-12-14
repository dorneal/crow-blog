app.controller('visitorController', function ($scope, $controller, visitorService) {
    $controller('baseController', {$scope: $scope});

    $scope.reload = function () {
        $scope.paginationComment($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    $scope.paginationComment = function (page, pageSize) {
        visitorService.searchByLocation(page, pageSize, $scope.searchEntity).success(function (result) {
            if (result.code === 200) {
                $scope.entityList = result.data.rows;
                $scope.paginationConf.totalItems = result.data.total;
            }
        });
    };
    $scope.sendEmail = function (email) {
        sessionStorage.setItem("receiver", email);
        location.href = "sendEmail.html";
    }
});