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
var AdminAcademicKPIedit = (function () {
    function AdminAcademicKPIedit(router, http, route) {
        this.router = router;
        this.http = http;
        this.route = route;
        this.submitted = false;
    }
    AdminAcademicKPIedit.prototype.ModelCreate = function () {
        return {
            "name": "",
            "mark": "",
            "unitCode": "",
            "multiplyValue": "",
            "orderNo": "",
            "description": "",
            "specialP1": "",
            "specialP2": "",
            "specialP3": "",
            "specialP4": "",
            "specialP5": "",
            "totalStudentFrom": "",
            "totalStudentTo": "",
            "fromRegis": ""
        };
    };
    AdminAcademicKPIedit.prototype.ModelCreateAcademic = function () {
        return {
            "name": "",
            "isCalculate": "",
            "isValidateNumber": "",
            "mandatory": "",
        };
    };
    AdminAcademicKPIedit.prototype.ngOnInit = function () {
        var _this = this;
        this.CreateAtti = this.ModelCreate();
        this.CreateAcademicAtti = this.ModelCreateAcademic();
        this.route.params.subscribe(function (params) { return _this.academicKPIId = params["academicKPIId"]; });
        if (this.academicKPIId) {
            console.log("academicKPIId :", this.academicKPIId);
        }
        this.route.params.subscribe(function (params) { return _this.workTypeCode = params["workTypeCode"]; });
        if (this.workTypeCode) {
            console.log("workTypeCode :", this.workTypeCode);
        }
        this.route.params.subscribe(function (params) { return _this.academicYear = params["academicYear"]; });
        if (this.academicYear) {
            console.log("academicYear :", this.academicYear);
        }
        this.route.params.subscribe(function (params) { return _this.facultyCode = params["facultyCode"]; });
        if (this.facultyCode) {
            console.log("facultyCode :", this.facultyCode);
        }
        this.SetUpdateKPI();
    };
    AdminAcademicKPIedit.prototype.onSubmit = function () {
        this.submitted = true;
        this.SetCreateAttiToKTP();
        console.log("true");
    };
    AdminAcademicKPIedit.prototype.SetUpdateKPI = function () {
        var _this = this;
        var url = "../admin/pbp/academicKPI/edit/" + this.academicKPIId;
        this.http.get(url).subscribe(function (response) { return _this.GetKPIJsonSucess(response); }, function (error) { return _this.GetKPIJsonError(error); }, function () { return console.log("Success  !"); });
    };
    AdminAcademicKPIedit.prototype.GetKPIJsonSucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        this.academicUnitList = this.model.academicUnitList;
        this.academicKPIAttributeList = this.model.academicKPIAttributeList;
        //this.CreateAcademicAtti = this.academicKPIAttributeList;
        this.SetCreateKPIToAtti();
        console.log("Success GetKPIJsonSucess  !");
    };
    AdminAcademicKPIedit.prototype.GetKPIJsonError = function (error) {
        console.log("Error !");
    };
    AdminAcademicKPIedit.prototype.SetCreateKPIToAtti = function () {
        this.CreateAtti.name = this.model.name;
        this.CreateAtti.mark = this.model.mark;
        this.CreateAtti.unitCode = this.model.unitCode;
        this.CreateAtti.multiplyValue = this.model.multiplyValue;
        this.CreateAtti.orderNo = this.model.orderNo;
        this.CreateAtti.description = this.model.description;
        this.CreateAtti.specialP1 = this.model.specialP1;
        this.CreateAtti.specialP2 = this.model.specialP2;
        this.CreateAtti.specialP3 = this.model.specialP3;
        this.CreateAtti.specialP4 = this.model.specialP4;
        this.CreateAtti.specialP5 = this.model.specialP5;
        this.CreateAtti.totalStudentFrom = this.model.totalStudentFrom;
        this.CreateAtti.totalStudentTo = this.model.totalStudentTo;
        this.CreateAtti.fromRegis = this.model.fromRegis;
    };
    AdminAcademicKPIedit.prototype.SetCreateAttiToKTP = function () {
        this.model.name = this.CreateAtti.name;
        this.model.mark = this.CreateAtti.mark;
        this.model.unitCode = this.CreateAtti.unitCode;
        this.model.multiplyValue = this.CreateAtti.multiplyValue;
        this.model.orderNo = this.CreateAtti.orderNo;
        this.model.description = this.CreateAtti.description;
        this.model.specialP1 = this.CreateAtti.specialP1;
        this.model.specialP2 = this.CreateAtti.specialP2;
        this.model.specialP3 = this.CreateAtti.specialP3;
        this.model.specialP4 = this.CreateAtti.specialP4;
        this.model.specialP5 = this.CreateAtti.specialP5;
        this.model.totalStudentFrom = this.CreateAtti.totalStudentFrom;
        this.model.totalStudentTo = this.CreateAtti.totalStudentTo;
        this.model.fromRegis = this.CreateAtti.fromRegis;
        this.model.academicKPIAttributeList = this.academicKPIAttributeList;
        this.SentCreateKPI();
    };
    AdminAcademicKPIedit.prototype.SentCreateKPI = function () {
        var _this = this;
        var url = "../admin/pbp/academicKPI/edit";
        this.http.post(url, this.model).subscribe(function (response) { return _this.CreateSuccess(response); }, function (error) { return _this.GetKPIJsonError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminAcademicKPIedit.prototype.CreateSuccess = function (response) {
        alert("Success !");
        this.router.navigate(['/AdminAcademicKPI', this.workTypeCode, this.academicYear, this.facultyCode]);
    };
    AdminAcademicKPIedit.prototype.onChange = function (event) {
        var isChecked = event.currentTarget.checked;
        console.log(isChecked);
    };
    AdminAcademicKPIedit.prototype.addNewAttribute = function () {
        var _this = this;
        var url = "../admin/pbp/academicKPI/addNewAttribute.htm/" + this.academicKPIId;
        this.http.get(url).subscribe(function (response) { return _this.CreateAttributeSuccess(response); }, function (error) { return _this.GetKPIJsonError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminAcademicKPIedit.prototype.deleteAttribute = function (academicKPIId, academicKPIAtributeId) {
        var _this = this;
        var url = "../admin/pbp/academicKPI/deleteAttribute.htm/" + academicKPIId + "/" + academicKPIAtributeId;
        this.http.get(url).subscribe(function (response) { return _this.CreateAttributeSuccess(response); }, function (error) { return _this.GetKPIJsonError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminAcademicKPIedit.prototype.CreateAttributeSuccess = function (response) {
        this.modelAttribute = response.json(JSON.stringify(response._body));
        this.modelAttribute = this.modelAttribute.resObj;
        this.academicKPIAttributeList = this.modelAttribute.academicKPIAttributeList;
        location.reload();
    };
    AdminAcademicKPIedit.prototype.back = function () {
        this.router.navigate(['/AdminAcademicKPI', this.workTypeCode, this.academicYear, this.facultyCode]);
    };
    AdminAcademicKPIedit = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminAcademicKPIedit.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, http_1.Http, router_1.ActivatedRoute])
    ], AdminAcademicKPIedit);
    return AdminAcademicKPIedit;
}());
exports.AdminAcademicKPIedit = AdminAcademicKPIedit;
//# sourceMappingURL=AdminAcademicKPIedit.component.js.map