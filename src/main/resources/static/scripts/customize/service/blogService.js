app.service('blogService', function ($http) {
    this.login = function (entity) {
        return $http.post("/token/login?", entity);
    };
    this.logout = function () {
        return $http({
            method: "DELETE",
            url: "/token/logout",
            data: {},
            header: {'Content-Type': 'application/json'}
        });
    };
    this.modifyInfo = function (entity) {
        return $http.put("/blog/modifyInfo", entity);
    };
    this.bloggerInfo =function () {
        return $http.get("/blog/bloggerInfo");
    };
    this.blogInfo = function () {
        return $http.get('/blog/blogInfo');
    };
});