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
var headWorkTypeBarChart = (function () {
    function headWorkTypeBarChart(http) {
        this.http = http;
    }
    headWorkTypeBarChart.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    headWorkTypeBarChart.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetuserSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("editdone !"); });
    };
    headWorkTypeBarChart.prototype.GetuserSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.academicYearList = this.user.academicYearList;
        this.currentAcademicYear = this.user.currentAcademicYear;
        this.DepartmentName(this.currentAcademicYear);
    };
    headWorkTypeBarChart.prototype.DepartmentName = function (year) {
        var _this = this;
        var url = "../person/MinMaxBean/" + year;
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    headWorkTypeBarChart.prototype.GetkendoSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.departmentName;
        this.getChart1();
        this.getChart2();
        this.getChart3();
        this.getChart4();
        this.getChart5();
    };
    headWorkTypeBarChart.prototype.GetDepartmentNameError = function (error) {
        console.log("GetDepartmentNameError.");
    };
    headWorkTypeBarChart.prototype.getChart1 = function () {
        var year = this.currentAcademicYear;
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
                        url: "../head/getWorkTypeBarchart/1/" + year,
                        dataType: "json"
                    }
                },
            },
            title: {
                // text: "ระดับคะแนนในภาควิชา ด้านวิชาการ ค่่าเฉลี่ย  " + start + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc1 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc1 + ")"
                text: "ระดับคะแนนในภาควิชา ด้านวิชาการ ค่่าเฉลี่ย  " + start + "  "
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
                // min: 0,
                // max: 3500,
                // majorUnit: 500,
                plotBands: [
                    { from: 1226, to: end, 1228: "orange" },
                    { from: 3000, to: 3002, color: "green" },
                    { from: startMin, to: endMin, color: "red" }
                ]
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    };
    headWorkTypeBarChart.prototype.getChart2 = function () {
        var year = this.currentAcademicYear;
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
                        url: "../head/getWorkTypeBarchart/2/" + year,
                        dataType: "json"
                    }
                },
            },
            title: {
                // text: "ระดับคะแนนในภาควิชา ด้านงานพัฒนาวิชาการ  ค่่าเฉลี่ย  " + this.json.mean2 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc2 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc2 + ")"
                text: "ระดับคะแนนในภาควิชา ด้านงานพัฒนาวิชาการ  ค่่าเฉลี่ย  " + this.json.mean2 + " "
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
                // min: 0,
                // max: 1200,
                // majorUnit: 200,
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
    headWorkTypeBarChart.prototype.getChart3 = function () {
        var year = this.currentAcademicYear;
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
                        url: "../head/getWorkTypeBarchart/3/" + year,
                        dataType: "json"
                    }
                },
            },
            title: {
                text: "ระดับคะแนนในภาควิชา ด้านงานวิจัย  ค่่าเฉลี่ย  " + this.json.mean3 + " "
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
                // min: 0,
                // max: 1200,
                // majorUnit: 200,
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
    headWorkTypeBarChart.prototype.getChart4 = function () {
        var year = this.currentAcademicYear;
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
                        url: "../head/getWorkTypeBarchart/4/" + year,
                        dataType: "json"
                    }
                }
            },
            title: {
                // text: "ระดับคะแนนในภาควิชา ด้านงานบริการวิชาการ  ค่่าเฉลี่ย  " + this.json.mean4 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc4 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc4 + ")"
                text: "ระดับคะแนนในภาควิชา ด้านงานบริการวิชาการ  ค่่าเฉลี่ย  " + this.json.mean4 + "  "
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
    headWorkTypeBarChart.prototype.getChart5 = function () {
        var year = this.currentAcademicYear;
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
                        url: "../head/getWorkTypeBarchart/5/" + year,
                        dataType: "json"
                    }
                }
            },
            title: {
                text: "ระดับคะแนนในภาควิชา ด้านงานทำนุบำรุงศิลป  ค่่าเฉลี่ย  " + this.json.mean5 + "  "
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
    headWorkTypeBarChart.prototype.changeYear = function (year) {
        this.DepartmentName(year);
    };
    headWorkTypeBarChart = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/headWorkTypeBarChart.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], headWorkTypeBarChart);
    return headWorkTypeBarChart;
}());
exports.headWorkTypeBarChart = headWorkTypeBarChart;
//# sourceMappingURL=headWorkTypeBarChart.component.js.map