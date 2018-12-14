app.service('likeService', function ($http) {
    this.likes = function (id) {
        return $http.get("/like/likes?id=" + id);
    }
});