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
var PreviousAcademicWorkComponent = (function () {
    function PreviousAcademicWorkComponent(commonService, http) {
        this.commonService = commonService;
        this.http = http;
        this.tabPerson = false;
    }
    PreviousAcademicWorkComponent.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    PreviousAcademicWorkComponent.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    PreviousAcademicWorkComponent.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.nameDepart = this.user.facultyName;
        this.year = this.user.currentAcademicYear;
        this.academicYearList = this.user.academicYearList;
        this.currentAcademicYear = this.user.currentAcademicYear;
        this.GetPersonByAcadamy(this.user.userName, this.user.currentAcademicYear);
    };
    PreviousAcademicWorkComponent.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    PreviousAcademicWorkComponent.prototype.GetPersonByAcadamy = function (user, year) {
        var _this = this;
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year;
        this.http.get(url).subscribe(function (response) { return _this.GetPersonSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    PreviousAcademicWorkComponent.prototype.GetPersonSucess = function (response) {
        this.profile = response.json(JSON.stringify(response._body));
        this.initTotle(this.currentAcademicYear);
    };
    PreviousAcademicWorkComponent.prototype.GetPersonError = function (error) {
        console.log("GetPersonError.");
    };
    PreviousAcademicWorkComponent.prototype.initTotle = function (year) {
        var _this = this;
        this.commonService.loading();
        var url = "../head/initByYear/" + this.profile.evaluateRound + "/" + year;
        this.http.get(url).subscribe(function (response) { return _this.initTotleSucess(response); }, function (error) { return _this.initTotlError(error); }, function () { return console.log("editdoneUser !"); });
    };
    PreviousAcademicWorkComponent.prototype.initTotleSucess = function (response) {
        var bodyJson;
        bodyJson = response.json(JSON.stringify(response._body));
        this.academicPersonList = bodyJson.department.academicPersonList;
        console.log(this.academicPersonList);
        this.commonService.unLoading();
    };
    PreviousAcademicWorkComponent.prototype.initTotlError = function (error) {
        console.log("error getTotle");
    };
    PreviousAcademicWorkComponent.prototype.changeYear = function (year) {
        // alert(year);
        this.initTotle(year);
    };
    PreviousAcademicWorkComponent = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/previousAcademicWork.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], PreviousAcademicWorkComponent);
    return PreviousAcademicWorkComponent;
}());
exports.PreviousAcademicWorkComponent = PreviousAcademicWorkComponent;
//# sourceMappingURL=PreviousAcademicWork.component.js.map