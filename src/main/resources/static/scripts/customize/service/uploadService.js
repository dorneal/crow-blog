app.service('uploadService', function ($http) {
    this.upload = function (formData) {
        return $http({
            url: '/upload',
            method: 'post',
            data: formData,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        })
    }
});