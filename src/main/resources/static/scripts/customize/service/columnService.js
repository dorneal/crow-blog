app.service('columnService', function ($http) {
    this.update = function (entity) {
        return $http.put("/column/modifyColumn", entity);
    };
    this.listColumn = function () {
        return $http.get("/column/listColumn");
    };
    this.find = function (id) {
        return $http.get("/column/findColumn?id=" + id);
    };
    this.delete = function (id) {
        return $http({
            method: "DELETE",
            url: "/column/delete?id=" + id,
            data: {},
            header: {'Content-Type': 'application/json'}
        });
    };
    this.save = function (entity) {
        return $http.post("/column/save", entity);
    };
    this.listByLevel = function (level) {
        return $http.get("/column/listByLevel?columnLevel=" + level);
    };
    this.listByParent = function (parentId) {
        return $http.get("/column/listByParent?parentId=" + parentId);
    };
    this.rankColumn = function () {
        return $http.get("/column/rankColumn");
    };
    this.columnNav = function (id) {
        return $http.get("/column/columnNav?id=" + id);
    };
});