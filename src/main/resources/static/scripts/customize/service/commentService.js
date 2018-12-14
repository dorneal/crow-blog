app.service('commentService', function ($http) {
    this.paginationComment = function (page, pageSize) {
        return $http.get("/comment/pagination?page=" + page + "&pageSize=" + pageSize);
    };
    this.newComment = function (entity) {
        return $http.post("/comment/newComment", entity);
    };
    this.deleteComment = function (id) {
        return $http({
            method: "DELETE",
            url: "/comment/deleteComment?id=" + id,
            data: {},
            headers: {"Content-Type": "application/json"}
        })
    };
    this.listByArticleId = function (id) {
        return $http.get("/comment/listByArticleId?id=" + id);
    }
});