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
var workTypeBarChart = (function () {
    function workTypeBarChart(commonService, http) {
        this.commonService = commonService;
        this.http = http;
        this.libPath = "/PBP3/baiwa/libs/";
    }
    workTypeBarChart.prototype.ngOnInit = function () {
        this.DepartmentName();
    };
    workTypeBarChart.prototype.ngAfterViewInit = function () {
    };
    workTypeBarChart.prototype.DepartmentName = function () {
        var _this = this;
        var url = "../person/MinMaxBean";
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    workTypeBarChart.prototype.GetkendoSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.departmentName;
        this.kendoChart1();
        this.kendoChart2();
        this.kendoChart3();
        this.kendoChart4();
        this.kendoChart5();
    };
    workTypeBarChart.prototype.GetDepartmentNameError = function (error) {
        console.log("GetDepartmentNameError.");
    };
    workTypeBarChart.prototype.kendoChart1 = function () {
        var start = this.json.mean1;
        var end = start + 2;
        var startMin = this.json.minValue1;
        var endMin = startMin + 2;
        var startMax = this.json.maxValue1;
        var endMax = startMax + 2;
        jQuery("#chart1").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../person/getWorkTypeBarchart/1",
                        cache: false,
                        dataType: "json"
                    }
                },
            },
            title: {
                text: "ระดับคะแนนในภาควิชา ด้านวิชาการ ค่่าเฉลี่ย  " + start + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc1 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc1 + ")"
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
                plotBands: [
                    { from: start, to: end, color: "orange" },
                    { from: startMax, to: endMax, color: "green" },
                    { from: startMin, to: endMin, color: "red" }
                ]
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    };
    workTypeBarChart.prototype.kendoChart2 = function () {
        var start = this.json.mean2;
        var end = start + 2;
        if (start == 0.00) {
            end = 0.00;
        }
        var startMin = this.json.minValue2;
        var endMin = startMin + 2;
        if (startMin == 0.00) {
            endMin = 0.00;
        }
        var startMax = this.json.maxValue2;
        var endMax = startMax + 2;
        jQuery("#chart2").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../person/getWorkTypeBarchart/2",
                        dataType: "json"
                    }
                }
            },
            title: {
                text: "ระดับคะแนนในภาควิชา ด้านงานพัฒนาวิชาการ  ค่่าเฉลี่ย  " + this.json.mean2 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc2 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc2 + ")"
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
                plotBands: [
                    { from: start, to: end, color: "orange" },
                    { from: startMax, to: endMax, color: "green" },
                    { from: startMin, to: endMin, color: "red" }
                ]
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    };
    workTypeBarChart.prototype.kendoChart3 = function () {
        var start = this.json.mean3;
        var end = start + 2;
        if (start == 0.00) {
            end = 0.00;
        }
        var startMin = this.json.minValue3;
        var endMin = startMin + 2;
        if (startMin == 0.00) {
            endMin = 0.00;
        }
        var startMax = this.json.maxValue3;
        var endMax = startMax + 2;
        jQuery("#chart3").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../person/getWorkTypeBarchart/3",
                        dataType: "json"
                    }
                }
            },
            title: {
                text: "ระดับคะแนนในภาควิชา ด้านงานวิจัย  ค่่าเฉลี่ย  " + this.json.mean3 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc3 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc3 + ")"
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
            }, valueAxis: {
                plotBands: [
                    { from: start, to: end, color: "orange" },
                    { from: startMax, to: endMax, color: "green" },
                    { from: startMin, to: endMin, color: "red" }
                ]
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    };
    workTypeBarChart.prototype.kendoChart4 = function () {
        var start = this.json.mean4;
        var end = start + 2;
        if (start == 0.00) {
            end = 0.00;
        }
        var startMin = this.json.minValue4;
        var endMin = startMin + 2;
        if (startMin == 0.00) {
            endMin = 0.00;
        }
        var startMax = this.json.maxValue4;
        var endMax = startMax + 2;
        jQuery("#chart4").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../person/getWorkTypeBarchart/4",
                        dataType: "json"
                    }
                }
            },
            title: {
                text: "ระดับคะแนนในภาควิชา ด้านงานบริการวิชาการ  ค่่าเฉลี่ย  " + this.json.mean4 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc4 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc4 + ")"
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
            }, valueAxis: {
                plotBands: [
                    { from: start, to: end, color: "orange" },
                    { from: startMax, to: endMax, color: "green" },
                    { from: startMin, to: endMin, color: "red" }
                ]
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    };
    workTypeBarChart.prototype.kendoChart5 = function () {
        var start = this.json.mean5;
        var end = start + 2;
        if (start == 0.00) {
            end = 0.00;
        }
        var startMin = this.json.minValue5;
        var endMin = startMin + 2;
        if (startMin == 0.00) {
            endMin = 0.00;
        }
        var startMax = this.json.maxValue5;
        var endMax = startMax + 2;
        jQuery("#chart5").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../person/getWorkTypeBarchart/5",
                        dataType: "json"
                    }
                }
            },
            title: {
                text: "ระดับคะแนนในภาควิชา ด้านงานทำนุบำรุงศิลป  ค่่าเฉลี่ย  " + this.json.mean5 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc5 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc5 + ")"
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
            }, valueAxis: {
                plotBands: [
                    { from: start, to: end, color: "orange" },
                    { from: startMax, to: endMax, color: "green" },
                    { from: startMin, to: endMin, color: "red" }
                ]
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    };
    workTypeBarChart = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/workTypeBarChart.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], workTypeBarChart);
    return workTypeBarChart;
}());
exports.workTypeBarChart = workTypeBarChart;
//# sourceMappingURL=workTypeBarChart.component.js.map