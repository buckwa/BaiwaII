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
var AdminFacultyeditFaculty = (function () {
    function AdminFacultyeditFaculty(router, http, route) {
        this.router = router;
        this.http = http;
        this.route = route;
        this.Faculty = this.defaultFaculty();
    }
    AdminFacultyeditFaculty.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) { return _this.FacultyID = params["FacultyID"]; });
        if (this.FacultyID) {
            console.log("FacultyID :", this.FacultyID);
        }
        this.adminFacultyedit();
    };
    AdminFacultyeditFaculty.prototype.defaultFaculty = function () {
        return {
            "name": "",
        };
    };
    AdminFacultyeditFaculty.prototype.adminFacultyedit = function () {
        var _this = this;
        var url = "../admin/json/editFaculty/" + this.FacultyID;
        return this.http.get(url).subscribe(function (response) { return _this.adminFacultyeditSucess(response); }, function (error) { return _this.adminFacultyeditError(error); }, function () { return console.log("DepartmentName !"); });
    };
    AdminFacultyeditFaculty.prototype.adminFacultyeditSucess = function (response) {
        this.Facultyname = response.json(JSON.stringify(response._body));
        this.Faculty = this.Facultyname[0].name;
        this.model = this.Facultyname[0];
        console.log("SS !");
    };
    AdminFacultyeditFaculty.prototype.adminFacultyeditError = function (error) {
        console.log("GetadminFacultyError.");
    };
    AdminFacultyeditFaculty.prototype.adminSaveFacultyedit = function () {
        var _this = this;
        this.model.name = this.Faculty;
        var url = "../admin/json/editFacultySave/"; //ติดไว้ก่อน
        this.http.post(url, this.model).subscribe(function (response) { return _this.SaveFacultySJsonSucess(response); }, function (error) { return _this.SaveFacultySJsonError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminFacultyeditFaculty.prototype.SaveFacultySJsonSucess = function (response) {
        //Todo
        //Show Status
        this.router.navigate(['/AdminFaculty']);
        this.FacultyJson = response.json(JSON.stringify(response._body));
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    };
    AdminFacultyeditFaculty.prototype.SaveFacultySJsonError = function (response) {
        //Todo
        console.log("Error !");
    };
    AdminFacultyeditFaculty.prototype.adminSaveFacultyDelete = function () {
        var _this = this;
        var url = "../admin/pbp/faculty/deleteFacultyById/" + this.FacultyID; //ติดไว้ก่อน
        this.http.get(url).subscribe(function (response) { return _this.DeleteFacultySJsonSucess(response); }, function (error) { return _this.SaveFacultySJsonError(error); }, function () { return console.log("Delete: Success !"); });
    };
    AdminFacultyeditFaculty.prototype.DeleteFacultySJsonSucess = function (response) {
        this.router.navigate(['/AdminFaculty']);
        this.result = response.json(JSON.stringify(response._body));
        console.log("Delete: Ready SaveUserJsonSucess !");
    };
    AdminFacultyeditFaculty = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminFacultyeditFaculty.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, http_1.Http, router_1.ActivatedRoute])
    ], AdminFacultyeditFaculty);
    return AdminFacultyeditFaculty;
}());
exports.AdminFacultyeditFaculty = AdminFacultyeditFaculty;
//# sourceMappingURL=AdminFacultyeditFaculty.component.js.map