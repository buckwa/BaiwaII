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
var ng2_file_upload_1 = require('ng2-file-upload');
var URL1 = 'http://localhost:8080/PBP3/admin/json/UploadFile_Profile';
//Dev  Amuletkill !
var AdminUserCreate = (function () {
    function AdminUserCreate(http) {
        this.http = http;
        this.uploader = new ng2_file_upload_1.FileUploader({ url: URL1 });
        this.CheckPass = false;
        this.submitted = false;
        this.model = this.ModelUser();
        this.person = this.PersonUser();
    }
    AdminUserCreate.prototype.ngOnInit = function () {
        this.getCreateUserJson();
        this.Datetime();
        this.uploader.onBuildItemForm = function (fileItem, form) {
            form.append('academicKPIId', "1");
        };
    };
    AdminUserCreate.prototype.ngAfterViewInit = function () {
    };
    AdminUserCreate.prototype.ModelUser = function () {
        return {
            "username": "",
            "password": "",
            "passwordConfirmation": "",
            "enabled": "",
            "groups": "",
            "person": {}
        };
    };
    AdminUserCreate.prototype.PersonUser = function () {
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
            "picture": ""
        };
    };
    AdminUserCreate.prototype.getCreateUserJson = function () {
        var _this = this;
        console.log("AdminUserCreate : Ready getlistByDepartment ");
        var url = "../admin/json/createuser";
        this.http.get(url).subscribe(function (response) { return _this.GetUserJsonSucess(response); }, function (error) { return _this.GetUserJsonError(error); }, function () { return console.log("AdminUserCreate : Success getlistByDepartment !"); });
    };
    AdminUserCreate.prototype.GetUserJsonSucess = function (response) {
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
    };
    AdminUserCreate.prototype.GetUserJsonError = function (response) {
        console.log("AdminUserCreate :GetUserJsonError " + response);
    };
    AdminUserCreate.prototype.onSubmit = function () {
        this.submitted = true;
        // if (this.model.password == this.model.passwordConfirmation) {
        //     this.CheckPass = true;
        // }
        // if (this.CheckPass) {
        // }
        console.log("GetUserSubmitted :" + this.submitted);
        this.saveUser();
    };
    AdminUserCreate.prototype.saverange = function (newValue) {
        console.log(newValue);
        //   this.range = newValue;
        //   this.Platform.ready().then(() => {
        //      this.rootRef.child("users").child(this.UserID).child('range').set(this.range)
        //   })
    };
    AdminUserCreate.prototype.saveUser = function () {
        //Ready 
        var _this = this;
        var temp = jQuery('input[type="checkbox"]:checked');
        for (var i = 0; i < temp.length; i++) {
            // console.log("Tamp",temp[i].value);
            for (var j = 0; j < this.groupList.length; j++) {
                if (temp[i].value == this.groupList[j].groupName) {
                    this.groupList[j].enable = true;
                }
            }
        }
        this.model.groupList = this.groupList;
        var birthdateStr = jQuery("#birthdateStr").val();
        var workingDateStr = jQuery("#workingDateStr").val();
        var retireDateStr = jQuery("#retireDateStr").val();
        var assignDateStr = jQuery("#assignDateStr").val();
        this.model.person.birthdateStr = birthdateStr;
        this.model.person.workingDateStr = workingDateStr;
        this.model.person.retireDateStr = retireDateStr;
        this.model.person.assignDateStr = assignDateStr;
        console.log("AdminUserCreate : Ready SaveUser");
        this.model.person = this.person;
        var url = "../admin/json/createuserSave";
        this.http.post(url, this.model).subscribe(function (response) { return _this.SaveUserJsonSucess(response); }, function (error) { return _this.SaveUserJsonError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminUserCreate.prototype.SaveUserJsonSucess = function (response) {
        //Todo
        alert("บันทึกเรียบร้อย !");
        window.location.href = '#/AdminWorkUser';
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    };
    AdminUserCreate.prototype.SaveUserJsonError = function (response) {
        //Todo
        console.log("AdminUserCreate : Success SaveUser Error !");
    };
    AdminUserCreate.prototype.uploadFileAll = function () {
        this.uploader.uploadAll();
        if (this.uploader.uploadAll()) {
            console.log("uploadsucess1");
        }
        console.log("uploadsucess2");
    };
    AdminUserCreate.prototype.onChangefile = function (event) {
        var eventObj = event;
        var target = eventObj.target;
        var files = target.files;
        this.file = files[0];
        //this.person.fileData =this.file;
        this.person.picture = this.file.name;
        this.Filename = this.file.name;
        console.log(this.file);
    };
    AdminUserCreate.prototype.Datetime = function () {
        jQuery("#birthdateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            // timePicker: true,
            locale: {
                format: 'DD/MM/YYYY'
            }
        });
        jQuery("#workingDateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            // timePicker: true,
            locale: {
                format: 'DD/MM/YYYY'
            }
        });
        jQuery("#assignDateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            // timePicker: true,
            locale: {
                format: 'DD/MM/YYYY'
            }
        });
        jQuery("#retireDateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            // timePicker: true,
            locale: {
                format: 'DD/MM/YYYY'
            }
        });
    };
    AdminUserCreate = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminUserCreate.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], AdminUserCreate);
    return AdminUserCreate;
}());
exports.AdminUserCreate = AdminUserCreate;
//# sourceMappingURL=AdminUserCreate.component.js.map