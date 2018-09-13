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
var common_1 = require('@angular/common');
var approvework = (function () {
    function approvework(router, route, http, commonService, _location) {
        this.router = router;
        this.route = route;
        this.http = http;
        this.commonService = commonService;
        this._location = _location;
        this.pointKPI = this.setdefualtpoitkpi();
        this.academicKPI = this.setacademicKPIdefault();
        this.academicKPIUserMappingList = this.kpiusermappingList();
    }
    approvework.prototype.setdefualtpoitkpi = function () {
        return {
            "name": "",
            "kpiUserMappingId": "",
            "calResultStr": "",
            "status": "",
            "description": "",
            "academicKPIAttributeValueList": [{}]
        };
    };
    approvework.prototype.setacademicKPIdefault = function () {
        return {
            "name": "",
            "unitDesc": "",
            "mark": ""
        };
    };
    approvework.prototype.kpiusermappingList = function () {
        return [{
                "status": "",
                "kpiUserMappingId": "",
                "academicKPIAttributeValueList": [{}]
            }];
    };
    approvework.prototype.ngOnInit = function () {
        var _this = this;
        this.sub = this.route.params.subscribe(function (params) {
            _this.email = params['email']; // (+) converts string 'id' to a number
            _this.rond = +params['rond'];
            // In a real app: dispatch action to load the details here.
        });
        this.GetUserSession();
    };
    approvework.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    approvework.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.GetPersonByAcadamy(this.user.userName, this.user.currentAcademicYear);
    };
    approvework.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    approvework.prototype.GetPersonByAcadamy = function (user, year) {
        var _this = this;
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year;
        this.http.get(url).subscribe(function (response) { return _this.GetPersonSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    approvework.prototype.GetPersonSucess = function (response) {
        this.profile = response.json(JSON.stringify(response._body));
        this.getwork();
    };
    approvework.prototype.GetPersonError = function (error) {
        console.log("GetPersonError.");
    };
    approvework.prototype.getwork = function () {
        var _this = this;
        //this.personWorkList = this.personWork.academicKPIUserMappingList;
        this.commonService.loading();
        var url = "../head/initByUserName/" + this.email + "/" + this.profile.evaluateRound;
        this.http.get(url).subscribe(function (response) { return _this.getkpiworksucess(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approvework.prototype.getkpiworkeror = function (error) {
        console.log("error !");
    };
    approvework.prototype.getkpiworksucess = function (response) {
        var json = response.json(JSON.stringify(response._body));
        this.academicKPIUserMappingList = json.academicKPIUserMappingList;
        console.log(this.academicKPIUserMappingList);
        this.name = this.academicKPIUserMappingList[0].name;
        //console.log("getkpimapping :" +this.academicKPIUserMappingList)
        this.commonService.unLoading();
    };
    approvework.prototype.blackpage = function () {
        // this.router.navigate(['/initApprove']);
        this._location.back();
    };
    approvework.prototype.ClickGetPointKPI = function (Code, indexKPI) {
        var _this = this;
        this.indexKPI = indexKPI;
        var url = "../person/getImportWork/" + Code;
        return this.http.get(url).subscribe(function (response) { return _this.GetKPISucess(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approvework.prototype.GetKPISucess = function (response) {
        this.pointKPI = response.json(JSON.stringify(response._body));
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
    };
    approvework.prototype.approveKPIWork = function (KPIId) {
        var _this = this;
        var url = "../head/approveWork/" + KPIId;
        this.http.get(url).subscribe(function (response) { return _this.approveSucess(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approvework.prototype.unapproveKPIWork = function (KPIId) {
        var _this = this;
        var url = "../head/unApprove/" + KPIId;
        this.http.get(url).subscribe(function (response) { return _this.unApproveSucess(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approvework.prototype.unApproveSucess = function (response) {
        var temp = response.json(JSON.stringify(response._body));
        if (temp.status == '0') {
            this.academicKPIUserMappingList[this.indexKPI].status = 'CREATE';
            this.statusKpi = false;
        }
        jQuery("#myModal").modal('hide');
        console.log("unApproveSucess!");
    };
    approvework.prototype.approveSucess = function (response) {
        var temp = response.json(JSON.stringify(response._body));
        if (temp.status == '0') {
            this.academicKPIUserMappingList[this.indexKPI].status = 'APPROVED';
            this.statusKpi = true;
        }
        jQuery("#myModal").modal('hide');
        console.log("ApproveSucess!");
    };
    approvework.prototype.sentReplyPBPMessage = function () {
        var _this = this;
        if (this.replyMessage != null) {
            this.Model.replyMessage = this.replyMessage;
            var url = "../head/pbp/replyMessage"; //ติดไว้ก่อน
            this.http.post(url, this.Model).subscribe(function (response) { return _this.ReplyPBPMessageSucess(response); }, function (error) { return _this.ReplyPBPMessageError(error); }, function () { return console.log("AdminUserCreate : Success saveUser !"); });
        }
    };
    approvework.prototype.ReplyPBPMessageSucess = function (response) {
        var temp = response.json(JSON.stringify(response._body));
        this.ClickGetPointKPI(this.codeNew, this.indexKPI);
    };
    approvework.prototype.ReplyPBPMessageError = function (response) {
        console.log("Error!");
    };
    approvework = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/approvework.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, router_1.ActivatedRoute, http_1.Http, Common_service_1.CommonService, common_1.Location])
    ], approvework);
    return approvework;
}());
exports.approvework = approvework;
//# sourceMappingURL=approvework.component.js.map