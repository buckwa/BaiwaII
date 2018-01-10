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
var AdminFacultyEditDepartment = (function () {
    function AdminFacultyEditDepartment(http, route, router) {
        this.http = http;
        this.route = route;
        this.router = router;
    }
    AdminFacultyEditDepartment.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) { return _this.Depart_Id = params["Depart_Id"]; });
        if (this.Depart_Id) {
            console.log("Depart_Id :", this.Depart_Id);
        }
        this.adminFaculty();
    };
    AdminFacultyEditDepartment.prototype.adminFaculty = function () {
        var _this = this;
        // admin/json//
        var url = "../admin/json/editDepartmentJson/" + this.Depart_Id; //987
        return this.http.get(url).subscribe(function (response) { return _this.adminFacultySucess(response); }, function (error) { return _this.adminFacultyError(error); }, function () { return console.log("DepartmentName !"); });
    };
    AdminFacultyEditDepartment.prototype.adminFacultySucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.resObj = this.model.resObj;
        this.name = this.resObj.name;
        this.academicYear = this.resObj.academicYear;
        this.Department = this.resObj.department.name;
        console.log("Succuss !");
    };
    AdminFacultyEditDepartment.prototype.adminFacultyError = function (error) {
        console.log("Error.");
    };
    AdminFacultyEditDepartment.prototype.adminSaveFacultyedit = function () {
        var _this = this;
        this.resObj.name = this.name;
        var url = "../admin/json/editDepartmentJsonSave"; //ติดไว้ก่อน
        this.http.post(url, this.resObj).subscribe(function (response) { return _this.SaveFacultySJsonSucess(response); }, function (error) { return _this.adminFacultyError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminFacultyEditDepartment.prototype.SaveFacultySJsonSucess = function (response) {
        //Todo
        //Show Status
        //window.location.href = '#/AdminFaculty';
        this.router.navigate(['/AdminFaculty']);
        this.result = response.json(JSON.stringify(response._body));
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    };
    AdminFacultyEditDepartment.prototype.adminSaveFacultyDelete = function () {
        var _this = this;
        this.resObj.name = this.name;
        var url = "../admin/pbp/faculty/deleteDepartmentById/" + this.Depart_Id; //ติดไว้ก่อน
        this.http.get(url, this.resObj).subscribe(function (response) { return _this.DeleteFacultySJsonSucess(response); }, function (error) { return _this.adminFacultyError(error); }, function () { return console.log("Delete: Success !"); });
    };
    AdminFacultyEditDepartment.prototype.DeleteFacultySJsonSucess = function (response) {
        this.router.navigate(['/AdminFaculty']);
        this.result = response.json(JSON.stringify(response._body));
        console.log("Delete: Ready SaveUserJsonSucess !");
    };
    AdminFacultyEditDepartment = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminFacultyEditDepartment.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.ActivatedRoute, router_1.Router])
    ], AdminFacultyEditDepartment);
    return AdminFacultyEditDepartment;
}());
exports.AdminFacultyEditDepartment = AdminFacultyEditDepartment;
//# sourceMappingURL=AdminFacultyEditDepartment.component.js.map