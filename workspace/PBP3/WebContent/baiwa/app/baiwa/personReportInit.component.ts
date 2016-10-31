import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/personReportInit.component.html'
})
export class personReportInit {

    public libPath: string;
    public json: any;

    public json2: any;

    constructor() {
        this.libPath = "/PBP3/baiwa/libs/";
        this.json = [{ "orderNo": 0, "axisName": "งานวิชาการ", "axisValue": "810.00", "axisName2": "งานวิชาการ", "axisValue2": "810.00", "mean": null }, { "orderNo": 0, "axisName": "งานพัฒนาวิชาการ", "axisValue": "100.00", "axisName2": "งานพัฒนาวิชาการ", "axisValue2": "100.00", "mean": null }, { "orderNo": 0, "axisName": "งานวิจัย หรือ สร้างสรรค์", "axisValue": "0.00", "axisName2": "งานวิจัย หรือ สร้างสรรค์", "axisValue2": "0.00", "mean": null }, { "orderNo": 0, "axisName": "งานบริการวิชาการ", "axisValue": "0.00", "axisName2": "งานบริการวิชาการ", "axisValue2": "0.00", "mean": null }, { "orderNo": 0, "axisName": "งานทำนุบำรุงศิลป วัฒนธรรมและสร้างชื่อเสียงให้กับสถาบันฯ", "axisValue": "0.00", "axisName2": "งานทำนุบำรุงศิลป วัฒนธรรมและสร้างชื่อเสียงให้กับสถาบันฯ", "axisValue2": "0.00", "mean": null }]


        this.json2 = [{ "orderNo": 0, "axisName": "งานวิชาการ", "axisValue": "810.00", "axisName2": "งานวิชาการ", "axisValue2": "810.00", "mean": null }, { "orderNo": 0, "axisName": "งานพัฒนาวิชาการ", "axisValue": "100.00", "axisName2": "งานพัฒนาวิชาการ", "axisValue2": "100.00", "mean": null }, { "orderNo": 0, "axisName": "งานวิจัย หรือ สร้างสรรค์", "axisValue": "0.00", "axisName2": "งานวิจัย หรือ สร้างสรรค์", "axisValue2": "0.00", "mean": null }, { "orderNo": 0, "axisName": "งานบริการวิชาการ", "axisValue": "0.00", "axisName2": "งานบริการวิชาการ", "axisValue2": "0.00", "mean": null }, { "orderNo": 0, "axisName": "งานทำนุบำรุงศิลป วัฒนธรรมและสร้างชื่อเสียงให้กับสถาบันฯ", "axisValue": "0.00", "axisName2": "งานทำนุบำรุงศิลป วัฒนธรรมและสร้างชื่อเสียงให้กับสถาบันฯ", "axisValue2": "0.00", "mean": null }]


    }
    ngOnInit() {
        this.creatChart();


    }

    ngAfterViewInit() {

    }

    public creatChart(){
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
            

    }



}