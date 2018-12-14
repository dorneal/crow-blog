app.controller('emailController', function ($scope, $controller, emailService, uploadService, visitorService) {
    $controller('baseController', {$scope: $scope});

    $scope.emailInfo = {receiver: sessionStorage.getItem("receiver")};
    $scope.initEmail = function () {
        if ($scope.emailInfo.receiver != null) {
            visitorService.findByEmail($scope.emailInfo.receiver).success(function (result) {
                if (result.code === 200) {
                    $scope.emailInfo.name = result.data.visitorName;
                } else {
                    alert(result.message);
                }
            });
        }
    };
    $scope.sendEmail = function () {
        $scope.emailInfo.content = $('#email-content').summernote('code');
        emailService.sendEmail($scope.emailInfo).success(function (result) {
            if (result.code === 200) {
                alert("已发送");
                location.href = "index.html";
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
                $('#email-content').summernote('insertImage', result.data);
            } else {
                alert(result.message);
            }
        });
    };
});