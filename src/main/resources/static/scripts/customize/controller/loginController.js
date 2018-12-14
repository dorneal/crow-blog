app.controller('loginController', function ($scope, blogService) {

    $scope.alerts = [];
    $scope.addAlert = function () {
        $scope.alerts.push({msg: '一条消息'});
    };

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.addInfoAlert = function (msg) {
        $scope.alerts.push({
            type: "info", msg: msg, "dismiss-on-timeout": "5000", close: function () {
                return closeAlert(this);
            },
        });
    };
    $scope.addSuccessAlert = function (msg) {
        $scope.alerts.push({type: "success", msg: msg});
    };

    $scope.addWarningAlert = function (msg) {
        $scope.alerts.push({type: "warning", msg: msg});
    };

    $scope.addDangerAlert = function (msg) {
        $scope.alerts.push({type: "danger", msg: msg});
    };


    /**
     * 处理登录
     */
    $scope.entity = {rememberMe: false};
    $scope.login = function () {
        blogService.login($scope.entity).success(function (result) {
            if (result.code === 200) {
                localStorage.setItem('currentUser', JSON.stringify(result.data));
                location.href = result.message;
            } else {
                $scope.addDangerAlert(result.message);
            }
        });
    };
});