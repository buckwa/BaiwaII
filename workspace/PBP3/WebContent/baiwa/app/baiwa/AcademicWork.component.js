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
var ng2_file_upload_1 = require('ng2-file-upload');
var URL = 'http://localhost:8080/PBP3/person/uploadMultiFile';
var AcademicWork = (function () {
    function AcademicWork(commonService, http) {
        this.commonService = commonService;
        this.http = http;
        this.kpiuserList = [];
        this.uploader = new ng2_file_upload_1.FileUploader({ url: URL });
        this.academy = this.setdefualtkpi();
        this.kpiuserList = [];
        this.kpival = [];
        this.pointKPI = this.setdefualtpoitkpi();
    }
    AcademicWork.prototype.setdefualtkpi = function () {
        return {
            "academicYear": "",
            "totalInMapping": "",
            "calResultStr": "",
            "pBPWorkTypeList": [{
                    "name": "",
                    "totalInWorkType": "",
                    "academicKPIUserMappingList": [{}]
                }]
        };
    };
    AcademicWork.prototype.setdefualtpoitkpi = function () {
        return {
            "name": "",
            "kpiUserMappingId": "",
            "calResultStr": "",
            "academicKPIAttributeValueList": [{}]
        };
    };
    AcademicWork.prototype.ngOnInit = function () {
        this.GetUserSession();
        this.uploader.onBuildItemForm = function (fileItem, form) {
            form.append('data', '2');
        };
    };
    AcademicWork.prototype.ngAfterViewInit = function () {
    };
    AcademicWork.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    AcademicWork.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.GetAcademicWork(this.user.userName, this.user.currentAcademicYear, "1");
    };
    AcademicWork.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    AcademicWork.prototype.GetAcademicWork = function (user, year, round) {
        var _this = this;
        this.commonService.loading();
        var url = "../person/getAcademicWork/" + user + "/" + year + "/" + round;
        return this.http.get(url).subscribe(function (response) { return _this.GetUserAcademicSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    AcademicWork.prototype.GetUserAcademicSucess = function (response) {
        this.academy = response.json(JSON.stringify(response._body));
        this.academyList = this.academy.pBPWorkTypeList;
        //this.kpiuserList =this.academy.pBPWorkTypeList.academicKPIUserMappingList;
        for (var i = 0; i < this.academy.pBPWorkTypeList.length; i++) {
            this.kpiuserList.push(this.academy.pBPWorkTypeList[i].academicKPIUserMappingList);
        }
        this.commonService.unLoading();
        this.mapKpi();
    };
    AcademicWork.prototype.ClickGetPointKPI = function (Code, mark) {
        var _this = this;
        this.mark = mark;
        var url = "../person/getImportWork/" + Code;
        return this.http.get(url).subscribe(function (response) { return _this.GetKPISucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    AcademicWork.prototype.GetKPISucess = function (response) {
        this.pointKPI = response.json(JSON.stringify(response._body));
        this.pointLPIList = this.pointKPI.academicKPIAttributeValueList;
    };
    AcademicWork.prototype.mapKpi = function () {
        for (var i = 0; i < this.kpiuserList.length; i++) {
            this.kpival[i] = [];
            for (var j = 0; j < this.kpiuserList[i].length; j++) {
                if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 2) {
                    var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[1].value;
                    this.kpival[i][j] = temp + "%";
                }
                else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 3) {
                    this.kpival[i][j] = "";
                }
                else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 4) {
                    var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[2].value;
                    this.kpival[i][j] = temp + "%";
                }
                else {
                    this.kpival[i][j] = "";
                }
            }
        }
    };
    AcademicWork = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AcademicWork.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], AcademicWork);
    return AcademicWork;
}());
exports.AcademicWork = AcademicWork;
//# sourceMappingURL=AcademicWork.component.js.map