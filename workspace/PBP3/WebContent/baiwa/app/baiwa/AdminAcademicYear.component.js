"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var http_1 = require('@angular/http');
var router_1 = require('@angular/router');
var AdminAcademicYear = (function () {
    function AdminAcademicYear(router, http, route) {
        this.router = router;
        this.http = http;
        this.route = route;
        this.AcademicYear = [
            {
                "name": "Test",
                "startDateStr": "1 ส.ค. 2558",
                "endDateStr": "31 ก.ค. 2559",
                "termstartDate": "31 ก.ค. 2559",
                "termendDate": "31 ก.ค. 2559"
            },
            {
                "name": "Test2",
                "startDateStr": "1 ส.ค. 2558",
                "endDateStr": "31 ก.ค. 2559",
                "termstartDate": "31 ก.ค. 2559",
                "termendDate": "31 ก.ค. 2559"
            }
        ];
    }
    AdminAcademicYear.prototype.ngOnInit = function () {
        this.adminAcademicYear();
    };
    AdminAcademicYear.prototype.adminAcademicYear = function () {
        var _this = this;
        var url = "../admin/json/getAcademicYear/init";
        return this.http.get(url).map(function (response) { return response.json(); }).subscribe(function (response) { return _this.adminAcademicYearSucess(response); }, function (error) { return _this.adminAcademicYearError(error); }, function () { return console.log("DepartmentName !"); });
    };
    AdminAcademicYear.prototype.adminAcademicYearSucess = function (response) {
        console.log(response);
        this.AcademicYearlist = response.resObj[1];
        console.log("sucsess !");
    };
    AdminAcademicYear.prototype.adminAcademicYearError = function (error) {
        console.log("getAdminAcademicYearError.");
    };
    AdminAcademicYear.prototype.AcademicYearAction = function (year, startDate, endDate) {
        // alert(year);
        this.router.navigate(['/adminAcademicYearEdit', year, startDate, endDate]);
    };
    AdminAcademicYear.prototype.AdjustAcademicYearAction = function (year) {
        alert("AdjustAcademicYearAction");
    };
    AdminAcademicYear = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminAcademicYear.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, http_1.Http, router_1.ActivatedRoute])
    ], AdminAcademicYear);
    return AdminAcademicYear;
}());
exports.AdminAcademicYear = AdminAcademicYear;
//# sourceMappingURL=AdminAcademicYear.component.js.map