import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';
import {Location} from '@angular/common';

@Component({
    templateUrl: 'app/baiwa/html/AdminAcademicYearEdit.component.html'
})

export class AdminAcademicYearEditComponent implements OnInit {
   
    public year: string;
    public startDate: string;
    public endDate: string;
    public model :any;
    
   public AcademicYearTamp: any  = {
    "name": "",
    "startDateStr":"",
    "endDateStr":"",
    "termstartDate":"",
    "termendDate":""
    };

    constructor(private router: Router, private http: Http, private route: ActivatedRoute ,private _location: Location) {

    }

    ngOnInit() {
        this.route.params.subscribe(params => this.year = params["year"]);
        this.route.params.subscribe(params => this.startDate = params["startDate"]);
        this.route.params.subscribe(params => this.endDate = params["endDate"]);

        if (this.year && this.startDate && this.endDate) {
            console.log("Input Year :", this.year);
            console.log("Input StartDate :", this.startDate);
            console.log("Input EndDate :", this.endDate);
        }
        this.getData();
    }
    public getData(){
        var url = "../admin/json/getAcademicYearEdit/"+this.year;
       
            this.http.get(url).subscribe(response => this.adminSentSucess(response),
            error => this.GetSentError(error), () => console.log("getlistByDepartment !")
            );
    }

    public adminSentSucess(response: any) {

        // alert("Success !  1");
        // console.log("Result 1:",response.json(JSON.stringify(response._body)));

        this.AcademicYearTamp = response.json(JSON.stringify(response._body));
        this.AcademicYearTamp = this.AcademicYearTamp.resObj;
        console.log("Data Set",this.AcademicYearTamp)
       // this.router.navigate(['/AdminAcademicKPI',this.workTypeCode,this.academicYear,this.facultyCode]);
    }

    public saveClicked(){
            

            // this.AcademicYearTamp.startDateStr = this.AcademicYearTamp.startDate;
            // this.AcademicYearTamp.endDateStr = this.AcademicYearTamp.endDate;
            console.log("Data Sent",this.AcademicYearTamp);
             var url = "../admin/json/editDateAcademicYear";
            this.http.post(url, this.AcademicYearTamp).subscribe(response => this.CreateSuccess(response),
            error => this.GetSentError(error), () => console.log("AdminUserCreate : Success saveUser !"));
    
    }

    public CreateSuccess(response: any) {

       // alert("Success !");
        // console.log("Result 2:",response.json(JSON.stringify(response._body)));
       this.router.navigate(['/AdminAcademicYear']);
    }

    public GetSentError(response: any) {

        alert("Sent Error !");
    
    }


    public backClicked(){
        this._location.back();
    }

}