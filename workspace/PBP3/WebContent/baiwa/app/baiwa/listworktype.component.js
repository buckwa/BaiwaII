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
var listworktype = (function () {
    function listworktype(commonService, http) {
        this.commonService = commonService;
        this.http = http;
        this.libPath = "/PBP3/baiwa/libs/";
        this.Inport = this.defaultInport();
    }
    listworktype.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    listworktype.prototype.ngAfterViewInit = function () {
    };
    listworktype.prototype.defaultInport = function () {
        return {
            "status": "",
            "rownum": "",
            "createDate": "",
            "updateDate": "",
            "createBy": "",
            "updateBy": "",
            "name": "",
            "code": "",
            "description": "",
            "academicKPIId": "",
            "academicRuleId": "",
            "academicYear": "",
            "workTypeCode": "",
            "mark": "",
            "unitCode": "",
            "unitDesc": "",
            "facultyCode": "",
            "specialP1": "",
            "specialP2": "",
            "specialP3": "",
            "specialP4": "",
            "specialP5": "",
            "totalStudentFrom": "",
            "totalStudentTo": "",
            "multiplyValue": "",
            "academicUnitList": "",
            "academicKPIAttributeList": "",
            "fileData": "",
            "orderNo": "",
            "fromRegis": "",
            "errorDesc": "",
            "academicKPIUserMappingId": "",
            "replyMessage": "",
            "ratio": "",
            "remark": "",
            "tmpFileNameList": "",
            "index": "",
            "createDateTimeStr": "",
            "updateDateTimeStr": "",
            "createDateStr": "",
            "updateDateStr": "",
        };
    };
    listworktype.prototype.jsondefualt = function () {
        return {
            "workTypeName": "",
            "academicKPIList": {}
        };
    };
    listworktype.prototype.GetDataInport = function (facultyCode, currentAcademicYear) {
        var _this = this;
        var url = "../person/getAllWorkList/" + currentAcademicYear + "/" + facultyCode;
        this.http.get(url).subscribe(function (response) { return _this.GetSucess(response); }, function (error) { return _this.GetError(error); }, function () { return console.log("editdoneInportt !"); });
    };
    listworktype.prototype.GetSucess = function (response) {
        this.Inport = response.json(JSON.stringify(response._body));
        this.inport0 = this.Inport[0];
        this.inport1 = this.Inport[1];
        this.inport2 = this.Inport[3];
        this.inport3 = this.Inport[3];
        this.inport4 = this.Inport[4];
    };
    listworktype.prototype.GetError = function (error) {
        console.log("GetPersonError.");
    };
    listworktype.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    listworktype.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.GetDataInport(this.user.facultyCode, this.user.currentAcademicYear);
    };
    listworktype.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    listworktype = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/listworktype.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], listworktype);
    return listworktype;
}());
exports.listworktype = listworktype;
//# sourceMappingURL=listworktype.component.js.map