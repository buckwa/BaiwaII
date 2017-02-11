import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/personReport.component.html'
})
export class personReport  {

    public libPath: string;
    public json: any;
    public nameDepart: any;
    public mean1: any;
    public user:any;
    public academicYearList :any;
    public currentAcademicYear :any;

    constructor(private commonService: CommonService, private http: Http) {
        this.libPath = "/PBP3/baiwa/libs/";

    }
    ngOnInit() {
        
        this.GetUserSession();
        
       
    }
    ngAfterViewInit() {
        
    }

    public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetuserSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !"));
    }

    public GetuserSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.academicYearList = this.user.academicYearList;
        this.currentAcademicYear = this.user.currentAcademicYear;

        this.DepartmentName(this.currentAcademicYear);
    }
    
    public DepartmentName(year: any){
        var url = "../person/DepartmentName/"+year ;
        this.currentAcademicYear = year;
        return this.http.get(url).subscribe(response => this.GetkendoSucess(response),
        error => this.GetDepartmentNameError(error), () => console.log("DepartmentName !"));
    }

    public GetkendoSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart =this.json.departmentName;
        this.mean1 =this.json.mean1;
        this.kendoChart();
    }

    public GetDepartmentNameError(error: String) {
        console.log("GetDepartmentNameError.")

    }
    
        
    public NameDepartment(){
        var url = "../person/getRadarPlotNewByYear/"+this.currentAcademicYear;
        return this.http.get(url).subscribe(response => this.GetkendoGridSucess(response),
        error => this.GetPersonError(error), () => console.log("getRadarPlotNewByYear1 !"));
    }

     public GetkendoGridSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));

    }

    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }

    public kendoChart() {

        var year =this.currentAcademicYear ; 
        jQuery("#KendoChart").kendoChart({
                 dataSource: {
                     transport: {
                         read: {
                         	 url: "../person/getBarchart/"+year ,
                             dataType: "json"
                         }
                     } 
                 },
        	        title: {
        	            text: "ระดับคะแนน ค่าเฉลี่ย "+ this.mean1
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
        	        	max: 7000,
        	        	majorUnit: 1000
       	        	},
                    tooltip: {
                        visible: true,
                        template: "#= series.name #: #= value #"
                    }
        	    });

    }

    public changeYear(year: any){

        this.DepartmentName(year);
    }

}