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
var deanBarChart = (function () {
    function deanBarChart(http) {
        this.http = http;
    }
    deanBarChart.prototype.ngOnInit = function () {
        this.DepartmentName();
    };
    deanBarChart.prototype.DepartmentName = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    deanBarChart.prototype.GetkendoSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        console.log(this.json);
        this.academicYear = this.json.currentAcademicYear;
        this.academicYearList = this.json.academicYearList;
        this.nameDepart = this.json.facultyName;
        //this.mean1 = this.json.mean1;
        this.getbarChart(this.academicYear);
    };
    deanBarChart.prototype.GetDepartmentNameError = function (error) {
        console.log("GetDepartmentNameError.");
    };
    deanBarChart.prototype.getbarChart = function (academicYear) {
        jQuery("#chart").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../dean/getBarchart/" + academicYear,
                        dataType: "json"
                    }
                }
            },
            title: {
                text: "ระดับคะแนนในคณะ"
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
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
        jQuery("#grid").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "../dean/getBarchart/" + academicYear,
                        dataType: "Json"
                    }
                }
            },
            columns: [
                { field: "axisName", title: "ภาควิชา" },
                { field: "axisValue", title: "คะแนน" }
            ]
        });
    };
    deanBarChart = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/deanBarChart.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], deanBarChart);
    return deanBarChart;
}());
exports.deanBarChart = deanBarChart;
//# sourceMappingURL=deanBarChart.component.js.map