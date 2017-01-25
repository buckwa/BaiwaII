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
var AdminChainOfCommandinit = (function () {
    function AdminChainOfCommandinit(http, router) {
        this.http = http;
        this.router = router;
        this.facultyList = this.facultyListJson();
        this.FacultyWrapper = this.FacultyWrapperJson();
    }
    AdminChainOfCommandinit.prototype.facultyListJson = function () {
        return [{
                "name": "",
                "departmentList": [{
                        "name": "",
                        "head": {
                            "email": null,
                            "thaiName": "",
                            "thaiSurname": "",
                        }
                    }],
                "dean": {
                    "thaiName": "",
                    "thaiSurname": "",
                    "email": null,
                },
            }];
    };
    AdminChainOfCommandinit.prototype.FacultyWrapperJson = function () {
        return {
            "academicYear": "",
            "president": {
                "thaiName": "",
                "thaiSurname": "",
            },
            "facultyList": {},
        };
    };
    AdminChainOfCommandinit.prototype.ngOnInit = function () {
        this.chainOfCommand();
    };
    AdminChainOfCommandinit.prototype.chainOfCommand = function () {
        var _this = this;
        var url = "../admin/json/getFacultyWrapper";
        return this.http.get(url).subscribe(function (response) { return _this.chainOfCommandSucess(response); }, function (error) { return _this.chainOfCommandError(error); }, function () { return console.log("DepartmentName !"); });
    };
    AdminChainOfCommandinit.prototype.chainOfCommandSucess = function (response) {
        this.FacultyWrappers = response.json(JSON.stringify(response._body));
        this.FacultyWrapper = this.FacultyWrappers[0];
        this.academicYear = this.FacultyWrapper.academicYear;
        this.thaiName = this.FacultyWrapper.president.thaiName;
        this.thaiSurname = this.FacultyWrapper.president.thaiSurname;
        //this.facultyList = this.facultyListJson();
        this.facultyList = this.FacultyWrapper.facultyList;
        console.log("SSS !");
    };
    AdminChainOfCommandinit.prototype.chainOfCommandError = function (error) {
        console.log("GetchainOfCommandError.");
    };
    AdminChainOfCommandinit.prototype.clickShowDepartment = function (departID) {
        var _this = this;
        var url = "../admin/json/getlistByDepartment/" + departID;
        this.http.get(url).subscribe(function (response) { return _this.GetImportSucess(response); }, function (error) { return _this.GetError(error); }, function () { return console.log("getlistByDepartment !"); });
    };
    AdminChainOfCommandinit.prototype.GetImportSucess = function (response) {
        this.listByDepartments = response.json(JSON.stringify(response._body));
        this.DepartmentName = this.listByDepartments[0].department.name;
        this.listByDepartment = this.listByDepartments[0].department.academicPersonList;
        // this.department = this.listByDepartment.department;
        // this.academicPersonList = this.department.academicPersonList;
        console.log("SS2 !");
    };
    AdminChainOfCommandinit.prototype.GetError = function (error) {
        console.log("GetPersonError.");
    };
    AdminChainOfCommandinit.prototype.AdminChainOfCommandinitPresident = function () {
        console.log("result : SS");
        this.router.navigate(['/AdminChainOfCommandinitPresident']);
    };
    AdminChainOfCommandinit.prototype.AdminChainOfCommandinitDean = function (result) {
        console.log("result :", result);
        this.router.navigate(['/AdminChainOfCommandinitDean', result]);
    };
    AdminChainOfCommandinit.prototype.AdminChainOfCommandinitHead = function (result) {
        console.log("result :", result);
        this.router.navigate(['/AdminChainOfCommandinitHead', result]);
    };
    AdminChainOfCommandinit = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminChainOfCommandinit.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.Router])
    ], AdminChainOfCommandinit);
    return AdminChainOfCommandinit;
}());
exports.AdminChainOfCommandinit = AdminChainOfCommandinit;
//# sourceMappingURL=AdminChainOfCommandinit.component.js.map