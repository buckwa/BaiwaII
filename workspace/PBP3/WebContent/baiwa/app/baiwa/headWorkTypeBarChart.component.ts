import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/headWorkTypeBarChart.component.html'
})

export class headWorkTypeBarChart implements OnInit {
    public libPath: string;
    public json: any;
    public nameDepart: any;
    public mean1: any;

    constructor(private http: Http) {
    }
    ngOnInit() {
        this.DepartmentName();

    }
    public DepartmentName() {
        var url = "../person/MinMaxBean";
        return this.http.get(url).subscribe(response => this.GetkendoSucess(response),
            error => this.GetDepartmentNameError(error), () => console.log("DepartmentName !"));
    }

    public GetkendoSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.departmentName;
        this.getChart1();
        this.getChart2();
        this.getChart3();
        this.getChart4();
        this.getChart5();

    }

    public GetDepartmentNameError(error: String) {
        console.log("GetDepartmentNameError.")

    }
    getChart1() {
        var start= this.json.mean1;
        var end = start + 2;
        var startMin = this.json.minValue1;
        var endMin = startMin + 2;
        var startMax = this.json.maxValue1;
        var endMax = startMax + 2;
        jQuery("#chart1").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../head/getWorkTypeBarchart/1",
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
    }
    getChart2() {
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
                        url: "../head/getWorkTypeBarchart/2",
                        dataType: "json"
                    }
                },

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
                // min: 0,
                // max: 1200,
                // majorUnit: 200,
                plotBands: [
                    { from: start, to: end, color: "orange" },
                    { from: startMax, to: endMax, color: "green" },
                    { from: startMin, to: endMin, color: "red" }
                ]
            }
            
            ,
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
    }
    getChart3() {
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
                        url: "../head/getWorkTypeBarchart/3",
                        dataType: "json"
                    }
                },

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
            }
            
            
            ,
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });


    }
    getChart4() {

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
                        url: "../head/getWorkTypeBarchart/4",
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
            }
            ,
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });


    }
    getChart5() {

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
                        url: "../head/getWorkTypeBarchart/5",
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
            }
            ,
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });



    }



}
