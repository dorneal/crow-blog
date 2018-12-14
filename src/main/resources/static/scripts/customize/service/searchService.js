app.service('searchService', function ($http) {
    this.search = function (keywords) {
        return $http.get("/search/searchArticle?q=" + keywords);
    };
});