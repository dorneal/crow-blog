app.controller('columnController', function ($scope, $controller, columnService) {
    $controller('baseController', {$scope: $scope});

    /**
     * 保存或者更新操作
     */
    $scope.save = function () {
        let id = $scope.entity.id;
        if ($scope.entity.columnLevel != null) {
            if ($scope.entity.columnLevel > 1) {
                if ($scope.entity.higherId == null) {
                    alert("超过一级栏目，请选择父级栏目");
                    return
                }
            }
            let method;
            if (id != null) {
                // 如果ID不为空，则说明是更新信息操作，为空则是添加操作
                method = columnService.update($scope.entity);
            } else {
                method = columnService.save($scope.entity);
            }
            method.success(function (result) {
                $scope.myReload(result);
            });
        } else {
            alert("请选择栏目级别");
        }
    };

    /**
     * 删除操作
     * @param id 栏目ID
     */
    $scope.deleteColumn = function (id) {
        if (id != null) {
            if (confirm("确认删除栏目以及栏目下的所有文章?")) {
                columnService.delete(id).success(function (result) {
                    $scope.myReload(result);
                });
            }
        }
    };

    /**
     * 查询所有的栏目列表
     */
    $scope.levelNameList = ['column level 1', 'column level 2', 'column level 3'];
    $scope.listColumn = function () {
        columnService.listColumn().success(function (result) {
            if (result.code === 200) {
                $scope.columnList = result.data;
            }
        });
    };


    /**
     * 查询该级别下的所有栏目
     * @param level
     */
    $scope.subColumnList = [];
    $scope.listByLevel = function (level) {
        if (level != null && level > 1) {
            columnService.listByLevel(parseInt(level) - 1 + '').success(function (result) {
                if (result.code === 200) {
                    $scope.subColumnList = result.data;
                }
            });
        } else {
            $scope.subColumnList = [];
        }
    };

    /**
     * 根据ID查找该条栏目信息
     * @param id
     */
    $scope.findColumn = function (id) {
        columnService.find(id).success(function (result) {
            if (result.code === 200) {
                $scope.entity = result.data;
                $scope.listByLevel($scope.entity.columnLevel);
            }
        });
    };


    /**
     * 抽取共用块
     * @param result response result
     */
    $scope.myReload = function (result) {
        if (result.code === 200) {
            alert(result.message);
            $scope.listColumn();
        } else {
            alert(result.message);
        }
    }
});