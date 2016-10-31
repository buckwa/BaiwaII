import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;
declare var kendo: any;

@Component({
    templateUrl: 'app/baiwa/html/personReport.component.html'
})
export class personReport  {

public libPath: string;

    constructor() {
        this.libPath = "/PBP3/baiwa/libs/";

    }
    ngOnInit() {
        this.kendoChart();
       
    }
    ngAfterViewInit() {
        
    }
        
    public kendoChart(): void {
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

    }



}