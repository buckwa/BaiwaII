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
var FacultyReport = (function () {
    function FacultyReport(http) {
        this.http = http;
    }
    FacultyReport.prototype.ngOnInit = function () {
        this.DepartmentName();
    };
    FacultyReport.prototype.DepartmentName = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    FacultyReport.prototype.GetkendoSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.facultyName;
        //this.mean1 = this.json.mean1;
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
        this.getbarChart();
    };
    FacultyReport.prototype.GetDepartmentNameError = function (error) {
        console.log("GetDepartmentNameError.");
    };
    FacultyReport.prototype.getbarChart = function () {
        jQuery("#KendoChart").kendoChart({
            title: {
                text: "คะแนนภาพรวมระดับคณะ"
            },
            dataSource: {
                transport: {
                    read: {
                        url: "../dean/facultyReport",
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
        jQuery("#grid").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "../dean/facultyReport",
                        dataType: "Json"
                    }
                }
            },
            columns: [
                { field: "axisName", title: "ภาควิชา" },
                { field: "axisValue", title: "คะแนน เฉลี่ย" }
            ]
        });
    };
    FacultyReport = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/FacultyReport.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], FacultyReport);
    return FacultyReport;
}());
exports.FacultyReport = FacultyReport;
//# sourceMappingURL=FacultyReport.component.js.map