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
var AcademicWork = (function () {
    function AcademicWork(commonService, http) {
        this.commonService = commonService;
        this.http = http;
    }
    AcademicWork.prototype.ngOnInit = function () {
        this.GetUserSession();
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
        var url = "../person/getAcademicWork/" + user + "/" + year + "/" + round;
        return this.http.get(url).subscribe(function (response) { return _this.GetUserAcademicSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    AcademicWork.prototype.GetUserAcademicSucess = function (response) {
        this.academy = response.json(JSON.stringify(response._body));
        this.academyList = this.academy.pBPWorkTypeList;
        this.kpiuserList = this.academyList[0].academicKPIUserMappingList;
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