app.controller('searchController', function ($scope, $controller, $sce, columnService, visitorService, articleService, searchService) {
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

    $scope.searchEntity = {keywords: sessionStorage.getItem("keywords")};
    $scope.columnNav = function () {
        if ($scope.searchEntity.keywords === undefined || $scope.searchEntity.keywords == null) {
            $scope.navColumn = $sce.trustAsHtml("<li class='breadcrumb-item active' aria-current='page'>没有结果</li>");
        } else {
            $scope.fillData($scope.searchEntity.keywords);
        }
    };

    $scope.searchResultList = [];
    $scope.fillData = function (keywords) {
        let param = $.trim(keywords);
        if (param.length !== 0) {
            searchService.search(param).success(function (result) {
                if (result.code === 200) {
                    $scope.searchResultList = [];
                    $scope.highlighted = result.data.highlighted;
                    for (let i of $scope.highlighted) {
                        let entity = i.entity;
                        let highlights = i.highlights;
                        if (highlights != null) {
                            for (let j of highlights) {
                                if (j.field.name === 'title') {
                                    entity.title = j.snipplets;
                                } else if (j.field.name === 'description') {
                                    entity.description = j.snipplets;
                                }
                            }
                        }
                        $scope.searchResultList.push(entity);
                    }
                    console.info($scope.searchResultList);
                    $scope.navColumn = $sce.trustAsHtml("<li class='breadcrumb-item active' aria-current='page'>找到 " + result.data.totalElements + " 个结果</li>");
                }
            });
        }
    };
    $scope.toArticle = function (id) {
        if (id != null) {
            sessionStorage.setItem("articleId", id);
            location.href = 'content.html';
        }
    };
});