app.controller('commentController', function ($scope, $controller, commentService) {
    $controller('baseController', {$scope: $scope});

    $scope.reload = function () {
        $scope.paginationComment($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    $scope.paginationComment = function (page, pageSize) {
        commentService.paginationComment(page, pageSize).success(function (result) {
            if (result.code === 200) {
                $scope.entityList = result.data.rows;
                $scope.paginationConf.totalItems = result.data.total;
            }
        });
    };

    $scope.deleteComment = function (id) {
        if (id != null) {
            commentService.deleteComment(id).success(function (result) {
                if (result.code === 200) {
                    $scope.reload();
                }
            });
        }
    };
});