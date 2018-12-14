app.service('visitorService', function ($http) {
    this.searchByLocation = function (page, pageSize, entity) {
        return $http.post("/visitor/search?page=" + page + "&pageSize=" + pageSize, entity);
    };
    this.findByRequest = function () {
        return $http.get("/visitor/visitorInfo");
    };
    this.findByEmail = function (email) {
        return $http.get("/visitor/findByEmail?email=" + email);
    }
});