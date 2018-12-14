app.service('emailService', function ($http) {
    this.sendEmail = function (entity) {
        return $http.post("/email/sendEmail", entity);
    };
});