import { Component, Injectable,Input,ViewChild,OnInit,Output,EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/deanBarChart.component.html'
})

export class deanBarChart implements OnInit  {
	     public json: any;
    public nameDepart: any;
    public mean1: any;
    public headDepart:any;
    
    constructor(private http: Http) {
    }
    ngOnInit() {
        this.DepartmentName();
    }

    public DepartmentName() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetkendoSucess(response),
            error => this.GetDepartmentNameError(error), () => console.log("DepartmentName !"));
    }

    public GetkendoSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.facultyName;
        //this.mean1 = this.json.mean1;
        this.getbarChart();
    }

    public GetDepartmentNameError(error: String) {
        console.log("GetDepartmentNameError.")

    }
    getbarChart(){
         jQuery("#chart").kendoChart({
                 dataSource: {
                     transport: {
                         read: {
                         	 url: "../dean/getBarchart",
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
        	        valueAxis: {
        	        	min: 0,        	  
        	        	max: 105000,
        	        	majorUnit: 10000
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
        		                url:    "../dean/getBarchart",
        		                dataType: "Json"
        		            }
        		        }
        		    },
        		    columns   : [
        		        { field: "axisName", title: "ภาควิชา" },
        		        { field: "axisValue", title: "คะแนน" }
        		    ]
        		});
    }
}