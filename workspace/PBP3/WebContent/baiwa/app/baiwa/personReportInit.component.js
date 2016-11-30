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
var platform_browser_1 = require('@angular/platform-browser');
var personReportInit = (function () {
    function personReportInit(commonService, http, sanitizer) {
        this.commonService = commonService;
        this.http = http;
        this.sanitizer = sanitizer;
        this.libPath = "/PBP3/baiwa/libs/";
    }
    personReportInit.prototype.ngOnInit = function () {
        this.GetUserSession();
        // this.GetUserSession();
    };
    personReportInit.prototype.ngAfterViewInit = function () {
    };
    personReportInit.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserSessionSucess(response); }, function (error) { return _this.GetUserSessionError(error); }, function () { return console.log("editdoneUser !"); });
    };
    personReportInit.prototype.GetUserSessionSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.kendoGrid(this.user.currentAcademicYear);
    };
    personReportInit.prototype.GetUserSessionError = function (error) {
        console.log("GetPersonError.");
    };
    personReportInit.prototype.kendoGrid = function (year) {
        var _this = this;
        var url = "../person/getRadarPlotNewByYear/" + year;
        this.url = url;
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoGridSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("getRadarPlotNewByYear1 !"); });
    };
    personReportInit.prototype.GetkendoGridSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        var maxVal;
        for (var i = 0; i < this.json.length; i++) {
            if (this.json[i].axisValue > this.json[i].axisValue2 && this.json[i].axisValue > maxVal) {
                maxVal = this.json[i].axisValue;
            }
            else if (this.json[i].axisValue < this.json[i].axisValue2 && this.json[i].axisValue2 > maxVal) {
                maxVal = this.json[i].axisValue2;
            }
        }
        this.maxVal = maxVal;
        this.creatChart();
    };
    personReportInit.prototype.GetPersonError = function (error) {
        console.log("GetPersonError.");
    };
    personReportInit.prototype.creatChart = function () {
        jQuery("#chartKendo").kendoChart({
            title: {
                text: "คะแนนประจำปี  "
            },
            dataSource: {
                transport: {
                    read: {
                        url: this.url,
                        dataType: "json"
                    }
                }
            },
            seriesDefaults: {
                type: "radarLine"
            },
            series: [{
                    name: "คะแนน",
                    field: "axisValue"
                }],
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
        console.log("getRadarPlotNewByYear2 !");
    };
    personReportInit = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/personReportInit.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http, platform_browser_1.DomSanitizer])
    ], personReportInit);
    return personReportInit;
}());
exports.personReportInit = personReportInit;
//# sourceMappingURL=personReportInit.component.js.map