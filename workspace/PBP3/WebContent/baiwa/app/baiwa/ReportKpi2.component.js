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
var ReportKpi2 = (function () {
    function ReportKpi2(commonService, http) {
        this.commonService = commonService;
        this.http = http;
    }
    ReportKpi2.prototype.ModelSearch = function () {
        return {
            "workTypeCode": "",
            "academicYear": "",
            "facultyName": "",
        };
    };
    ReportKpi2.prototype.ngOnInit = function () {
        this.searchAtti = this.ModelSearch();
        this.GetUserSession();
    };
    ReportKpi2.prototype.ngAfterViewInit = function () {
    };
    ReportKpi2.prototype.getlistKPI = function () {
        //console.log("getlistKPI : Ready getlistByDepartment ");
        var _this = this;
        var url = "../admin/pbp/academicKPI/init";
        this.http.get(url).subscribe(function (response) { return _this.GetlistKPISucess(response); }, function (error) { return _this.GetlistKPIJsonError(error); }, function () { return console.log(" Sent Success !"); });
    };
    ReportKpi2.prototype.GetlistKPISucess = function (response) {
        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        console.log("RR");
        console.log(this.model);
        this.academicYearList = this.model.academicYearList;
        this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        if (this.user.isAdmin == true) {
            this.facultyList = this.model.facultyList;
            this.searchAtti.facultyName = "วิศวกรรมศาสตร์";
        }
        else {
            this.user.facultyName;
            var temp = [{
                    "name": this.user.facultyName,
                }];
            this.facultyList = temp;
            this.searchAtti.facultyName = this.user.facultyName;
        }
        this.searchAtti.academicYear = this.model.academicYear;
        this.searchAtti.workTypeCode = this.model.workTypeCode;
        console.log("RR 2");
        console.log(this.facultyList);
        // var _this = this;
        this.createChart();
    };
    ReportKpi2.prototype.GetlistKPIJsonError = function (error) {
        console.log(" Error !");
    };
    ReportKpi2.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetuserSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    ReportKpi2.prototype.GetuserSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        console.log(this.user);
        this.currentAcademicYear = this.user.currentAcademicYear;
        this.getlistKPI();
    };
    ReportKpi2.prototype.GetPersonError = function (error) {
        console.log("GetPersonError.");
    };
    ReportKpi2.prototype.GetCreateChart = function () {
        var _this = this;
        this.commonService.loading();
        var url = "../dean/getReport2Barchart/" + this.searchAtti.academicYear + "/" + this.searchAtti.facultyName + "/" + this.searchAtti.workTypeCode + "";
        return this.http.get(url).subscribe(function (response) { return _this.GetCreateS(response); }, function (error) { return _this.GetCreateE(error); }, function () { return console.log("editdone !"); });
    };
    ReportKpi2.prototype.GetCreateS = function (response) {
        this.commonService.unLoading();
        this.listData = response.json(JSON.stringify(response._body));
        this.createChart();
    };
    ReportKpi2.prototype.GetCreateE = function (error) {
        console.log("GetCreateError.");
    };
    ReportKpi2.prototype.createChart = function () {
        // setTimeout(function(){this.commonService.unLoading();  }, 15);
        console.log("../dean/getReport2Barchart/" + this.searchAtti.academicYear + "/" + this.searchAtti.facultyName + "/" + this.searchAtti.workTypeCode + "");
        jQuery("#chart").kendoChart({
            dataSource: {
                data: this.listData
            },
            chema: {
                data: function (response) {
                    // this.commonService.unLoading();//
                    for (var i = 0; i < response.length; i++) {
                        response[i].orderNo = new Number(response[i].orderNo);
                    }
                    return response;
                }
            },
            title: {
                text: "ระดับคะแนนรวมในภาควิชา"
            },
            series: [{
                    type: "column",
                    field: "axisValue",
                    name: "ระดับคะแนนรวม"
                }],
            categoryAxis: {
                field: "axisName",
                labels: {
                    rotation: -90
                }
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    };
    ReportKpi2 = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/ReportKpi2.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], ReportKpi2);
    return ReportKpi2;
}());
exports.ReportKpi2 = ReportKpi2;
//# sourceMappingURL=ReportKpi2.component.js.map