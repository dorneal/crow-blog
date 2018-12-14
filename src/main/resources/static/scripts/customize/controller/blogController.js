app.controller('blogController', function ($scope, $controller, blogService, uploadService) {
    // 伪继承
    $controller('baseController', {$scope: $scope});
    /**
     * 上传图片
     */
    $scope.upload = function () {
        let formData = new FormData();
        let data = file.files[0];
        if (data != null) {
            // file 文件上传框name
            formData.append("file", data);
            uploadService.upload(formData).success(function (result) {
                if (result.code === 200) {
                    $scope.user.avatar = result.data;
                } else {
                    alert(result.message);
                }
            });
        } else {
            alert("please choose images");
        }
    };

    /**
     * 更新博主信息
     */
    $scope.updateInfo = function () {
        blogService.modifyInfo($scope.user).success(function (result) {
            if (result.code === 200) {
                localStorage.setItem("currentUser", JSON.stringify(result.data));
                alert(result.message);
                location.reload();
            } else {
                alert(result.message);
            }
        })
    };
});