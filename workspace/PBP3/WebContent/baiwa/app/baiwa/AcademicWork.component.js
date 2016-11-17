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
var platform_browser_1 = require('@angular/platform-browser');
var Rx_1 = require('rxjs/Rx');
var URL1 = 'http://localhost:8080/PBP3/person/importwork_file';
var AcademicWork = (function () {
    function AcademicWork(commonService, http, sanitizer) {
        this.commonService = commonService;
        this.http = http;
        this.sanitizer = sanitizer;
        this.kpiuserList = [];
        this.uploader = new ng2_file_upload_1.FileUploader({ url: URL1 });
        this.academy = this.setdefualtkpi();
        this.kpiuserList = [];
        this.kpival = [];
        this.pointKPI = this.setdefualtpoitkpi();
    }
    AcademicWork.prototype.setdefualtkpi = function () {
        return {
            "academicYear": "",
            "totalInMapping": "",
            "calResultStr": "",
            "pBPWorkTypeList": [{
                    "name": "",
                    "totalInWorkType": "",
                    "academicKPIUserMappingList": [{}]
                }]
        };
    };
    AcademicWork.prototype.setdefualtpoitkpi = function () {
        return {
            "name": "",
            "kpiUserMappingId": "",
            "calResultStr": "",
            "academicKPIAttributeValueList": [{}]
        };
    };
    AcademicWork.prototype.ngOnInit = function () {
        var _this = this;
        this.GetUserSession();
        this.uploader.onBuildItemForm = function (fileItem, form) {
            form.append('academicKPIId', _this.codeKpi);
        };
    };
    AcademicWork.prototype.ngAfterViewInit = function () {
    };
    AcademicWork.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    AcademicWork.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.GetAcademicWork(this.user.userName, this.user.currentAcademicYear, "1");
    };
    AcademicWork.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    AcademicWork.prototype.GetAcademicWork = function (user, year, round) {
        var _this = this;
        this.commonService.loading();
        var url = "../person/getAcademicWork/" + user + "/" + year + "/" + round;
        return this.http.get(url).subscribe(function (response) { return _this.GetUserAcademicSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    AcademicWork.prototype.GetUserAcademicSucess = function (response) {
        this.academy = response.json(JSON.stringify(response._body));
        this.academyList = this.academy.pBPWorkTypeList;
        //this.kpiuserList =this.academy.pBPWorkTypeList.academicKPIUserMappingList;
        for (var i = 0; i < this.academy.pBPWorkTypeList.length; i++) {
            this.kpiuserList.push(this.academy.pBPWorkTypeList[i].academicKPIUserMappingList);
        }
        this.commonService.unLoading();
        this.mapKpi();
    };
    AcademicWork.prototype.ClickGetPointKPI = function (Code, mark) {
        var _this = this;
        this.uploader.clearQueue();
        this.mark = mark;
        this.codeKpi = Code;
        var url = "../person/getImportWork/" + Code;
        return this.http.get(url).subscribe(function (response) { return _this.GetKPISucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    AcademicWork.prototype.GetKPISucess = function (response) {
        this.pointKPI = response.json(JSON.stringify(response._body));
        this.pointLPIList = this.pointKPI.academicKPIAttributeValueList;
        this.fileWork = this.pointKPI.academicKPIAttachFileList;
        if (this.fileWork.length == 0) {
            this.chFilework = true;
        }
        else {
            this.chFilework = false;
        }
        // FileUploader.prototype.addToQueue(this.f,null,null);
        if (this.pointKPI.status == "APPROVED") {
            this.statusKpi = true;
        }
        else {
            this.statusKpi = false;
        }
    };
    AcademicWork.prototype.mapKpi = function () {
        for (var i = 0; i < this.kpiuserList.length; i++) {
            this.kpival[i] = [];
            for (var j = 0; j < this.kpiuserList[i].length; j++) {
                if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 2) {
                    var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[1].value;
                    this.kpival[i][j] = temp + "%";
                }
                else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 3) {
                    this.kpival[i][j] = "";
                }
                else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 4) {
                    var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[2].value;
                    this.kpival[i][j] = temp + "%";
                }
                else {
                    this.kpival[i][j] = "";
                }
            }
        }
    };
    AcademicWork.prototype.getImage = function (url) {
        return Rx_1.Observable.create(function (observer) {
            var req = new XMLHttpRequest();
            req.open('get', url);
            req.responseType = "arraybuffer";
            req.onreadystatechange = function () {
                if (req.readyState == 4 && req.status == 200) {
                    observer.next(req.response);
                    observer.complete();
                }
            };
            req.send();
        });
    };
    AcademicWork.prototype.getFile = function (KpiID) {
        var _this = this;
        // var data = {'profileImg' : profileImg}
        var url = "../person/getAcademicWork_File/" + KpiID;
        this.getImage(url).subscribe(function (imageData) {
            _this.f = imageData;
            console.log("imageReturn :" + imageData.length);
            //var blob: Blob = new Blob(imageData, JSON.stringify('_body'));
            _this.tmpUrl = URL.createObjectURL(new Blob([imageData]));
            _this.fielPath = _this.sanitize(_this.tmpUrl);
            //this.f = new File(new Blob([imageData]),"name.txt",{type: "image/png"});
            //FileUploader.addToQueue();
            console.log("file");
        });
        // the below will throw not implemented error
        this.http.get(url).subscribe(function (image) {
            console.log("imageUrl :" + image.url);
            console.log(image.arrayBuffer());
        });
    };
    AcademicWork.prototype.sanitize = function (url) {
        return this.sanitizer.bypassSecurityTrustUrl(url);
    };
    AcademicWork.prototype.uploadFileAll = function () {
        var _this = this;
        this.uploader.uploadAll();
        window.setTimeout(function () {
            var temp = !_this.uploader.getNotUploadedItems().length;
            _this.ClickGetPointKPI(_this.codeKpi, _this.mark);
            console.log("status upload :" + temp);
            _this.uploader.clearQueue();
        }, 600);
    };
    AcademicWork.prototype.deleteFile = function (attachFileId, fileName) {
        var url = "../person/deleteAttachFile/" + this.codeKpi + "/" + fileName + "/" + attachFileId;
        this.commonService.confirm("คุณต้องการลบเอกสารแบบใช่หรื่อไม่?", jQuery.proxy(function (isOk) {
            var _this = this;
            console.log("isOk", isOk);
            if (isOk) {
                this.http.get(url).subscribe(function (response) { return _this.deletesucess(response); }, function (error) { return _this.deleteError(); }, function () { return console.log("editdoneUser !"); });
            }
        }, this));
    };
    AcademicWork.prototype.deletesucess = function (response) {
        console.log("deletesucess!");
        this.ClickGetPointKPI(this.codeKpi, this.mark);
    };
    AcademicWork.prototype.deleteError = function () {
        console.log("deleteError!");
    };
    AcademicWork = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AcademicWork.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http, platform_browser_1.DomSanitizer])
    ], AcademicWork);
    return AcademicWork;
}());
exports.AcademicWork = AcademicWork;
//# sourceMappingURL=AcademicWork.component.js.map