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
//Dev  Amuletkill !
var AdminUserEdit = (function () {
    function AdminUserEdit(http, route) {
        this.http = http;
        this.route = route;
        this.submitted = false;
        this.model = this.ModelUser();
        this.person = this.PersonUser();
        console.log("Sub : ", this.sub);
    }
    AdminUserEdit.prototype.ngOnInit = function () {
        var _this = this;
        this.getCreateUserJson();
        this.route.params.subscribe(function (params) { return _this.user = params["user"]; });
        if (this.user) {
            console.log("User :", this.user);
        }
        else {
        }
    };
    AdminUserEdit.prototype.ngAfterViewInit = function () {
    };
    AdminUserEdit.prototype.ModelUser = function () {
        return {
            "username": "",
            "password": "",
            "passwordConfirmation": "",
            "enabled": "",
            "groups": "",
            "person": {}
        };
    };
    AdminUserEdit.prototype.PersonUser = function () {
        return {
            "thaiName": "",
            "thaiSurname": "",
            "sex": "",
            "birthdate": "",
            "rateNo": "",
            "employeeType": "",
            "position": "",
            "level": "",
            "workLine": "",
            "salary": "",
            "workTelNo": "",
            "belongTo": "",
            "faculty": "",
            "workingDate": "",
            "assignDate": "",
            "retireDate": "",
            "maxInsignia": "",
            "maxEducation": "",
            "taxNo": "",
            "workingStatus": "",
            "marriedStatus": "",
            "workNumber": "",
            "insureNo": "",
            "fund": "",
            "address": "",
            "zipCode": "",
            "telNo": "",
            "email": "",
            "oldWorklineCode": "",
            "worklineCode": "",
            "worklineName": "",
        };
    };
    AdminUserEdit.prototype.getCreateUserJson = function () {
        var _this = this;
        console.log("AdminUserCreate : Ready getlistByDepartment ");
        var url = "../admin/json/createuser";
        this.http.get(url).subscribe(function (response) { return _this.GetUserJsonSucess(response); }, function (error) { return _this.GetUserJsonError(error); }, function () { return console.log("AdminUserCreate : Success getlistByDepartment !"); });
    };
    AdminUserEdit.prototype.GetUserJsonSucess = function (response) {
        console.log("AdminUserCreate : Ready GetUserJsonSucess");
        this.modelUser = response.json(JSON.stringify(response._body));
        this.modelUser = this.modelUser[0];
        this.model = this.modelUser;
        this.person = this.modelUser.person;
        this.groupList = this.modelUser.groupList;
        this.lovSexList = this.modelUser.person.lovSexList;
        this.lovEmployeeTypeList = this.modelUser.person.lovEmployeeTypeList;
        this.lovPositionList = this.modelUser.person.lovPositionList;
        this.lovWorkLineList = this.modelUser.person.lovWorkLineList;
        this.lovFacultyList = this.modelUser.person.lovFacultyList;
        this.lovInsigniaList = this.modelUser.person.lovInsigniaList;
        this.lovMarriedStatusList = this.modelUser.person.lovMarriedStatusList;
        this.lovEducationList = this.modelUser.person.lovEducationList;
        this.lovWorkingStatusList = this.modelUser.person.lovWorkingStatusList;
        this.evaluateRoundList = this.modelUser.person.evaluateRoundList;
        console.log("AdminUserCreate :GetUserJsonSucess !");
        this.getEditUserJson();
    };
    AdminUserEdit.prototype.GetUserJsonError = function (response) {
        console.log("AdminUserCreate :GetUserJsonError " + response);
    };
    AdminUserEdit.prototype.onSubmit = function () {
        this.submitted = true;
        console.log("GetUserSubmitted :" + this.submitted);
        this.saveUser();
    };
    AdminUserEdit.prototype.saveUser = function () {
        //Ready 
        var _this = this;
        console.log("AdminUserCreate : Ready SaveUser");
        var url = "../admin/json/createuserSave"; //ติดไว้ก่อน
        this.http.post(url, this.model).subscribe(function (response) { return _this.SaveUserJsonSucess(response); }, function (error) { return _this.SaveUserJsonError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminUserEdit.prototype.SaveUserJsonSucess = function (response) {
        //Todo
        //Show Status
        window.location.href = 'http://www.google.com/';
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    };
    AdminUserEdit.prototype.SaveUserJsonError = function (response) {
        //Todo
        console.log("AdminUserCreate : Success SaveUser Error !");
    };
    AdminUserEdit.prototype.getEditUserJson = function () {
        var _this = this;
        console.log("AdminUserCreate : Ready getlistByDepartment ");
        var url = "../admin/json/editUser/" + this.user;
        this.http.get(url).subscribe(function (response) { return _this.GetUserEditJsonSucess(response); }, function (error) { return _this.GetUserEditJsonError(error); }, function () { return console.log("AdminUserCreate : Success getlistByDepartment !"); });
    };
    AdminUserEdit.prototype.GetUserEditJsonSucess = function (response) {
        console.log("AdminUserCreate : Ready GetUserJsonSucess");
        this.modelEdit = response.json(JSON.stringify(response._body));
    };
    AdminUserEdit.prototype.GetUserEditJsonError = function (response) {
        console.log("AdminUserCreate : Error");
    };
    AdminUserEdit = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminUserEdit.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.ActivatedRoute])
    ], AdminUserEdit);
    return AdminUserEdit;
}());
exports.AdminUserEdit = AdminUserEdit;
//# sourceMappingURL=AdminUserEdit.component.js.map