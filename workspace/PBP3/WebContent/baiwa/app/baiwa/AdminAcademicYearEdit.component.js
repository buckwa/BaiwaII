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
var common_1 = require('@angular/common');
var AdminAcademicYearEditComponent = (function () {
    function AdminAcademicYearEditComponent(router, http, route, _location) {
        this.router = router;
        this.http = http;
        this.route = route;
        this._location = _location;
        this.AcademicYearTamp = {
            "name": "",
            "startDateStr": "",
            "endDateStr": "",
            "termstartDate": "",
            "termendDate": ""
        };
    }
    AdminAcademicYearEditComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) { return _this.year = params["year"]; });
        this.route.params.subscribe(function (params) { return _this.startDate = params["startDate"]; });
        this.route.params.subscribe(function (params) { return _this.endDate = params["endDate"]; });
        if (this.year && this.startDate && this.endDate) {
            console.log("Input Year :", this.year);
            console.log("Input StartDate :", this.startDate);
            console.log("Input EndDate :", this.endDate);
        }
        this.getData();
    };
    AdminAcademicYearEditComponent.prototype.getData = function () {
        var _this = this;
        var url = "../admin/json/getAcademicYearEdit/" + this.year;
        this.http.get(url).subscribe(function (response) { return _this.adminSentSucess(response); }, function (error) { return _this.GetSentError(error); }, function () { return console.log("getlistByDepartment !"); });
    };
    AdminAcademicYearEditComponent.prototype.adminSentSucess = function (response) {
        // alert("Success !  1");
        // console.log("Result 1:",response.json(JSON.stringify(response._body)));
        this.AcademicYearTamp = response.json(JSON.stringify(response._body));
        this.AcademicYearTamp = this.AcademicYearTamp.resObj;
        console.log("Data Set", this.AcademicYearTamp);
        // this.router.navigate(['/AdminAcademicKPI',this.workTypeCode,this.academicYear,this.facultyCode]);
    };
    AdminAcademicYearEditComponent.prototype.saveClicked = function () {
        var _this = this;
        // this.AcademicYearTamp.startDateStr = this.AcademicYearTamp.startDate;
        // this.AcademicYearTamp.endDateStr = this.AcademicYearTamp.endDate;
        console.log("Data Sent", this.AcademicYearTamp);
        var url = "../admin/json/editDateAcademicYear";
        this.http.post(url, this.AcademicYearTamp).subscribe(function (response) { return _this.CreateSuccess(response); }, function (error) { return _this.GetSentError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminAcademicYearEditComponent.prototype.CreateSuccess = function (response) {
        // alert("Success !");
        // console.log("Result 2:",response.json(JSON.stringify(response._body)));
        this.router.navigate(['/AdminAcademicYear']);
    };
    AdminAcademicYearEditComponent.prototype.GetSentError = function (response) {
        alert("Sent Error !");
    };
    AdminAcademicYearEditComponent.prototype.backClicked = function () {
        this._location.back();
    };
    AdminAcademicYearEditComponent = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminAcademicYearEdit.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, http_1.Http, router_1.ActivatedRoute, common_1.Location])
    ], AdminAcademicYearEditComponent);
    return AdminAcademicYearEditComponent;
}());
exports.AdminAcademicYearEditComponent = AdminAcademicYearEditComponent;
//# sourceMappingURL=AdminAcademicYearEdit.component.js.map