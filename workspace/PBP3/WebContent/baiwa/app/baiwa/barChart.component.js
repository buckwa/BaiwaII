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
var barChart = (function () {
    function barChart(http) {
        this.http = http;
    }
    barChart.prototype.ngOnInit = function () {
        this.DepartmentName();
    };
    barChart.prototype.DepartmentName = function () {
        var _this = this;
        var url = "../person/DepartmentName";
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    barChart.prototype.GetkendoSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.departmentName;
        this.mean1 = this.json.mean1;
        this.getbarChart();
    };
    barChart.prototype.GetDepartmentNameError = function (error) {
        console.log("GetDepartmentNameError.");
    };
    barChart.prototype.getbarChart = function () {
        jQuery("#KendoChart").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../head/getBarchart",
                        dataType: "json"
                    }
                }
            },
            chema: {
                data: function (response) {
                    for (var i = 0; i < response.length; i++) {
                        response[i].orderNo = new Number(response[i].orderNo);
                    }
                    return response;
                }
            },
            title: {
                text: "ระดับคะแนนในภาควิชา"
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
                max: 6500,
                majorUnit: 1000
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
                        url: "../head/getBarchart",
                        dataType: "Json"
                    }
                }
            },
            chema: {
                data: function (response) {
                    for (var i = 0; i < response.length; i++) {
                        response[i].orderNo = new Number(response[i].orderNo);
                    }
                    return response;
                }
            },
            columns: [
                { field: "axisName", title: "บุคลากร" },
                { field: "axisValue", title: "คะแนน",
                    headerAttributes: { style: "text-align:right" },
                    attributes: { class: "text-right" }
                }
            ]
        });
    };
    barChart.prototype.sortMaxVal = function () {
    };
    barChart = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/barChart.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], barChart);
    return barChart;
}());
exports.barChart = barChart;
//# sourceMappingURL=barChart.component.js.map