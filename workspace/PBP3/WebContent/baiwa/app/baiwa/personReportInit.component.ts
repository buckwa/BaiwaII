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
    public json: any[];
    public user: any;
    public json2: any;
    public maxVal: any;
    public academicYearList :any;
    public currentAcademicYear :any;

    public url;
    constructor(private commonService: CommonService, private http: Http, private sanitizer: DomSanitizer) {
        this.libPath = "/PBP3/baiwa/libs/";

    }
    ngOnInit() {
        this.GetUserSession();
        // this.GetUserSession();

    }

    ngAfterViewInit() {

    }
    public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetUserSessionSucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }
    public GetUserSessionSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.academicYearList = this.user.academicYearList;
        this.currentAcademicYear = this.user.currentAcademicYear;



        this.kendoGrid(this.user.currentAcademicYear);

    }
    public GetUserSessionError(error: String) {
        console.log("GetPersonError.")

    }

    public kendoGrid(year: any) {
        var url = "../person/getRadarPlotNewByYear/" + year;
        this.currentAcademicYear =year;
        this.url  = url;
        return this.http.get(url).subscribe(response => this.GetkendoGridSucess(response),
            error => this.GetPersonError(error), () => console.log("getRadarPlotNewByYear1 !"));
    }

    public GetkendoGridSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));
        var maxVal;
        for (var i = 0; i < this.json.length; i++) {
            if (this.json[i].axisValue > this.json[i].axisValue2 && this.json[i].axisValue > maxVal) {
                maxVal = this.json[i].axisValue;
            } else if (this.json[i].axisValue < this.json[i].axisValue2 && this.json[i].axisValue2 > maxVal) {
                maxVal = this.json[i].axisValue2;
            }
        }
        this.maxVal = maxVal;

        this.creatChart();

    }

    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }

    public creatChart() {
        
        var Year = this.currentAcademicYear ;
        jQuery("#chartKendo").kendoChart({
            title: {
                text: "คะแนนประจำปี  "+Year,
            },
            dataSource: {
                transport: {
                    read: {
                        url: this.url ,
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
         
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
        console.log("getRadarPlotNewByYear2 !");
    }

    public changeYear(year: any){

        this.kendoGrid(year);
    }

}