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
var http_1 = require('@angular/http');
var workTypeCompareBarChart = (function () {
    function workTypeCompareBarChart(http) {
        this.http = http;
        this.WorkTypeCompareReport = this.WorkTypeCompareReportDefult();
        this.WorkTypeCompareReport.type1 = true;
    }
    workTypeCompareBarChart.prototype.ngOnInit = function () {
        this.getDepartment();
    };
    workTypeCompareBarChart.prototype.WorkTypeCompareReportDefult = function () {
        return {
            "type1": "",
            "type2": "",
            "type3": "",
            "type4": "",
            "type5": "",
        };
    };
    workTypeCompareBarChart.prototype.getCompare = function () {
        var _this = this;
        var url = "../dean/workTypeCompareBarChart";
        return this.http.post(url, this.WorkTypeCompareReport).subscribe(function (response) { return _this.GetComparesucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    workTypeCompareBarChart.prototype.GetComparesucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        this.getChart();
        //this.nameDepart = this.json.resObj.faculty;
        //this.mean1 = this.json.mean1;
        //this.createChart(this.nameDepart[0].email);
    };
    workTypeCompareBarChart.prototype.changeCheckBox = function () {
        var _this = this;
        console.log("changeCheckBok");
        window.setTimeout(function () {
            _this.getCompare();
        }, 600);
    };
    workTypeCompareBarChart.prototype.GetDepartmentNameError = function (error) {
        console.log("GetDepartmentNameError.");
    };
    workTypeCompareBarChart.prototype.getChart = function () {
        jQuery("#columnChart").kendoChart({
            title: {
                text: "ระดับคะแนนในภาควิชา"
            },
            dataSource: {
                data: this.json,
                group: {
                    field: "groupName"
                },
                sort: {
                    field: "orderNo",
                    dir: "asc"
                },
                schema: {
                    model: {
                        fields: {
                            categoryName: { type: "string" },
                            groupName: { type: "string" },
                            score: { type: "string" },
                            orderNo: { type: "number" }
                        }
                    }
                }
            },
            series: [{
                    type: "column",
                    field: "axisValue",
                    name: "#= group.value #"
                }],
            legend: {
                position: "top"
            },
            valueAxis: {
                min: 0,
                max: 80000,
                majorUnit: 10000,
            },
            categoryAxis: {
                field: "categoryName",
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
    workTypeCompareBarChart.prototype.getDepartment = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    workTypeCompareBarChart.prototype.GetkendoSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.facultyName;
        this.getCompare();
        //this.mean1 = this.json.mean1;
    };
    workTypeCompareBarChart = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/workTypeCompareBarChart.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], workTypeCompareBarChart);
    return workTypeCompareBarChart;
}());
exports.workTypeCompareBarChart = workTypeCompareBarChart;
//# sourceMappingURL=workTypeCompareBarChart.component.js.map