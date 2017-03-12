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
var router_1 = require('@angular/router');
var URL1 = '../pam/person/uploadPersonProfilePicture2';
var home = (function () {
    function home(router, commonService, http, sanitizer) {
        this.router = router;
        this.commonService = commonService;
        this.http = http;
        this.sanitizer = sanitizer;
        this.uploader = new ng2_file_upload_1.FileUploader({ url: URL1 });
        this.libPath = "/PBP3/baiwa/libs/";
        this.profile = this.defaultProfile();
        this.work = this.defaultWork();
        this.imgUpload = true;
        this.messageList = this.defaultMessage();
    }
    home.prototype.ngOnInit = function () {
        var _this = this;
        this.GetUserSession();
        this.uploader.queue;
        this.uploader.onBuildItemForm = function (fileItem, form) {
            console.log("PersonId :" + _this.personId);
            form.append('PersonId', _this.personId);
        };
    };
    home.prototype.ngAfterViewInit = function () {
    };
    home.prototype.defaultProfile = function () {
        return {
            "thaiName": "",
            "thaiSurname": "",
            "facultyDesc": "",
            "departmentDesc": "",
            "employeeType": "",
            "rateNo": "",
            "academicRank": "",
            "maxEducation": "",
            "email": "",
            "personId": "",
        };
    };
    home.prototype.defaultWork = function () {
        return [{
                "orderNo": "",
                "axisName": "",
                "axisValue": "",
                "axisName2": "",
                "axisValue2": "",
                "mean": ""
            }];
    };
    home.prototype.defaultMessage = function () {
        return [{
                "createBy": "",
                "messageDetail": "",
                "messageId": "",
                "topicId": ""
            }];
    };
    home.prototype.sumaryAsix = function () {
        this.sumasix = 0;
        this.sumasix2 = 0;
        var maxVal;
        for (var i = 0; i < this.work.length; i++) {
            this.sumasix = parseFloat(this.sumasix) + parseFloat(this.work[i].axisValue);
            this.sumasix2 = parseFloat(this.sumasix2) + parseFloat(this.work[i].axisValue2);
            if (this.work[i].axisValue > this.work[i].axisValue2 && this.work[i].axisValue > maxVal) {
                maxVal = this.work[i].axisValue;
            }
            else if (this.work[i].axisValue < this.work[i].axisValue2 && this.work[i].axisValue2 > maxVal) {
                maxVal = this.work[i].axisValue2;
            }
        }
        this.maxVal = maxVal;
    };
    home.prototype.GetPersonByAcadamy = function (user, year) {
        var _this = this;
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year;
        this.http.get(url).subscribe(function (response) { return _this.GetPersonSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    home.prototype.GetPersonSucess = function (response) {
        this.profile = response.json(JSON.stringify(response._body));
        //this.imageProfilePath = this.sanitize(this.profile.picture)
        this.imgUpload = this.profile.picture;
        this.personId = this.profile.personId;
        this.getImageLocal(this.profile.personId);
        this.getMessage();
        this.countMessage();
        this.evaluateRoundValue = this.profile.evaluateRound;
        if (this.profile.employeeType == 'ข้าราชการ') {
            this.evaluateRoundList = this.profile.evaluateRoundList;
        }
    };
    home.prototype.GetPersonError = function (error) {
        console.log("GetPersonError.");
    };
    home.prototype.GetRadarPlotNewSearch = function (year) {
        this.GetRadarPlotNew(this.user.userName, year, this.evaluateRoundValue);
        this.currentAcademicYear = year;
    };
    home.prototype.changeRound = function () {
        this.GetRadarPlotNew(this.user.userName, this.currentAcademicYear, this.evaluateRoundValue);
    };
    home.prototype.GetRadarPlot = function () {
        this.GetRadarPlotNew(this.user.userName, this.currentAcademicYear, this.evaluateRoundValue);
    };
    home.prototype.GetRadarPlotNew = function (user, year, num) {
        var _this = this;
        var url = "../person/getRadarPlotNew/" + user + "/" + year + "/" + num;
        this.url = url;
        this.http.get(url).subscribe(function (response) { return _this.GetRadarPlotSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    home.prototype.GetRadarPlotSucess = function (response) {
        this.work = response.json(JSON.stringify(response._body));
        this.sumaryAsix();
        this.createChart();
    };
    home.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetuserSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    home.prototype.GetuserSucess = function (response) {
        var _this = this;
        this.user = response.json(JSON.stringify(response._body));
        this.currentAcademicYear = this.user.currentAcademicYear;
        this.GetPersonByAcadamy(this.user.userName, this.currentAcademicYear);
        if (this.user.isAdmin == true) {
            this.router.navigate(['/AdminAcademicKPI', "0", "0", "0"]);
        }
        setTimeout(function () { return _this.GetRadarPlotNew(_this.user.userName, _this.currentAcademicYear, _this.evaluateRoundValue); }, 250);
    };
    home.prototype.createChart = function () {
        jQuery("#KendoChart").kendoChart({
            title: {
                text: "คะแนนประจำปี"
            },
            dataSource: {
                transport: {
                    read: {
                        url: this.url,
                        cache: false,
                        dataType: "json"
                    }
                }
            },
            seriesDefaults: {
                type: "radarLine"
            },
            series: [{
                    name: "คะแนนรวมรออนุมัติ",
                    field: "axisValue2",
                    color: '#FF8000'
                },
                {
                    name: "คะแนนรวมอนุมัติ",
                    field: "axisValue",
                    color: '#138021'
                }
            ],
            categoryAxis: {
                field: "axisName"
            },
            valueAxis: {
                labels: {
                    format: "{0}",
                    visible: true,
                },
                min: 0,
                max: this.maxVal
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
        jQuery("#grid").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "/PBP/json/person/getRadarPlot",
                        dataType: "Json"
                    }
                }
            },
            columns: [
                { field: "axisName", title: "ประเภทภาระงาน " },
                { field: "axisValue", title: "คะแนน" }
            ]
        });
    };
    home.prototype.changeUpload = function () {
        console.log("imageChange");
        this.imgUpload = false;
        this.updateImg = false;
    };
    home.prototype.UploadPicture = function (item) {
        //2436579
        var size = item.file.size;
        if (size < 1000000) {
            item.upload();
            if (!item.isSuccess) {
                jQuery("#myModal").modal('hide');
                //this.getImageLocal(this.personId);
                console.log("uploadsucess");
                window.location.reload();
            }
            else {
                console.log("uploadFali");
            }
        }
        else {
            alert("ไม่สามารถ อัพโหลดรูปภาพได้ เนื่องจากมีขนาดไฟล์ มากกว่า 1 MB");
        }
        //this.uploader.clearQueue()
        console.log("uploadsucess");
    };
    home.prototype.cancleUpload = function (item) {
        item.remove();
        //this.uploader.clearQueue()
        console.log("cancleUpload");
        this.imgUpload = true;
        this.updateImg = true;
    };
    home.prototype.UpdateImage = function () {
        this.updateImg = true;
        this.imgUpload = true;
        this.uploader.clearQueue();
    };
    home.prototype.sanitize = function (url) {
        return this.sanitizer.bypassSecurityTrustUrl(url);
    };
    home.prototype.getImage = function (url) {
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
    home.prototype.getImageLocal = function (personID) {
        var _this = this;
        // var data = {'profileImg' : profileImg}
        var url = "../person/getImageFile/" + personID;
        this.getImage(url).subscribe(function (imageData) {
            console.log("imageReturn :" + imageData.image);
            _this.tmpUrl = URL.createObjectURL(new Blob([imageData]));
            _this.imageProfilePath = _this.sanitize(_this.tmpUrl);
        });
        // the below will throw not implemented error
        this.http.get(url).subscribe(function (image) {
            console.log("imageUrl :" + image.url);
            console.log(image.arrayBuffer());
        });
    };
    home.prototype.recalculate = function () {
        var _this = this;
        this.commonService.loading();
        var url = "../person/recalculate";
        return this.http.get(url).subscribe(function (response) { return _this.recalsucess(response); }, function (error) { return _this.recalError(error); }, function () { return console.log("editdone !"); });
    };
    home.prototype.recalsucess = function (response) {
        this.commonService.unLoading();
        console.log("recal sucess");
        window.location.reload();
    };
    home.prototype.recalError = function (error) {
        this.commonService.unLoading();
        console.log("recal error");
    };
    home.prototype.getMessage = function () {
        var _this = this;
        var url = "../person/getUserMassage";
        this.http.post(url, this.profile).subscribe(function (response) { return _this.GetMessageSucess(response); }, function (error) { return _this.ErrorMessage(error); }, function () { return console.log("editdone !"); });
    };
    home.prototype.GetMessageSucess = function (response) {
        this.messageList = response.json(JSON.stringify(response._body));
    };
    home.prototype.countMessage = function () {
        var _this = this;
        var url = "../person/countMessage";
        this.http.get(url).subscribe(function (response) { return _this.countMessageSucess(response); }, function (error) { return _this.ErrorMessage(error); }, function () { return console.log("editdone !"); });
    };
    home.prototype.countMessageSucess = function (response) {
        this.totalMessage = response.json(JSON.stringify(response._body));
    };
    home.prototype.ErrorMessage = function (response) {
        console.log("Error :" + response);
    };
    home = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/home.component.html',
        }), 
        __metadata('design:paramtypes', [router_1.Router, Common_service_1.CommonService, http_1.Http, platform_browser_1.DomSanitizer])
    ], home);
    return home;
}());
exports.home = home;
//# sourceMappingURL=home.component.js.map