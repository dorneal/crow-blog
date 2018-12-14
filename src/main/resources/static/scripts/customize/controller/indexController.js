app.controller('indexController', function ($scope, $controller, $sce, columnService, visitorService, articleService) {
    $controller('baseController', {$scope: $scope});

    /**
     * 初始化右边栏排行榜
     */
    $scope.initRank = function () {
        columnService.rankColumn().success(function (result) {
            if (result.code === 200) {
                $scope.columnRank = result.data;
            }
        });
        articleService.visitRank().success(function (result) {
            if (result.code === 200) {
                $scope.rankVisit = result.data;
            }
        });
        articleService.likeRank().success(function (result) {
            if (result.code === 200) {
                $scope.rankLike = result.data;
            }
        });
    };

    $scope.columnEntity = {id: sessionStorage.getItem("columnId")};
    $scope.columnNav = function () {
        if ($scope.columnEntity.id === undefined || $scope.columnEntity.id == null || $scope.columnEntity.id === 1) {
            // 如果没有值，则直接显示最新文章
            articleService.newestArticle().success(function (result) {
                if (result.code === 200) {
                    $scope.articleList = result.data;
                }
            });
            $scope.navColumn = $sce.trustAsHtml("<li class='breadcrumb-item active' aria-current='page'>最新文章</li>");
        } else {
            $scope.fillData($scope.columnEntity.id);
        }
    };
    $scope.indexColumnNav = function (id) {
        if (id != null) {
            $scope.fillData(id);
        }
    };


    $scope.fillData = function (id) {
        columnService.columnNav(id).success(function (result) {
            if (result.code === 200) {
                $scope.navColumn = $sce.trustAsHtml(result.data);
            }
        });
        articleService.articlesByColumnId(id).success(function (result) {
            if (result.code === 200) {
                $scope.articleList = result.data;
            }
        });
    };

    $scope.toArticle = function (id) {
        sessionStorage.setItem("articleId", id);
        location.href = "client/content.html";
    }
});