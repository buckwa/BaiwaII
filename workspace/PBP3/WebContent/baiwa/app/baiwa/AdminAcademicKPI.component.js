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
var AdminAcademicKPI = (function () {
    function AdminAcademicKPI(commonService, router, http, route) {
        //this.getlistKPI();
        //this.GetSearchKpI();
        // if()
        this.commonService = commonService;
        this.router = router;
        this.http = http;
        this.route = route;
    }
    AdminAcademicKPI.prototype.ModelSearch = function () {
        return {
            "workTypeCode": "",
            "academicYear": "",
            "facultyCode": "",
        };
    };
    AdminAcademicKPI.prototype.ngOnInit = function () {
        var _this = this;
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
        var year = new Date().getFullYear() + 542;
        this.academicYearSelect = year;
        this.searchAtti = this.ModelSearch();
        if (this.facultyCode == '0' && this.academicYear == '0' && this.workTypeCode == '0') {
            this.getlistKPI();
        }
        else {
            this.GetSearchKpINew(this.facultyCode, this.academicYear, this.workTypeCode);
        }
    };
    AdminAcademicKPI.prototype.getlistKPI = function () {
        var _this = this;
        //console.log("getlistKPI : Ready getlistByDepartment ");
        var url = "../admin/pbp/academicKPI/init";
        this.http.get(url).subscribe(function (response) { return _this.GetlistKPISucess(response); }, function (error) { return _this.GetlistKPIJsonError(error); }, function () { return console.log(" Sent Success !"); });
    };
    AdminAcademicKPI.prototype.GetlistKPISucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        this.facultyName = this.model.facultyName;
        this.workTypeName = this.model.workTypeName;
        this.academicYear = this.model.academicYear;
        this.academicKPIList = this.model.academicKPIList;
        this.academicYearList = this.model.academicYearList;
        this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        this.facultyList = this.model.facultyList;
        this.searchAtti.facultyCode = this.model.facultyCodeSelect;
        this.searchAtti.academicYear = this.model.academicYear;
        this.searchAtti.workTypeCode = this.model.workTypeCode;
        console.log(" List Success  !");
    };
    AdminAcademicKPI.prototype.GetlistKPIJsonError = function (error) {
        console.log(" Error !");
    };
    //workTypeCode: any,academicYear:any,facultyCodeSelect:any
    AdminAcademicKPI.prototype.GetSearchKpI = function () {
        var _this = this;
        var url = "../admin/pbp/academicKPI/search/" + this.searchAtti.workTypeCode + "/" + this.searchAtti.academicYear + "/" + this.searchAtti.facultyCode;
        this.http.get(url).subscribe(function (response) { return _this.GetlistKPISucess(response); }, function (error) { return _this.GetlistKPIJsonError(error); }, function () { return console.log(" Sent Success !"); });
    };
    AdminAcademicKPI.prototype.GetSearchKPISucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        this.facultyName = this.model.facultyName;
        this.workTypeName = this.model.workTypeName;
        this.academicYear = this.model.academicYear;
        this.academicKPIList = this.model.academicKPIList;
        this.searchAtti.facultyCode = this.model.facultyCodeSelect;
        this.searchAtti.academicYear = this.model.academicYear;
        this.searchAtti.workTypeCode = this.model.workTypeCode;
        // this.academicYearList = this.model.academicYearList;
        // this.pBPWorkTypeList =this.model.pBPWorkTypeList ;
        // this.facultyList = this.model.facultyList;
        console.log(" Search Success  !", this.academicKPIList);
    };
    AdminAcademicKPI.prototype.GetCreateKPI = function () {
        this.router.navigate(['/AdminAcademicKPIcreate', this.searchAtti.workTypeCode, this.searchAtti.academicYear, this.searchAtti.facultyCode]);
        console.log(" GetCreateKPI !");
    };
    AdminAcademicKPI.prototype.GetUpdateKPI = function (academicKPIId) {
        this.router.navigate(['/AdminAcademicKPIedit', academicKPIId, this.searchAtti.workTypeCode, this.searchAtti.academicYear, this.searchAtti.facultyCode]);
        console.log(" GetUpdateKPI !");
    };
    AdminAcademicKPI.prototype.GetDeleteKPI = function (academicKPIId) {
        this.commonService.confirm("Are you sure you want to delete?", jQuery.proxy(function (isOk) {
            var _this = this;
            if (isOk) {
                //action 
                var url = "../admin/pbp/academicKPI/delete.htm/" + academicKPIId;
                this.http.get(url).subscribe(function (response) { return _this.getTimeTableSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("callsevice done !"); });
            }
        }, this));
        console.log(" GetDeleteKPI !");
    };
    AdminAcademicKPI.prototype.GetSearchKpINew = function (facultyCode, academicYear, workTypeCode) {
        var _this = this;
        var url = "../admin/pbp/academicKPI/search/" + workTypeCode + "/" + academicYear + "/" + facultyCode;
        this.http.get(url).subscribe(function (response) { return _this.GetlistKPISucess(response); }, function (error) { return _this.GetlistKPIJsonError(error); }, function () { return console.log(" Sent Success !"); });
    };
    AdminAcademicKPI.prototype.getTimeTableSucess = function () {
        location.reload();
    };
    AdminAcademicKPI = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminAcademicKPI.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, router_1.Router, http_1.Http, router_1.ActivatedRoute])
    ], AdminAcademicKPI);
    return AdminAcademicKPI;
}());
exports.AdminAcademicKPI = AdminAcademicKPI;
//# sourceMappingURL=AdminAcademicKPI.component.js.map