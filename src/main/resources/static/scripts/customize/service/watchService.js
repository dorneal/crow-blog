app.service('watchService', function ($http) {
    this.watches = function (id) {
        return $http.get('/watch/watches?id=' + id);
    }
});