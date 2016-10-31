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
var personReportInit = (function () {
    function personReportInit() {
        this.libPath = "/PBP3/baiwa/libs/";
        this.json = [{ "orderNo": 0, "axisName": "งานวิชาการ", "axisValue": "810.00", "axisName2": "งานวิชาการ", "axisValue2": "810.00", "mean": null }, { "orderNo": 0, "axisName": "งานพัฒนาวิชาการ", "axisValue": "100.00", "axisName2": "งานพัฒนาวิชาการ", "axisValue2": "100.00", "mean": null }, { "orderNo": 0, "axisName": "งานวิจัย หรือ สร้างสรรค์", "axisValue": "0.00", "axisName2": "งานวิจัย หรือ สร้างสรรค์", "axisValue2": "0.00", "mean": null }, { "orderNo": 0, "axisName": "งานบริการวิชาการ", "axisValue": "0.00", "axisName2": "งานบริการวิชาการ", "axisValue2": "0.00", "mean": null }, { "orderNo": 0, "axisName": "งานทำนุบำรุงศิลป วัฒนธรรมและสร้างชื่อเสียงให้กับสถาบันฯ", "axisValue": "0.00", "axisName2": "งานทำนุบำรุงศิลป วัฒนธรรมและสร้างชื่อเสียงให้กับสถาบันฯ", "axisValue2": "0.00", "mean": null }];
        this.json2 = [{ "orderNo": 0, "axisName": "งานวิชาการ", "axisValue": "810.00", "axisName2": "งานวิชาการ", "axisValue2": "810.00", "mean": null }, { "orderNo": 0, "axisName": "งานพัฒนาวิชาการ", "axisValue": "100.00", "axisName2": "งานพัฒนาวิชาการ", "axisValue2": "100.00", "mean": null }, { "orderNo": 0, "axisName": "งานวิจัย หรือ สร้างสรรค์", "axisValue": "0.00", "axisName2": "งานวิจัย หรือ สร้างสรรค์", "axisValue2": "0.00", "mean": null }, { "orderNo": 0, "axisName": "งานบริการวิชาการ", "axisValue": "0.00", "axisName2": "งานบริการวิชาการ", "axisValue2": "0.00", "mean": null }, { "orderNo": 0, "axisName": "งานทำนุบำรุงศิลป วัฒนธรรมและสร้างชื่อเสียงให้กับสถาบันฯ", "axisValue": "0.00", "axisName2": "งานทำนุบำรุงศิลป วัฒนธรรมและสร้างชื่อเสียงให้กับสถาบันฯ", "axisValue2": "0.00", "mean": null }];
    }
    personReportInit.prototype.ngOnInit = function () {
        this.creatChart();
    };
    personReportInit.prototype.ngAfterViewInit = function () {
    };
    personReportInit.prototype.creatChart = function () {
        jQuery("#chartKendo").kendoChart({
            title: {
                text: "คะแนนประจำปี  "
            },
            dataSource: {
                transport: {
                    read: {
                        url: "app/baiwa/kendoJson3.txt",
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
                max: 1000
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    };
    personReportInit = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/personReportInit.component.html'
        }), 
        __metadata('design:paramtypes', [])
    ], personReportInit);
    return personReportInit;
}());
exports.personReportInit = personReportInit;
//# sourceMappingURL=personReportInit.component.js.map