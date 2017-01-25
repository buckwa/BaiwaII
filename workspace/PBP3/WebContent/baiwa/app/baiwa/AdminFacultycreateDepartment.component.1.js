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
var AdminFacultycreateDepartment = (function () {
    function AdminFacultycreateDepartment(http, route) {
        this.http = http;
        this.route = route;
    }
    AdminFacultycreateDepartment.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) { return _this.Faculty_id = params["Faculty_id"]; });
        if (this.Faculty_id) {
            console.log("Faculty_id :", this.Faculty_id);
        }
        this.adminFaculty();
    };
    AdminFacultycreateDepartment.prototype.adminFaculty = function () {
        var _this = this;
        var url = "../admin/json/createDepartmentGet/" + this.Faculty_id;
        return this.http.get(url).subscribe(function (response) { return _this.adminFacultySucess(response); }, function (error) { return _this.adminFacultyError(error); }, function () { return console.log("DepartmentName !"); });
    };
    AdminFacultycreateDepartment.prototype.adminFacultySucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.resObj = this.model.resObj;
        this.name = this.resObj.name;
        this.academicYear = this.resObj.academicYear;
        this.Department = this.resObj.department.name;
        console.log("Succuss !");
    };
    AdminFacultycreateDepartment.prototype.adminFacultyError = function (error) {
        console.log("Error.");
    };
    AdminFacultycreateDepartment.prototype.adminSaveFacultyedit = function () {
        var _this = this;
        this.resObj.department.name = this.Department;
        this.resObj.name = this.name;
        var url = "../admin/json/createDepartmentPost"; //ติดไว้ก่อน
        this.http.post(url, this.resObj).subscribe(function (response) { return _this.SaveFacultySJsonSucess(response); }, function (error) { return _this.adminFacultyError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminFacultycreateDepartment.prototype.SaveFacultySJsonSucess = function (response) {
        //Todo
        //Show Status
        window.location.href = '#/AdminFaculty';
        this.result = response.json(JSON.stringify(response._body));
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    };
    AdminFacultycreateDepartment = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminFacultycreateDepartment.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.ActivatedRoute])
    ], AdminFacultycreateDepartment);
    return AdminFacultycreateDepartment;
}());
exports.AdminFacultycreateDepartment = AdminFacultycreateDepartment;
//# sourceMappingURL=AdminFacultycreateDepartment.component.1.js.map