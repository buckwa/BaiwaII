import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { RouterModule }   from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';
import {Observable} from 'rxjs/Rx';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/personReportInit.component.html'
})
export class personReportInit {

    public libPath: string;
    public json: any;
    public user: any;
    public json2: any;

    constructor(private commonService: CommonService, private http: Http, private sanitizer: DomSanitizer) {
        this.libPath = "/PBP3/baiwa/libs/";
     
    }
    ngOnInit() {
        this.creatChart();
        this.kendoGrid();
       // this.GetUserSession();

    }

    ngAfterViewInit() {

    }

    public kendoGrid(){
        var url = "../person/getRadarPlotNewByYear/2558";
        return this.http.get(url).subscribe(response => this.GetkendoGridSucess(response),
        error => this.GetPersonError(error), () => console.log("getRadarPlotNewByYear1 !"));
    }

    public GetkendoGridSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));

    }

    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }



    public creatChart(){
         jQuery("#chartKendo").kendoChart({
                title: {
                    text: "คะแนนประจำปี  " 
                },
                dataSource: {
                    transport: {
                        read: {
                            url: "../person/getRadarPlotNewByYear/2558",
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
            console.log("getRadarPlotNewByYear2 !");
    }



}