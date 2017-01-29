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
var AdminAcademicKPIcreate = (function () {
    function AdminAcademicKPIcreate(router, http, route) {
        this.router = router;
        this.http = http;
        this.route = route;
        this.submitted = false;
    }
    AdminAcademicKPIcreate.prototype.ModelCreate = function () {
        return {
            "name": "",
            "mark": "",
            "unitCode": "",
            "multiplyValue": "",
            "orderNo": "",
            "description": "",
        };
    };
    AdminAcademicKPIcreate.prototype.ngOnInit = function () {
        var _this = this;
        this.CreateAtti = this.ModelCreate();
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
        this.getCreateKPIJson();
    };
    AdminAcademicKPIcreate.prototype.onSubmit = function () {
        this.submitted = true;
        this.SetCreateKPI();
        console.log("true");
    };
    AdminAcademicKPIcreate.prototype.getCreateKPIJson = function () {
        var _this = this;
        var url = "../admin/pbp/academicKPI/create.htm/" + this.workTypeCode + "/" + this.academicYear + "/" + this.facultyCode;
        this.http.get(url).subscribe(function (response) { return _this.GetKPIJsonSucess(response); }, function (error) { return _this.GetKPIJsonError(error); }, function () { return console.log("Success  !"); });
    };
    AdminAcademicKPIcreate.prototype.GetKPIJsonSucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        this.academicUnitList = this.model.academicUnitList;
        console.log("Success GetKPIJsonSucess  !");
    };
    AdminAcademicKPIcreate.prototype.GetKPIJsonError = function (error) {
        console.log("Error !");
    };
    AdminAcademicKPIcreate.prototype.SetCreateKPI = function () {
        this.model.name = this.CreateAtti.name;
        this.model.mark = this.CreateAtti.mark;
        this.model.unitCode = this.CreateAtti.unitCode;
        this.model.multiplyValue = this.CreateAtti.multiplyValue;
        this.model.orderNo = this.CreateAtti.orderNo;
        this.model.description = this.CreateAtti.description;
        this.SentCreateKPI();
    };
    AdminAcademicKPIcreate.prototype.SentCreateKPI = function () {
        var _this = this;
        var url = "../admin/pbp/academicKPI/create";
        this.http.post(url, this.model).subscribe(function (response) { return _this.CreateSuccess(response); }, function (error) { return _this.GetKPIJsonError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminAcademicKPIcreate.prototype.CreateSuccess = function (response) {
        alert("Success !");
        this.router.navigate(['/AdminAcademicKPI']);
    };
    AdminAcademicKPIcreate = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminAcademicKPIcreate.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, http_1.Http, router_1.ActivatedRoute])
    ], AdminAcademicKPIcreate);
    return AdminAcademicKPIcreate;
}());
exports.AdminAcademicKPIcreate = AdminAcademicKPIcreate;
//# sourceMappingURL=AdminAcademicKPIcreate.component.js.map