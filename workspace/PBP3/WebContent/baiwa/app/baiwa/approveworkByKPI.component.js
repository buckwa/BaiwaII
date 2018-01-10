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
var router_1 = require('@angular/router');
var http_1 = require('@angular/http');
var Common_service_1 = require('./../service/Common.service');
var approveworkByKPI = (function () {
    function approveworkByKPI(route, http, commonService, router) {
        this.route = route;
        this.http = http;
        this.commonService = commonService;
        this.router = router;
        this.pointKPI = this.setdefualtpoitkpi();
        this.academicKPI = this.setacademicKPIdefault();
        this.academicKPIUserMappingList = this.kpiusermappingList();
    }
    approveworkByKPI.prototype.setdefualtpoitkpi = function () {
        return {
            "name": "",
            "kpiUserMappingId": "",
            "calResultStr": "",
            "status": "",
            "description": "",
            "academicKPIAttributeValueList": [{}]
        };
    };
    approveworkByKPI.prototype.setacademicKPIdefault = function () {
        return {
            "name": "",
            "unitDesc": "",
            "mark": ""
        };
    };
    approveworkByKPI.prototype.kpiusermappingList = function () {
        return [{
                "status": "",
                "kpiUserMappingId": "",
                "academicKPIAttributeValueList": [{}]
            }];
    };
    approveworkByKPI.prototype.ngOnInit = function () {
        var _this = this;
        this.sub = this.route.params.subscribe(function (params) {
            _this.code = params['code']; // (+) converts string 'id' to a number
            _this.status = +params['status'];
            //In a real app: dispatch action to load the details here.
        });
        this.route.params.subscribe(function (params) { return _this.code = params["code"]; });
        if (this.code) {
            console.log("code :", this.code);
        }
        this.route.params.subscribe(function (params) { return _this.status = params["status"]; });
        if (this.status) {
            console.log("status :", this.status);
        }
        this.route.params.subscribe(function (params) { return _this.workcode = params["workcode"]; });
        if (this.status) {
            console.log("workcode :", this.workcode);
        }
        this.GetUserSession();
        //this.getwork();
    };
    approveworkByKPI.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    approveworkByKPI.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.GetPersonByAcadamy(this.user.userName, this.user.currentAcademicYear);
    };
    approveworkByKPI.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    approveworkByKPI.prototype.GetPersonByAcadamy = function (user, year) {
        var _this = this;
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year;
        this.http.get(url).subscribe(function (response) { return _this.GetPersonSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    approveworkByKPI.prototype.GetPersonSucess = function (response) {
        this.profile = response.json(JSON.stringify(response._body));
        this.getwork();
    };
    approveworkByKPI.prototype.GetPersonError = function (error) {
        console.log("GetPersonError.");
    };
    approveworkByKPI.prototype.getwork = function () {
        var _this = this;
        //this.personWorkList = this.personWork.academicKPIUserMappingList;
        this.commonService.loading();
        ///initByUserNameNew/{code}/{status}/{facultyCode}/{department_desc}/{employeeType}
        var url = "../head/initByUserNameNew/" + this.code + "/" + this.status + "/" + this.user.facultyCode + "/" + this.user.departmentName + "/" + this.profile.evaluateRound;
        this.http.get(url).subscribe(function (response) { return _this.getkpiworksucess(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approveworkByKPI.prototype.getkpiworkeror = function (error) {
        console.log("error !");
    };
    approveworkByKPI.prototype.getkpiworksucess = function (response) {
        var json = response.json(JSON.stringify(response._body));
        this.academicKPIUserMappingList = json.academicKPIUserMappingList;
        //this.person = json.department.academicPersonList[0].thaiName +"  "+ json.department.academicPersonList[0].thaiSurname;
        //console.log("getkpimapping :" +this.academicKPIUserMappingList)
        this.commonService.unLoading();
    };
    approveworkByKPI.prototype.blackpage = function () {
        this.router.navigate(['/initApproveByKPI', this.workcode]);
    };
    approveworkByKPI.prototype.ClickGetPointKPI = function (Code, indexKPI) {
        var _this = this;
        this.indexKPI = indexKPI;
        this.codeNew = Code;
        var url = "../person/getImportWorkNew/" + Code;
        return this.http.get(url).subscribe(function (response) { return _this.GetKPISucess(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approveworkByKPI.prototype.ClickGetPointKPINew = function (Code, indexKPI) {
        var _this = this;
        this.indexKPI = indexKPI;
        var url = "../person/getImportWorkNew/" + Code;
        return this.http.get(url).subscribe(function (response) { return _this.GetKPISucessNew(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approveworkByKPI.prototype.GetKPISucessNew = function (response) {
        this.Model = response.json(JSON.stringify(response._body));
        this.pointKPI = this.Model.academicKPIUserMapping;
        if (this.pointKPI.messageList != null) {
            this.messageList = this.pointKPI.messageList;
        }
    };
    approveworkByKPI.prototype.GetKPISucess = function (response) {
        this.Model = response.json(JSON.stringify(response._body));
        this.pointKPI = this.Model.academicKPIUserMapping;
        this.pointLPIList = this.pointKPI.academicKPIAttributeValueList;
        this.fileWork = this.pointKPI.academicKPIAttachFileList;
        this.academicKPI = this.pointKPI.academicKPI;
        if (this.fileWork.length == 0) {
            this.chFilework = true;
        }
        else {
            this.chFilework = false;
        }
        if (this.pointKPI.status == "APPROVED") {
            this.statusKpi = true;
        }
        else {
            this.statusKpi = false;
        }
        if (this.pointKPI.messageList != null && this.status == 'C') {
            this.messageList = this.pointKPI.messageList;
        }
    };
    approveworkByKPI.prototype.approveKPIWork = function (KPIId) {
        var _this = this;
        var url = "../head/approveWork/" + KPIId;
        this.http.get(url).subscribe(function (response) { return _this.approveSucess(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approveworkByKPI.prototype.unapproveKPIWork = function (KPIId) {
        var _this = this;
        var url = "../head/unApprove/" + KPIId;
        this.http.get(url).subscribe(function (response) { return _this.unApproveSucess(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approveworkByKPI.prototype.unApproveSucess = function (response) {
        var temp = response.json(JSON.stringify(response._body));
        if (temp.status == '0') {
            this.academicKPIUserMappingList[this.indexKPI].status = 'CREATE';
            this.statusKpi = false;
        }
        jQuery("#myModal").modal('hide');
        console.log("unApproveSucess!");
    };
    approveworkByKPI.prototype.approveSucess = function (response) {
        var temp = response.json(JSON.stringify(response._body));
        if (temp.status == '0') {
            this.academicKPIUserMappingList[this.indexKPI].status = 'APPROVED';
            this.statusKpi = true;
        }
        jQuery("#myModal").modal('hide');
        console.log("ApproveSucess!");
    };
    approveworkByKPI.prototype.sentReplyPBPMessage = function () {
        var _this = this;
        if (this.replyMessage != null) {
            this.Model.replyMessage = this.replyMessage;
            var url = "../head/pbp/replyMessage"; //ติดไว้ก่อน
            this.http.post(url, this.Model).subscribe(function (response) { return _this.ReplyPBPMessageSucess(response); }, function (error) { return _this.ReplyPBPMessageError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
        }
    };
    approveworkByKPI.prototype.ReplyPBPMessageSucess = function (response) {
        var temp = response.json(JSON.stringify(response._body));
        this.ClickGetPointKPINew(this.codeNew, this.indexKPI);
    };
    approveworkByKPI.prototype.ReplyPBPMessageError = function (response) {
        console.log("Error!");
    };
    approveworkByKPI = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/approveworkByKPI.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.ActivatedRoute, http_1.Http, Common_service_1.CommonService, router_1.Router])
    ], approveworkByKPI);
    return approveworkByKPI;
}());
exports.approveworkByKPI = approveworkByKPI;
//# sourceMappingURL=approveworkByKPI.component.js.map