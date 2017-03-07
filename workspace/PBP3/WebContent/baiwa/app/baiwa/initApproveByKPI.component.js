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
var Common_service_1 = require('./../service/Common.service');
var http_1 = require('@angular/http');
var router_1 = require('@angular/router');
var initApproveByKPI = (function () {
    function initApproveByKPI(router, commonService, http) {
        this.router = router;
        this.commonService = commonService;
        this.http = http;
        this.tabPerson = false;
    }
    initApproveByKPI.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    initApproveByKPI.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    initApproveByKPI.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.nameDepart = this.user.facultyName;
        this.year = this.user.currentAcademicYear;
        this.academicKPI();
    };
    initApproveByKPI.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    initApproveByKPI.prototype.academicKPI = function () {
        var _this = this;
        this.commonService.loading();
        var url = "../admin/pbp/academicKPI/initApproveByKPI2/1/" + this.user.currentAcademicYear + "/" + this.user.facultyCode + "/" + this.user.departmentName;
        this.http.get(url).subscribe(function (response) { return _this.GetlistKPISucess(response); }, function (error) { return _this.GetlistKPIJsonError(error); }, function () { return console.log(" Sent Success !"); });
    };
    initApproveByKPI.prototype.GetlistKPISucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        this.facultyName = this.model.facultyName;
        this.workTypeName = this.model.workTypeName;
        this.academicYear = this.model.academicYear;
        this.academicKPIList = this.model.academicKPIList;
        this.workTypeCode = '1';
        this.academicYearList = this.model.academicYearList;
        this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        this.facultyList = this.model.facultyList;
        this.headApprove = this.model.headApprove;
        this.commonService.unLoading();
        console.log(" List Success  !");
    };
    initApproveByKPI.prototype.GetlistKPIJsonError = function (error) {
        console.log(" Error !");
    };
    initApproveByKPI.prototype.GetSearchKpI = function () {
        var _this = this;
        this.commonService.loading();
        var url = "../admin/pbp/academicKPI/initApproveByKPI2/" + this.workTypeCode + "/" + this.user.currentAcademicYear + "/" + this.user.facultyCode + "/" + this.user.departmentName;
        this.http.get(url).subscribe(function (response) { return _this.GetSearchKPISucess(response); }, function (error) { return _this.GetlistKPIJsonError(error); }, function () { return console.log(" Sent Success !"); });
    };
    initApproveByKPI.prototype.GetSearchKPISucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        this.facultyName = this.model.facultyName;
        this.workTypeName = this.model.workTypeName;
        this.academicYear = this.model.academicYear;
        this.academicKPIList = this.model.academicKPIList;
        this.academicYearList = this.model.academicYearList;
        this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        this.facultyList = this.model.facultyList;
        this.commonService.unLoading();
        console.log(" List Success  !");
    };
    initApproveByKPI.prototype.clickA = function (k_ID, summary) {
        if (summary != '0') {
            this.router.navigate(['/approveworkByKPI', k_ID, 'A']);
        }
    };
    initApproveByKPI.prototype.clickC = function (K_ID, summary) {
        if (summary != '0') {
            this.router.navigate(['/approveworkByKPI', K_ID, 'C']);
        }
    };
    initApproveByKPI = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/initApproveByKPI.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, Common_service_1.CommonService, http_1.Http])
    ], initApproveByKPI);
    return initApproveByKPI;
}());
exports.initApproveByKPI = initApproveByKPI;
//# sourceMappingURL=initApproveByKPI.component.js.map