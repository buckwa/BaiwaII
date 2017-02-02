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
var AdminFaculty = (function () {
    function AdminFaculty(router, http) {
        this.router = router;
        this.http = http;
        this.Facultyname = this.facultylistdefult();
        this.Faculty = this.Facultyde();
    }
    AdminFaculty.prototype.facultylistdefult = function () {
        return [{
                "status": "",
                "academicYearList": [{}],
                "facultyList": [
                    { name: "" }
                ]
            }];
    };
    AdminFaculty.prototype.Facultyde = function () {
        return [{
                name: ""
            }];
    };
    AdminFaculty.prototype.ngOnInit = function () {
        var year = new Date().getFullYear() + 542;
        this.adminFaculty(year);
    };
    AdminFaculty.prototype.adminFaculty = function (year) {
        var _this = this;
        //console.log(year);
        this.academicYearSelect = year;
        var url = "../admin/json/getFaculty/" + year;
        return this.http.get(url).subscribe(function (response) { return _this.adminFacultySucess(response); }, function (error) { return _this.adminFacultyError(error); }, function () { return console.log("DepartmentName !"); });
    };
    AdminFaculty.prototype.adminFacultySucess = function (response) {
        this.Facultyname = response.json(JSON.stringify(response._body));
        this.Faculty = this.Facultyname[0].facultyList;
        this.academicYear = this.Facultyname[0].academicYear;
        this.academicList = this.Facultyname[0].academicYearList;
        console.log("SS !");
    };
    AdminFaculty.prototype.adminFacultyError = function (error) {
        console.log("GetadminFacultyError.");
    };
    AdminFaculty.prototype.bntBack = function () {
    };
    AdminFaculty.prototype.AdminFacultycreateDepartment = function (facultyId) {
        this.router.navigate(['/AdminFacultycreateDepartment', facultyId]);
    };
    AdminFaculty.prototype.AdminFacultyCreate = function (year) {
        this.router.navigate(['/AdminFacultyCreate', year]);
    };
    AdminFaculty.prototype.AdminFacultyeditFaculty = function (facultyId) {
        this.router.navigate(['/AdminFacultyeditFaculty', facultyId]);
    };
    AdminFaculty.prototype.AdminFacultyEditDepartment = function (departmentId) {
        this.router.navigate(['/AdminFacultyEditDepartment', departmentId]);
    };
    AdminFaculty.prototype.changeYear = function (year) {
        this.adminFaculty(year);
        console.log(year);
    };
    AdminFaculty = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminFaculty.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, http_1.Http])
    ], AdminFaculty);
    return AdminFaculty;
}());
exports.AdminFaculty = AdminFaculty;
//# sourceMappingURL=AdminFaculty.component.js.map