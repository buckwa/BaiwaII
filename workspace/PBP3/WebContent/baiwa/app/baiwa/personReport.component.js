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
var personReport = (function () {
    function personReport(commonService, http) {
        this.commonService = commonService;
        this.http = http;
        this.libPath = "/PBP3/baiwa/libs/";
    }
    personReport.prototype.ngOnInit = function () {
        this.DepartmentName();
    };
    personReport.prototype.ngAfterViewInit = function () {
    };
    personReport.prototype.DepartmentName = function () {
        var _this = this;
        var url = "../person/DepartmentName";
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    personReport.prototype.GetkendoSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.departmentName;
        this.mean1 = this.json.mean1;
        this.kendoChart();
    };
    personReport.prototype.GetDepartmentNameError = function (error) {
        console.log("GetDepartmentNameError.");
    };
    personReport.prototype.NameDepartment = function () {
        var _this = this;
        var url = "../person/getRadarPlotNewByYear/2558";
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoGridSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("getRadarPlotNewByYear1 !"); });
    };
    personReport.prototype.GetkendoGridSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
    };
    personReport.prototype.GetPersonError = function (error) {
        console.log("GetPersonError.");
    };
    personReport.prototype.kendoChart = function () {
        jQuery("#KendoChart").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../person/getBarchart",
                        dataType: "json"
                    }
                }
            },
            title: {
                text: "ระดับคะแนน ค่าเฉลี่ย " + this.mean1
            },
            series: [{
                    type: "column",
                    field: "axisValue",
                    name: "ระดับคะแนน"
                }],
            categoryAxis: {
                field: "axisName",
                labels: {
                    rotation: -90
                }
            },
            valueAxis: {
                min: 0,
                max: 7000,
                majorUnit: 1000
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    };
    personReport = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/personReport.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], personReport);
    return personReport;
}());
exports.personReport = personReport;
//# sourceMappingURL=personReport.component.js.map