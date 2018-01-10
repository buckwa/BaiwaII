import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';


@Component({
    templateUrl: 'app/baiwa/html/AdminAcademicYear.component.html'
})

export class AdminAcademicYear implements OnInit {
    public AcademicYearlist: any;
    public AcademicYear: any = [
        {
        "name": "Test",
        "startDateStr":"1 ส.ค. 2558",
        "endDateStr":"31 ก.ค. 2559",
        "termstartDate":"31 ก.ค. 2559",
        "termendDate":"31 ก.ค. 2559"
        } 
        ,{
            "name": "Test2",
            "startDateStr":"1 ส.ค. 2558",
            "endDateStr":"31 ก.ค. 2559",
            "termstartDate":"31 ก.ค. 2559",
            "termendDate":"31 ก.ค. 2559"
        }
   ];
    constructor(private router: Router, private http: Http, private route: ActivatedRoute) {

    }

    ngOnInit() {
        this.adminAcademicYear();

    }


    public adminAcademicYear() {
        var url = "../admin/json/getAcademicYear/init";
        return this.http.get(url).map(response => response.json()).subscribe(response => this.adminAcademicYearSucess(response),
            error => this.adminAcademicYearError(error), () => console.log("DepartmentName !"));

    }

    public adminAcademicYearSucess(response: any) {
        console.log(response);
        this.AcademicYearlist = response.resObj[1];
        
        console.log("sucsess !");
    }

    public adminAcademicYearError(error: string) {
        console.log("getAdminAcademicYearError.")
    }

    public AcademicYearAction(year: string,startDate:string ,endDate:string){
        // alert(year);
        this.router.navigate(['/adminAcademicYearEdit',year,startDate,endDate]);
    }

    public AdjustAcademicYearAction(year: string){

        alert("AdjustAcademicYearAction");
        
    }
}