app.service('articleService', function ($http) {
    this.enableArticle = function (id) {
        return $http.put("/article/enableArticle", id);
    };
    this.updateArticle = function (entity) {
        return $http.put("/article/modifyArticle", entity);
    };
    this.removeArticle = function (ids) {
        return $http({
            method: "delete",
            url: "/article/removeArticle?ids=" + ids,
            data: {},
            headers: {"Content-Type": "application/json"}
        });
    };
    this.findArticle = function (id) {
        return $http.get("/article/findArticle?id=" + id);
    };
    this.listArticle = function (page, size, entity) {
        return $http.post('/article/listArticle?page=' + page + '&pageSize=' + size, entity);
    };
    this.articlesByColumnId = function (columnId) {
        return $http.get("/article/articlesByColumnId?id=" + columnId);
    };
    this.newArticle = function (entity) {
        return $http.post("/article/newArticle", entity);
    };
    this.visitRank = function () {
        return $http.get('/article/visitRank');
    };
    this.likeRank = function () {
        return $http.get('/article/likeRank');
    };
    this.article = function (id) {
        return $http.get("/article/article?id=" + id);
    };
    this.like = function (articleId) {
        return $http.get("/article/like?articleId=" + articleId);
    };
    this.whetherLike = function (articleId) {
        return $http.get("/article/whetherLike?articleId=" + articleId);
    };
    this.newestArticle = function () {
        return $http.get("/article/newestArticle");
    }
});