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
var AdminEvaluateRoundinit = (function () {
    function AdminEvaluateRoundinit(router, http, route) {
        this.router = router;
        this.http = http;
        this.route = route;
        this.AcademicYearTamp1 = {
            evaluateTypeDesc: "",
            round1EndDateShortThaiStr: "",
            round1StartDateShortThaiStr: "",
            round2EndDateShortThaiStr: "",
            round2StartDateShortThaiStr: ""
        };
        this.AcademicYearTamp2 = {
            evaluateTypeDesc: "",
            round1EndDateShortThaiStr: "",
            round1StartDateShortThaiStr: "",
            round2EndDateShortThaiStr: "",
            round2StartDateShortThaiStr: ""
        };
    }
    AdminEvaluateRoundinit.prototype.ngOnInit = function () {
        this.adminAcademicYear1();
        this.adminAcademicYear2();
    };
    AdminEvaluateRoundinit.prototype.adminAcademicYear1 = function () {
        var _this = this;
        var url = "../admin/json/editDateEvaluateRound/2558/1";
        this.http.get(url).subscribe(function (response) { return _this.adminSentSucess1(response); }, function (error) { return _this.GetSentError(error); }, function () { return console.log("getlistByDepartment !"); });
    };
    AdminEvaluateRoundinit.prototype.adminSentSucess1 = function (response) {
        this.AcademicYearTamp1 = response.json(JSON.stringify(response._body));
        this.AcademicYearTamp1 = this.AcademicYearTamp1.resObj;
        console.log(this.AcademicYearTamp1);
        console.log("sucsess !");
    };
    AdminEvaluateRoundinit.prototype.adminAcademicYear2 = function () {
        var _this = this;
        var url = "../admin/json/editDateEvaluateRound/2558/2";
        this.http.get(url).subscribe(function (response) { return _this.adminSentSucess2(response); }, function (error) { return _this.GetSentError(error); }, function () { return console.log("getlistByDepartment !"); });
    };
    AdminEvaluateRoundinit.prototype.adminSentSucess2 = function (response) {
        this.AcademicYearTamp2 = response.json(JSON.stringify(response._body));
        this.AcademicYearTamp2 = this.AcademicYearTamp2.resObj;
        console.log("Result2:", this.AcademicYearTamp2);
        console.log("sucsess !");
    };
    AdminEvaluateRoundinit.prototype.GetSentError = function (error) {
        console.log("getAdminAcademicYearError.");
    };
    AdminEvaluateRoundinit.prototype.getlistKPI = function () {
        var _this = this;
        var url = "../admin/pbp/academicKPI/init";
        this.http.get(url).subscribe(function (response) { return _this.GetlistKPISucess(response); }, function (error) { return _this.GetlistKPIJsonError(error); }, function () { return console.log(" Sent Success !"); });
    };
    AdminEvaluateRoundinit.prototype.GetlistKPISucess = function (response) {
        // this.model = response.json(JSON.stringify(response._body));
        // this.model = this.model.resObj;
        // this.facultyName = this.model.facultyName;
        // this.workTypeName = this.model.workTypeName;
        // this.academicYear = this.model.academicYear;
        // this.academicKPIList = this.model.academicKPIList;
        // this.academicYearList = this.model.academicYearList;
        // this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        // this.facultyList = this.model.facultyList;
        // this.searchAtti.facultyCode  = this.model.facultyCodeSelect;
        // this.searchAtti.academicYear =this.model.academicYear;
        // this.searchAtti.workTypeCode =this.model.workTypeCode;
        console.log(" List Success  !");
    };
    AdminEvaluateRoundinit.prototype.GetlistKPIJsonError = function (error) {
        console.log(" Error !");
    };
    AdminEvaluateRoundinit = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminEvaluateRoundinit.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, http_1.Http, router_1.ActivatedRoute])
    ], AdminEvaluateRoundinit);
    return AdminEvaluateRoundinit;
}());
exports.AdminEvaluateRoundinit = AdminEvaluateRoundinit;
//# sourceMappingURL=AdminEvaluateRoundinit.component.js.map