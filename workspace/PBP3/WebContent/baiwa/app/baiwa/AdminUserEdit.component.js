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
    function AdminUserEdit(router, http, route) {
        this.router = router;
        this.http = http;
        this.route = route;
        this.javascript = false;
        this.angular = false;
        this.csharp = false;
        this.Department = [];
        this.submitted = false;
        this.model = this.ModelUser();
        this.person = this.PersonUser();
        console.log("Sub : ", this.sub);
    }
    AdminUserEdit.prototype.ngOnInit = function () {
        var _this = this;
        this.adminFaculty();
        this.route.params.subscribe(function (params) { return _this.user = params["user"]; });
        if (this.user) {
            console.log("User :", this.user);
        }
        else {
        }
        this.Datetime();
    };
    AdminUserEdit.prototype.ngAfterViewInit = function () {
        this.getCreateUserJson();
        this.makeDatePicker();
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
        var url = "../admin/json/editUser/" + this.user + "/work";
        this.http.get(url).subscribe(function (response) { return _this.GetUserJsonSucess(response); }, function (error) { return _this.GetUserJsonError(error); }, function () { return console.log("AdminUserCreate : Success getlistByDepartment !"); });
    };
    AdminUserEdit.prototype.GetUserJsonSucess = function (response) {
        var _this = this;
        console.log("AdminUserCreate : Ready GetUserJsonSucess");
        this.modelUser = response.json(JSON.stringify(response._body));
        this.modelUser = this.modelUser.resObj;
        this.model = this.modelUser;
        this.person = this.modelUser.person;
        this.person.department = this.modelUser.person.departmentDesc;
        this.person.faculty = this.modelUser.person.facultyDesc;
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
        setTimeout(function () {
            for (var i = 0; i < _this.modelUser.groups.length; i++) {
                for (var j = 0; j < _this.groupList.length; j++) {
                    if (_this.modelUser.groups[i] == _this.groupList[j].groupId) {
                        var roleName = _this.groupList[j].groupName;
                        console.log("Finde Roles", roleName);
                        jQuery("#" + roleName).attr("checked", true);
                        break;
                    }
                }
            }
        }, 800);
        console.log("AdminUserCreate :GetUserJsonSucess !");
        console.log(this.groupList);
        //this.getEditUserJson();
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
        var temp = jQuery('input[type="checkbox"]:checked');
        this.model.groups = [];
        for (var i = 0; i < temp.length; i++) {
            console.log('temp=', temp[i].value);
            this.model.groups.push(temp[i].value);
            console.log('groups=', this.model.groups);
        }
        var birthdateStr = jQuery("#birthdateStr").val();
        var workingDateStr = jQuery("#workingDateStr").val();
        this.model.person.birthdateStr = birthdateStr;
        this.model.person.workingDateStr = workingDateStr;
        console.log("AdminUserCreate : Ready SaveUser");
        var url = "../admin/json/editUserSave"; //ติดไว้ก่อน
        this.http.post(url, this.model).subscribe(function (response) { return _this.SaveUserJsonSucess(response); }, function (error) { return _this.SaveUserJsonError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
    };
    AdminUserEdit.prototype.SaveUserJsonSucess = function (response) {
        //Todo
        //Show Status
        alert("บันทึกเรียบร้อย !");
        this.router.navigate(['/AdminWorkUser']);
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    };
    AdminUserEdit.prototype.SaveUserJsonError = function (response) {
        //Todo
        console.log("AdminUserCreate : Success SaveUser Error !");
    };
    AdminUserEdit.prototype.GetUserEditJsonError = function (response) {
        console.log("AdminUserCreate : Error");
    };
    AdminUserEdit.prototype.makeDatePicker = function () {
    };
    AdminUserEdit.prototype.Datetime = function () {
        jQuery("#birthdateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            locale: {
                format: 'MM/DD/YYYY'
            }
        });
        jQuery("#workingDateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            locale: {
                format: 'MM/DD/YYYY'
            }
        });
        jQuery("#assignDateStrU").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            locale: {
                format: 'MM/DD/YYYY'
            }
        });
        jQuery("#retireDateStrU").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            locale: {
                format: 'MM/DD/YYYY'
            }
        });
    };
    AdminUserEdit.prototype.adminFaculty = function () {
        var _this = this;
        //console.log(year);
        var year = new Date().getFullYear() + 542;
        var url = "../admin/json/getFaculty/" + year;
        return this.http.get(url).subscribe(function (response) { return _this.adminFacultySucess(response); }, function (error) { return _this.adminFacultyError(error); }, function () { return console.log("DepartmentName !"); });
    };
    AdminUserEdit.prototype.adminFacultySucess = function (response) {
        this.Facultyname = response.json(JSON.stringify(response._body));
        this.Faculty = this.Facultyname[0].facultyList;
        this.setdepartment();
        console.log("SS !");
    };
    AdminUserEdit.prototype.adminFacultyError = function (error) {
        console.log("GetadminFacultyError.");
    };
    AdminUserEdit.prototype.Adddepartment = function (faculty) {
        for (var i = 0; i < this.Faculty.length; i++) {
            console.log("this.Faculty[i].name ", this.Faculty[i].name);
            console.log("this.person.faculty ", faculty);
            if (this.Faculty[i].name == faculty) {
                this.Department = this.Faculty[i].departmentList;
                break;
            }
        }
    };
    AdminUserEdit.prototype.setdepartment = function () {
        for (var i = 0; i <= this.Faculty.length; i++) {
            console.log("this.Faculty[i].name ", this.Faculty[i].name);
            console.log("this.person.faculty ", this.person.faculty);
            if (this.Faculty[i].name == this.person.faculty) {
                this.Department = this.Faculty[i].departmentList;
                break;
            }
        }
    };
    AdminUserEdit.prototype.back = function () {
        this.router.navigate(['/AdminWorkUser']);
    };
    AdminUserEdit = __decorate([
        core_1.Component({
            selector: 'input-controls',
            templateUrl: 'app/baiwa/html/AdminUserEdit.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, http_1.Http, router_1.ActivatedRoute])
    ], AdminUserEdit);
    return AdminUserEdit;
}());
exports.AdminUserEdit = AdminUserEdit;
//# sourceMappingURL=AdminUserEdit.component.js.map