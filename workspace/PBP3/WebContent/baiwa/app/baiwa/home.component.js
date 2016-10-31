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
var URL = 'http://localhost:8080/PBP3/person/uploadMultiFile';
var home = (function () {
    function home(commonService, http) {
        this.commonService = commonService;
        this.http = http;
        this.uploader = new ng2_file_upload_1.FileUploader({ url: URL });
        this.libPath = "/PBP3/baiwa/libs/";
        this.profile = this.defaultProfile();
        this.work = this.defaultWork();
    }
    home.prototype.ngOnInit = function () {
        this.GetUserSession();
        this.uploader.queue;
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
            "email": ""
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
    home.prototype.sumaryAsix = function () {
        this.sumasix = 0;
        this.sumasix2 = 0;
        for (var i = 0; i < this.work.length; i++) {
            this.sumasix = parseFloat(this.sumasix) + parseFloat(this.work[i].axisValue);
            this.sumasix2 = parseFloat(this.sumasix2) + parseFloat(this.work[i].axisValue2);
        }
    };
    home.prototype.GetPersonByAcadamy = function (user) {
        var _this = this;
        var url = "../person/getPersonByAcademicYear/" + user + "/2558";
        this.http.get(url).subscribe(function (response) { return _this.GetPersonSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    home.prototype.GetPersonSucess = function (response) {
        this.profile = response.json(JSON.stringify(response._body));
    };
    home.prototype.GetPersonError = function (error) {
        console.log("GetPersonError.");
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
        this.user = response.json(JSON.stringify(response._body));
        this.GetPersonByAcadamy(this.user.userName);
        this.GetRadarPlotNew(this.user.userName, this.user.currentAcademicYear, "1");
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
                max: 705.0
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
    home = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/home.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], home);
    return home;
}());
exports.home = home;
//# sourceMappingURL=home.component.js.map