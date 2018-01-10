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
var AssignHead = (function () {
    function AssignHead(http, router, commonService) {
        this.http = http;
        this.router = router;
        this.commonService = commonService;
    }
    AssignHead.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    AssignHead.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetuserSucess(response); }, function (error) { return _this.GetuserError(error); }, function () { return console.log("editdone !"); });
    };
    AssignHead.prototype.GetuserSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.academicYearList = this.user.academicYearList;
        this.currentAcademicYear = this.user.currentAcademicYear;
        this.departmentName = this.user.departmentName;
        this.GetPersonList();
    };
    AssignHead.prototype.GetuserError = function (response) {
        console.log("Error !");
    };
    AssignHead.prototype.GetPersonListError = function (response) {
        console.log("Error !");
    };
    AssignHead.prototype.GetPersonList = function () {
        var _this = this;
        this.commonService.loading();
        var url = "../head/AssignHeadY/" + this.departmentName;
        return this.http.get(url).subscribe(function (response) { return _this.GetPersonListSucess(response); }, function (error) { return _this.GetPersonListError(error); }, function () { return console.log("editdone !"); });
    };
    AssignHead.prototype.GetPersonListSucess = function (response) {
        this.Person1 = response.json(JSON.stringify(response._body));
        this.GetPersonListN();
    };
    AssignHead.prototype.GetPersonListN = function () {
        var _this = this;
        var url = "../head/AssignHeadN/" + this.departmentName;
        return this.http.get(url).subscribe(function (response) { return _this.GetPersonListNSucess(response); }, function (error) { return _this.GetPersonListError(error); }, function () { return console.log("editdone !"); });
    };
    AssignHead.prototype.GetPersonListNSucess = function (response) {
        this.Person2 = response.json(JSON.stringify(response._body));
        this.commonService.unLoading();
    };
    AssignHead.prototype.DeletePersonList = function (username) {
        var _this = this;
        var url = "../head/AssignHeadDelete/" + username + "/2";
        return this.http.get(url).subscribe(function (response) { return _this.DeletePersonListNSucess(response); }, function (error) { return _this.GetPersonListError(error); }, function () { return console.log("editdone !"); });
    };
    AssignHead.prototype.DeletePersonListNSucess = function (response) {
        alert("บันทึกเรียบร้อย");
        location.reload();
    };
    AssignHead.prototype.EditPersonList = function (username) {
        var _this = this;
        var url = "../head/AssignHeadEdit/" + username + "/1";
        return this.http.get(url).subscribe(function (response) { return _this.EditPersonListNSucess(response); }, function (error) { return _this.GetPersonListError(error); }, function () { return console.log("editdone !"); });
    };
    AssignHead.prototype.EditPersonListNSucess = function (response) {
        alert("บันทึกเรียบร้อย");
        location.reload();
    };
    AssignHead = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AssignHead.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.Router, Common_service_1.CommonService])
    ], AssignHead);
    return AssignHead;
}());
exports.AssignHead = AssignHead;
//# sourceMappingURL=AssignHead.component.js.map