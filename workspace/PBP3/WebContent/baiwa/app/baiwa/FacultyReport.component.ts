import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/FacultyReport.component.html'
})

export class FacultyReport implements OnInit {
    public json: any;
    public nameDepart: any;
    public mean1: any;
    public maxVal :any;

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
        var maxVal;
        for (var i = 0; i < this.json.length; i++) {
            if (this.json[i].axisValue > this.json[i].axisValue2 && this.json[i].axisValue > maxVal) {
                maxVal = this.json[i].axisValue;
            } else if (this.json[i].axisValue < this.json[i].axisValue2 && this.json[i].axisValue2 > maxVal) {
                maxVal = this.json[i].axisValue2;
            }
        }
        this.maxVal = maxVal;

        this.getbarChart();
    }

    public GetDepartmentNameError(error: String) {
        console.log("GetDepartmentNameError.")

    }
    getbarChart(){
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
       		                url:    "../dean/facultyReport",
       		                dataType: "Json"
       		            }
       		        }
       		    },
       		    columns   : [
       		        { field: "axisName", title: "ภาควิชา" },
       		        { field: "axisValue", title: "คะแนน เฉลี่ย" }
       		    ]
       		}); 
    }
}