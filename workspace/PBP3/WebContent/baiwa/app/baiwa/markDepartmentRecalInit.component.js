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
var markDepartmentRecalInit = (function () {
    function markDepartmentRecalInit(http, commonService) {
        this.http = http;
        this.commonService = commonService;
        this.kpiuserList = [];
    }
    markDepartmentRecalInit.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    markDepartmentRecalInit.prototype.ngAfterViewInit = function () {
    };
    markDepartmentRecalInit.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    markDepartmentRecalInit.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.departmentName = this.user.departmentName;
        this.currentAcademicYear = this.user.currentAcademicYear;
    };
    markDepartmentRecalInit.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    markDepartmentRecalInit.prototype.ClickAction = function () {
        var _this = this;
        this.commonService.loading();
        var url = "../head/pbp/markDepartmentRecal";
        return this.http.get(url).subscribe(function (response) { return _this.ClickActionSucess(response); }, function (error) { return _this.GetActionUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    markDepartmentRecalInit.prototype.ClickActionSucess = function (response) {
        this.commonService.unLoading();
        this.model = response.json(JSON.stringify(response._body));
        alert("เรียบร้อย");
    };
    markDepartmentRecalInit.prototype.GetActionUserSessionError = function (error) {
        this.commonService.unLoading();
        console.log("GetPersonError.");
    };
    markDepartmentRecalInit = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/markDepartmentRecalInit.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, Common_service_1.CommonService])
    ], markDepartmentRecalInit);
    return markDepartmentRecalInit;
}());
exports.markDepartmentRecalInit = markDepartmentRecalInit;
//# sourceMappingURL=markDepartmentRecalInit.component.js.map