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
var ng2_file_upload_1 = require('ng2-file-upload');
var URL = '../person/importwork_file';
var listworktype = (function () {
    function listworktype(commonService, http) {
        this.commonService = commonService;
        this.http = http;
        this.uploader = new ng2_file_upload_1.FileUploader({ url: URL });
        this.hasBaseDropZoneOver = false;
        this.hasAnotherDropZoneOver = false;
        this.savealert = false;
        this.uploadalert = false;
        this.statusActiveUpload = false;
        this.valid = true;
        this.libPath = "/PBP3/baiwa/libs/";
        this.Inport = this.defaultInport();
        this.inport5 = this.defaultImport();
        this.FormAddInput = this.defaultFormAddInput();
    }
    listworktype.prototype.ngOnInit = function () {
        var _this = this;
        this.GetUserSession();
        this.uploader.onBuildItemForm = function (fileItem, form) {
            form.append('academicKPIId', _this.academicKPIId);
        };
        //this.uploadalert =true;
    };
    listworktype.prototype.ngAfterViewInit = function () {
    };
    listworktype.prototype.defaultInport = function () {
        return {
            "status": "",
            "rownum": "",
            "createDate": "",
            "updateDate": "",
            "createBy": "",
            "updateBy": "",
            "name": "",
            "code": "",
            "description": "",
            "academicKPIId": "",
            "academicRuleId": "",
            "academicYear": "",
            "workTypeCode": "",
            "mark": "",
            "unitCode": "",
            "unitDesc": "",
            "facultyCode": "",
            "specialP1": "",
            "specialP2": "",
            "specialP3": "",
            "specialP4": "",
            "specialP5": "",
            "totalStudentFrom": "",
            "totalStudentTo": "",
            "multiplyValue": "",
            "academicUnitList": "",
            "academicKPIAttributeList": "",
            "fileData": "",
            "orderNo": "",
            "fromRegis": "",
            "errorDesc": "",
            "academicKPIUserMappingId": "",
            "replyMessage": "",
            "ratio": "",
            "remark": "",
            "tmpFileNameList": "",
            "index": "",
            "createDateTimeStr": "",
            "updateDateTimeStr": "",
            "createDateStr": "",
            "updateDateStr": "",
        };
    };
    listworktype.prototype.defaultImport = function () {
        return {
            "name": "",
            "code": "",
            "mark": "",
            "unitDesc": "",
        };
    };
    listworktype.prototype.defaultFormAddInput = function () {
        return [{
                "name": "",
                "code": "",
                "description": "",
                "academicKPIAtributeId": "",
                "academicKPIId": "",
                "academicKPICode": "",
                "value": "",
                "isCalculate": "",
                "academicYear": "",
                "ratio": "",
                "isValidateNumber": ""
            }];
    };
    listworktype.prototype.jsondefualt = function () {
        return {
            "workTypeName": "",
            "academicKPIList": "",
        };
    };
    listworktype.prototype.GetDataInport = function (facultyCode, currentAcademicYear) {
        var _this = this;
        var url = "../person/getAllWorkList/" + currentAcademicYear + "/" + facultyCode;
        this.http.get(url).subscribe(function (response) { return _this.GetSucess(response); }, function (error) { return _this.GetError(error); }, function () { return console.log("editdoneInportt !"); });
    };
    listworktype.prototype.uploadFileAll = function () {
        var _this = this;
        this.uploader.uploadAll();
        this.uploader.onCompleteItem = function (item, response, status, headers) {
            console.log("ImageUpload:uploaded:", item, status);
            _this.ClickGetPointKPI(_this.academicKPIId, _this.mark);
            _this.uploadalert = true;
        };
    };
    listworktype.prototype.GetSucess = function (response) {
        this.Inport = response.json(JSON.stringify(response._body));
        this.inport0 = this.Inport[0].academicKPIList;
        this.inport1 = this.Inport[1].academicKPIList;
        this.inport2 = this.Inport[2].academicKPIList;
        this.inport3 = this.Inport[3].academicKPIList;
        this.inport4 = this.Inport[4].academicKPIList;
    };
    listworktype.prototype.GetError = function (error) {
        console.log("GetPersonError.");
    };
    listworktype.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    listworktype.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.currentAcademicYear = this.user.currentAcademicYear;
        this.GetPersonByAcadamy(this.user.userName, this.currentAcademicYear);
    };
    listworktype.prototype.GetPersonByAcadamy = function (user, year) {
        var _this = this;
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year;
        this.http.get(url).subscribe(function (response) { return _this.GetPersonSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdone !"); });
    };
    listworktype.prototype.GetPersonSucess = function (response) {
        this.profile = response.json(JSON.stringify(response._body));
        this.evaluateRoundValue = this.profile.employeeTypeNo;
        this.GetDataInport(this.user.facultyCode, this.user.currentAcademicYear);
    };
    listworktype.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    listworktype.prototype.clickedAddImport = function (academicKPICode) {
        var _this = this;
        var url = "../person/getAcademicKPI/" + academicKPICode + "/" + this.user.facultyCode + "/" + this.user.currentAcademicYear;
        this.http.get(url).subscribe(function (response) { return _this.GetImportSucess(response); }, function (error) { return _this.GetError(error); }, function () { return console.log("editdoneImportt2 !"); });
    };
    listworktype.prototype.GetImportSucess = function (response) {
        this.inport5 = response.json(JSON.stringify(response._body));
        this.FormAddInput = this.inport5.academicKPIAttributeList;
    };
    listworktype.prototype.clickedSaveImport = function () {
        var _this = this;
        var keys = Object.keys(this.FormAddInput);
        var len = keys.length;
        var tamp = 1;
        for (var i = 0; i < len; i++) {
            if (this.FormAddInput[i].value == null) {
                console.log("Required Now !");
                tamp = 0;
            }
            if (this.FormAddInput[i].name == 'สัดส่วน(%)') {
                if (this.FormAddInput[i].value > 100) {
                    console.log("Number limit !");
                    tamp = 0;
                }
            }
        }
        if (tamp == 1) {
            var url = "../person/importwork";
            this.http.post(url, this.inport5).subscribe(function (response) { return _this.savesucess(response); }, function (error) { return _this.GetError(error); }, function () { return console.log("save sucess na !"); });
        }
    };
    listworktype.prototype.savesucess = function (response) {
        this.academicKPIId = response.json(JSON.stringify(response._body));
        console.log(this.academicKPIId);
        //  if(this.academicKPIId.resObj=="0"){
        //      alert("ไม่สามารถ บันทึกรายการได้ กรุณาติดต่อผู้ดูแลระบบ");
        //  }else{}
        this.academicKPIId = this.academicKPIId.resObj;
        this.statusActiveUpload = true;
        this.savealert = true;
        this.valid = false;
        this.ClickGetPointKPI(this.academicKPIId, this.mark);
    };
    listworktype.prototype.exitModal = function () {
        this.statusActiveUpload = false;
        this.uploader.clearQueue();
        this.uploadalert = false;
        this.savealert = false;
        this.valid = true;
    };
    listworktype.prototype.ClickGetPointKPI = function (Code, mark) {
        var _this = this;
        this.academicKPIId = Code;
        console.log("../person/getImportWorkNew/" + Code);
        var url = "../person/getImportWorkNew/" + Code;
        return this.http.get(url).subscribe(function (response) { return _this.GetKPISucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    listworktype.prototype.GetKPISucess = function (response) {
        var Model = response.json(JSON.stringify(response._body));
        console.log(Model);
        this.fileWork = Model.academicKPIUserMapping.academicKPIAttachFileList;
    };
    listworktype.prototype.deleteFile = function (attachFileId, fileName) {
        console.log(this.academicKPIId);
        var url = "../person/deleteAttachFile/" + this.academicKPIId + "/" + fileName + "/" + attachFileId;
        this.commonService.confirm("คุณต้องการลบเอกสารแบบใช่หรื่อไม่?", jQuery.proxy(function (isOk) {
            var _this = this;
            console.log("isOk", isOk);
            if (isOk) {
                this.http.get(url).subscribe(function (response) { return _this.deletesucess(response); }, function (error) { return _this.deleteError(); }, function () { return console.log("editdoneUser !"); });
            }
        }, this));
    };
    listworktype.prototype.deletesucess = function (response) {
        console.log("deletesucess!");
        this.ClickGetPointKPI(this.academicKPIId, this.mark);
    };
    listworktype = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/listworktype.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], listworktype);
    return listworktype;
}());
exports.listworktype = listworktype;
//# sourceMappingURL=listworktype.component.js.map