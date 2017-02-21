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
var approvework = (function () {
    function approvework(route, http, commonService) {
        this.route = route;
        this.http = http;
        this.commonService = commonService;
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
        this.getwork();
    };
    approvework.prototype.getwork = function () {
        var _this = this;
        //this.personWorkList = this.personWork.academicKPIUserMappingList;
        this.commonService.loading();
        var url = "../head/initByUserName/" + this.email + "/1";
        this.http.get(url).subscribe(function (response) { return _this.getkpiworksucess(response); }, function (error) { return _this.getkpiworkeror(error); }, function () { return console.log("editdoneUser !"); });
    };
    approvework.prototype.getkpiworkeror = function (error) {
        console.log("error !");
    };
    approvework.prototype.getkpiworksucess = function (response) {
        var json = response.json(JSON.stringify(response._body));
        this.academicKPIUserMappingList = json.department.academicPersonList[0].academicKPIUserMappingList;
        this.person = json.department.academicPersonList[0].thaiName + "  " + json.department.academicPersonList[0].thaiSurname;
        //console.log("getkpimapping :" +this.academicKPIUserMappingList)
        this.commonService.unLoading();
    };
    approvework.prototype.blackpage = function () {
        window.location.href = "#/initApprove";
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
    approvework = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/approvework.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.ActivatedRoute, http_1.Http, Common_service_1.CommonService])
    ], approvework);
    return approvework;
}());
exports.approvework = approvework;
//# sourceMappingURL=approvework.component.1.js.map