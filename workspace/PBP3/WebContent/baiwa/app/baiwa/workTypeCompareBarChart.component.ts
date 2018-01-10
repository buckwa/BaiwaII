import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/workTypeCompareBarChart.component.html'
})

export class workTypeCompareBarChart implements OnInit {
    public json: any;
    public nameDepart: any[];
    public mean1: any;
    public headDepart: any
    public WorkTypeCompareReport: any;
    constructor(private http: Http) {
        this.WorkTypeCompareReport = this.WorkTypeCompareReportDefult();
        this.WorkTypeCompareReport.type1 = true;
    }
    ngOnInit() {
        this.getDepartment();
        

    }
    WorkTypeCompareReportDefult() {
        return {
            "type1": "",
            "type2": "",
            "type3": "",
            "type4": "",
            "type5": "",

        }
    }

    public getCompare() {
        var url = "../dean/workTypeCompareBarChart";
        return this.http.post(url,this.WorkTypeCompareReport).subscribe(response => this.GetComparesucess(response),
            error => this.GetDepartmentNameError(error), () => console.log("DepartmentName !"));
    }

    public GetComparesucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));
        this.getChart();
        //this.nameDepart = this.json.resObj.faculty;
        //this.mean1 = this.json.mean1;
        //this.createChart(this.nameDepart[0].email);

    }
    changeCheckBox(){
        console.log("changeCheckBok");
         window.setTimeout(() => {
           this.getCompare();
        }, 600);
        
    }

    public GetDepartmentNameError(error: String) {
        console.log("GetDepartmentNameError.")

    }
    getChart(){
        jQuery("#columnChart").kendoChart({
		title: {
			text: "ระดับคะแนนในภาควิชา"
		},
		dataSource:{
			data: this.json,
		 	group: {
		 		field: "groupName"
		 	},
		 	sort: {
		 		field: "orderNo",
		 		dir: "asc"
	 		},
			schema: {
		        model: {
		            fields: {
		        		categoryName: { type: "string" },
		        		groupName: { type: "string" },
		            	score: { type: "string" },
		        		orderNo: { type: "number" }
		            }
		        }
			}
		},
		series: [{
			type: "column",
			field: "axisValue",
			name: "#= group.value #"
		}],
		legend: {
            position: "top"
        },
		valueAxis: {
			min: 0,
			max: 1000,
			majorUnit: 1000,
		},
		categoryAxis: {
			field: "categoryName",
			labels: {
				rotation : -90
			}
		},
		tooltip: {
			visible: true,
			template: "#= series.name #: #= value #"
		}
	});
    }
    public getDepartment() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetkendoSucess(response),
            error => this.GetDepartmentNameError(error), () => console.log("DepartmentName !"));
    }

    public GetkendoSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.facultyName;
        this.getCompare();
        //this.mean1 = this.json.mean1;
        
    }


    

}