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
var workTypeBarChart = (function () {
    function workTypeBarChart() {
        this.libPath = "/PBP3/baiwa/libs/";
    }
    workTypeBarChart.prototype.ngOnInit = function () {
        this.kendoChart();
    };
    workTypeBarChart.prototype.ngAfterViewInit = function () {
    };
    workTypeBarChart.prototype.kendoChart = function () {
        jQuery("#chart1").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../person/getWorkTypeBarchart/1",
                        dataType: "json"
                    }
                }
            },
            title: {
                text: "ระดับคะแนนในภาควิชา ด้านวิชาการ"
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
                max: 1000,
                majorUnit: 500
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
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
                text: "ระดับคะแนนในภาควิชา ด้านงานพัฒนาวิชาการ"
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
                max: 1000,
                majorUnit: 100
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
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
                text: "ระดับคะแนนในภาควิชา ด้านงานวิจัย"
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
                max: 1000,
                majorUnit: 100
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
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
                text: "ระดับคะแนนในภาควิชา ด้านงานบริการวิชาการ"
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
                max: 1000,
                majorUnit: 100
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
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
                text: "ระดับคะแนนในภาควิชา ด้านงานทำนุบำรุงศิลป"
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
                max: 1000,
                majorUnit: 100
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
        __metadata('design:paramtypes', [])
    ], workTypeBarChart);
    return workTypeBarChart;
}());
exports.workTypeBarChart = workTypeBarChart;
//# sourceMappingURL=workTypeBarChart.component.js.map