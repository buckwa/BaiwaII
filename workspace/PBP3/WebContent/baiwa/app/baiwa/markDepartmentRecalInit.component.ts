import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/markDepartmentRecalInit.component.html'
})


export class markDepartmentRecalInit  {

    public libPath: string;
    public user: any;
    public academy: any;
    public academyList: any[];
    public kpiuserList: any[] = [];
    public pointKPI: any;
    public mark: String;
    public pointLPIList: any[];
    public kpival: any[][];
    public statusKpi: boolean;
    public fileWork: any[];
    public tmpUrl: any;
    public codeKpi: string;
    public academicYearList :any;
    public currentAcademicYear :any;
    public profile:any;
    public departmentName:any;
    public model:any;

    constructor(private http: Http,private commonService: CommonService) {
        
    }
    ngOnInit() {

       this.GetUserSession();
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
        this.departmentName = this.user.departmentName;
        this.currentAcademicYear = this.user.currentAcademicYear;
    }


    public GetUserSessionError(error: String) {
        console.log("GetPersonError.")

    }

     public ClickAction(){
        this.commonService.loading();
        var url = "../head/pbp/markDepartmentRecal";
        return this.http.get(url).subscribe(response => this.ClickActionSucess(response),
            error => this.GetActionUserSessionError(error), () => console.log("editdoneUser !"));
    }

     public ClickActionSucess(response: any){
          this.commonService.unLoading();
        this.model = response.json(JSON.stringify(response._body));
        alert("คำนวณเสร็จสิ้น");
    } 

    public GetActionUserSessionError(error: String) {
         this.commonService.unLoading();
        console.log("GetPersonError.")

    }

}