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
var personReport = (function () {
    function personReport() {
        this.libPath = "/PBP3/baiwa/libs/";
    }
    personReport.prototype.ngOnInit = function () {
        this.kendoChart();
    };
    personReport.prototype.ngAfterViewInit = function () {
    };
    personReport.prototype.kendoChart = function () {
        jQuery("#KendoChart").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../president/getWorkTypeBarchart/1",
                        dataType: "json"
                    }
                }
            },
            title: {
                text: "ระดับคะแนนระดับสถาบัน ด้านวิชาการ"
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
                max: 60,
                majorUnit: 10
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
        __metadata('design:paramtypes', [])
    ], personReport);
    return personReport;
}());
exports.personReport = personReport;
//# sourceMappingURL=personReport.component.js.map