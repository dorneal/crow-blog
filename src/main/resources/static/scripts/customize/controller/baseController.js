app.controller('baseController', function ($scope, blogService) {
    /**
     * 初始化信息
     */
    $scope.initInfo = function () {
        $scope.user = JSON.parse(localStorage.getItem("currentUser"));
    };

    /**
     *分页对象
     * @type {{currentPage: number, totalItems: number, itemsPerPage: number, perPageOptions: number[], onChange: onChange}}
     */
    $scope.searchEntity = {};
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 0,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reload();
        }
    };

    /**
     * 退出登录
     */
    $scope.logout = function () {
        blogService.logout().success(function (result) {
            if (result.code === 200) {
                location.href = "login2.html";
            } else {
                alert(result.message);
            }
        });
    };

    /**
     * 初始化博主信息板块
     */
    $scope.initBlog = function () {
        blogService.bloggerInfo().success(function (result) {
            if (result.code === 200) {
                $scope.blogger = result.data;
            }
        });
        blogService.blogInfo().success(function (result) {
            if (result.code === 200) {
                $scope.blog = result.data;
            }
        });
    };

    /**
     * 搜索
     * @param keywords
     */
    $scope.keywords = "";
    $scope.searchArticle = function () {
        let keywords = $.trim($scope.keywords);
        if (keywords.length !== 0) {
            sessionStorage.setItem("keywords", keywords);
            location.href = '/client/search.html';
        }
    }
});

