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
var AdminAcademicYear = (function () {
    function AdminAcademicYear(http) {
        this.http = http;
        this.AcademicYear = {
            "name": ""
        };
    }
    AdminAcademicYear.prototype.ngOnInit = function () {
        this.adminAcademicYear();
    };
    AdminAcademicYear.prototype.adminAcademicYear = function () {
        var _this = this;
        var url = "../admin/json/getAdminAcademicYear";
        return this.http.get(url).map(function (r) { return r.json(); }).subscribe(function (response) { return _this.adminAcademicYearSucess(response); }, function (error) { return _this.adminAcademicYearError(error); }, function () { return console.log("DepartmentName !"); });
    };
    AdminAcademicYear.prototype.adminAcademicYearSucess = function (response) {
        console.log(response);
        this.AcademicYear = response.resObj.academicYear;
        console.log("sucsess !");
    };
    AdminAcademicYear.prototype.adminAcademicYearError = function (error) {
        console.log("getAdminAcademicYearError.");
    };
    AdminAcademicYear = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminAcademicYear.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], AdminAcademicYear);
    return AdminAcademicYear;
}());
exports.AdminAcademicYear = AdminAcademicYear;
//# sourceMappingURL=AdminAcademicYear.component.js.map