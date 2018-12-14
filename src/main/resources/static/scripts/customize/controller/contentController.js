app.controller('contentController', function ($scope, $controller, $sce, articleService, commentService, visitorService, likeService, watchService) {
    $controller('baseController', {$scope: $scope});

    /**
     * 获取页面传值过来的文章ID
     * @type {{id: *}}
     */
    $scope.articleEntity = {id: sessionStorage.getItem("articleId")};
    $scope.visitor = {};
    $scope.tagsColor = ['转载', '原创'];
    $scope.comments = [];
    $scope.initArticle = function () {
        if ($scope.articleEntity.id === undefined || $scope.articleEntity.id == null) {
            $scope.articleEntity.id = 1;
        }
        // 查找文章
        articleService.article($scope.articleEntity.id).success(function (result) {
            if (result.code === 200) {
                $scope.articleVo = result.data;
                // 编译html
                $scope.articleVo.column = $sce.trustAsHtml($scope.articleVo.column);
                $scope.articleVo.article.content = $sce.trustAsHtml($scope.articleVo.article.content);
            }
        });
        // 查找该来访者是否来访过，来访则设置用户名跟邮箱
        visitorService.findByRequest().success(function (result) {
            if (result.code === 200) {
                if (result.data.visitorName !== null && result.data.visitorEmail !== null) {
                    $scope.visitor.visitorName = result.data.visitorName;
                    $scope.visitor.visitorEmail = result.data.visitorEmail;
                }
            }
        });
        // 查找评论
        commentService.listByArticleId($scope.articleEntity.id).success(function (result) {
            if (result.code === 200) {
                $scope.comments = result.data;
                $scope.commentNum = $scope.comments.length;
            }
        });
        // 查找点赞数
        likeService.likes($scope.articleEntity.id).success(function (result) {
            if (result.code === 200) {
                $scope.likeNum = result.data;
            }
        });
        // 浏览数
        watchService.watches($scope.articleEntity.id).success(function (result) {
            if (result.code === 200) {
                $scope.watchNum = result.data;
            }
        });
    };
    /**
     *  是否点过赞
     */
    $scope.isLike = false;
    $scope.whetherLike = function () {
        if ($scope.articleEntity.id != null) {
            articleService.whetherLike($scope.articleEntity.id).success(function (result) {
                $scope.likeResult(result);
            });
        }
    };

    /**
     * 点赞
     */
    $scope.like = function () {
        if ($scope.articleEntity.id != null) {
            articleService.like($scope.articleEntity.id).success(function (result) {
                $scope.likeResult(result);
                $scope.initArticle();
                $scope.initBlog();
            });
        }
    };
    /**
     * 点赞结果
     * @param result
     */
    $scope.likeResult = function (result) {
        if (result.code === 200) {
            $scope.isLike = result.data !== 0;
        }
    };

    $scope.publishComment = function () {
        $scope.comment.articleId = $scope.articleEntity.id;
        if ($scope.visitor.visitorEmail != null && $scope.visitor.visitorName != null) {
            $scope.comment.commentName = $scope.visitor.visitorName;
            $scope.comment.commentEmail = $scope.visitor.visitorEmail;
        }
        if ($scope.comment.content === undefined || $scope.comment.content === null
            || $scope.comment.articleId === undefined || $scope.comment.articleId === null
            || $scope.comment.commentName === undefined || $scope.comment.commentName === null
            || $scope.comment.commentEmail === undefined || $scope.comment.commentEmail === null) {
            alert("请填写所需信息");
            return;
        }
        commentService.newComment($scope.comment).success(function (result) {
            if (result.code === 200) {
                $scope.comment.content = '';
                $scope.initArticle();
            } else {
                alert(result.message);
            }
        })
    };
});