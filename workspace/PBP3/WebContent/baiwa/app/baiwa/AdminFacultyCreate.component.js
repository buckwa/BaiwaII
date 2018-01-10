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
var AdminFacultyCreate = (function () {
    function AdminFacultyCreate(http, route, router) {
        this.http = http;
        this.route = route;
        this.router = router;
    }
    AdminFacultyCreate.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) { return _this.year = params["year"]; });
        if (this.year) {
            console.log("FacultyID :", this.year);
        }
        this.adminFaculty();
    };
    AdminFacultyCreate.prototype.adminFaculty = function () {
        var _this = this;
        var url = "../admin/json/createFaculty/" + this.year;
        return this.http.get(url).subscribe(function (response) { return _this.adminFacultySucess(response); }, function (error) { return _this.adminFacultyError(error); }, function () { return console.log("DepartmentName !"); });
    };
    AdminFacultyCreate.prototype.adminFacultySucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.resObj = this.model.resObj;
        this.name = this.resObj.name;
        console.log("Succuss !");
    };
    AdminFacultyCreate.prototype.adminFacultyError = function (error) {
        console.log("Error.");
    };
    AdminFacultyCreate.prototype.adminSaveFacultyedit = function () {
        var _this = this;
        this.resObj.name = this.name;
        var url = "../admin/json/createFaculty"; //ติดไว้ก่อน
        this.http.post(url, this.resObj).subscribe(function (response) { return _this.SaveFacultySJsonSucess(response); }, function (error) { return _this.adminFacultyError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminFacultyCreate.prototype.SaveFacultySJsonSucess = function (response) {
        //Todo
        //Show Status
        this.router.navigate(['/AdminFaculty']);
        this.result = response.json(JSON.stringify(response._body));
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    };
    AdminFacultyCreate = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminFacultyCreate.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.ActivatedRoute, router_1.Router])
    ], AdminFacultyCreate);
    return AdminFacultyCreate;
}());
exports.AdminFacultyCreate = AdminFacultyCreate;
//# sourceMappingURL=AdminFacultyCreate.component.js.map