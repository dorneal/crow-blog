app.controller('articleController', function ($scope, $controller, $location, articleService, columnService, uploadService) {
    $controller('baseController', {$scope: $scope});

    /**
     * 一级栏目
     */
    $scope.selectColumnList1 = function () {
        columnService.listByLevel('1').success(function (result) {
            if (result.code === 200) {
                $scope.columnList1 = result.data;
            } else {
                $scope.columnList2 = [];
            }
        });
    };

    /**
     * 二级栏目
     */
    $scope.$watch("id1", function (newVal, oldVal) {
        if (newVal === undefined || newVal === null) {
            // 如果新值，发生改变但值未定义或者为空，则进行list清空
            $scope.columnList2 = [];
            $scope.columnList3 = [];
        } else {
            columnService.listByParent(newVal).success(function (result) {
                if (result.code === 200) {
                    $scope.columnList2 = result.data;
                }
            });
        }
    });

    /**
     * 三级栏目
     */
    $scope.$watch("id2", function (newVal, oldVal) {
        if (newVal === undefined || newVal === null) {
            $scope.columnList3 = [];
        } else {
            columnService.listByParent(newVal).success(function (result) {
                if (result.code === 200) {
                    $scope.columnList3 = result.data;
                }
            });
        }
    });


    $scope.packageAttribute = function () {
        if ($scope.id3 != null) {
            // 如果三级栏目ID被选中则最终ID为三级栏目ID
            $scope.entity.article.columnId = $scope.id3;
        } else if ($scope.id2 != null) {
            // 如果三级栏目ID没被选中，二级栏目被选中，则最终ID为二级栏目ID
            $scope.entity.article.columnId = $scope.id2;
        } else if ($scope.id1 != null) {
            // 如果三级栏目，二级栏目ID都没被选中，则最终ID为一级级栏目ID
            $scope.entity.article.columnId = $scope.id1;
        }
        // 获取富文本的值(记得xss过滤)
        $scope.entity.article.content = $('#article-content').summernote('code');
    };

    /**
     * 对象初始化
     */
    $scope.entity = {
        article: {id: $location.search()['id'], columnId: null, articleTag: '1', articleStatus: '1'},
        myTiming: {}
    };
    $scope.save = function () {
        let method;
        $scope.packageAttribute();
        if ($scope.entity.article.columnId == null || $scope.entity.article.columnId === undefined
            || $scope.entity.article.title == null || $scope.entity.article.title === undefined
            || $scope.entity.article.createTime == null || $scope.entity.article.createTime === undefined
            || $scope.entity.article.articleTag == null || $scope.entity.article.articleTag === undefined
            || $scope.entity.article.content == null || $scope.entity.article.content === undefined) {
            alert("请选择细心填写内容");
            return;
        }
        $scope.entity.article.createTime = new Date($scope.entity.article.createTime);

        if ($scope.entity.myTiming !== undefined) {
            if ($scope.entity.myTiming.workTime !== undefined && $scope.entity.myTiming.workTime != null) {
                $scope.entity.myTiming.workTime = new Date($scope.entity.myTiming.workTime);
            }
        }
        console.info($scope.entity);
        if ($scope.entity.article.id == null) {
            method = articleService.newArticle($scope.entity);
        } else {
            method = articleService.updateArticle($scope.entity);
        }
        method.success(function (result) {
            if (result.code === 200) {
                alert(result.message);
                location.reload();
            } else {
                alert(result.message);
            }
        });
    };

    /**
     * 上传图片
     * @param file
     */
    $scope.uploadImg = function (file) {
        let formData = new FormData();
        formData.append("file", file);
        uploadService.upload(formData).success(function (result) {
            if (result.code === 200) {
                $('#article-content').summernote('insertImage', result.data);
            } else {
                alert(result.message);
            }
        });
    };

    $scope.reload = function () {
        $scope.listArticle($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);

    };
    $scope.articleStatus = ['disable', 'enable', 'timing publish'];
    $scope.articleStatusColor = ['text-danger', 'text-success', 'text-primary'];
    $scope.listArticle = function (page, size) {
        articleService.listArticle(page, size, $scope.searchEntity).success(function (result) {
            if (result.code === 200) {
                $scope.articleList = result.data.rows;
                $scope.paginationConf.totalItems = result.data.total;
            }
        });
    };


    /**
     * 跳转到更新页面并携带ID
     * @param id
     */
    $scope.toUpdate = function (id) {
        location.href = 'new-article.html#?id=' + id;
    };
    /**
     * 查找显示资料
     */
    $scope.findByUpdate = function () {
        if ($scope.entity.article.id != null) {
            articleService.findArticle($scope.entity.article.id).success(function (result) {
                if (result.code === 200) {
                    $scope.entity = result.data;
                    $('#article-content').summernote('code', $scope.entity.article.content);
                }
            });
        }
    };

    /**
     * 删除文章
     * @param id
     */
    $scope.deleteArticle = function (id) {
        if (id != null) {
            if (confirm("确认关闭该文章？")) {
                articleService.removeArticle(id).success(function (result) {
                    if (result.code === 200) {
                        // 刷新列表
                        $scope.reload();
                    }
                });
            }
        }
    };
    $scope.enableArticle = function (id) {
        if (id != null) {
            if (confirm("启用该文章？")) {
                articleService.enableArticle(id).success(function (result) {
                    if (result.code === 200) {
                        $scope.reload();
                    }
                })
            }
        }
    }
});