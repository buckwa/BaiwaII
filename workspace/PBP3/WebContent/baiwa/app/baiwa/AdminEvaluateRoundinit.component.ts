import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';



@Component({
    templateUrl: 'app/baiwa/html/AdminEvaluateRoundinit.component.html'
})

export class AdminEvaluateRoundinit implements OnInit {
    public AcademicYearTamp1: any={
        evaluateTypeDesc:"",
        round1EndDateShortThaiStr:"",
        round1StartDateShortThaiStr:"",
        round2EndDateShortThaiStr:"",
        round2StartDateShortThaiStr:""
    };
    public AcademicYearTamp2: any={
        evaluateTypeDesc:"",
        round1EndDateShortThaiStr:"",
        round1StartDateShortThaiStr:"",
        round2EndDateShortThaiStr:"",
        round2StartDateShortThaiStr:""
    };
    ngOnInit() {
        this.adminAcademicYear1();
        this.adminAcademicYear2();
        
    }
    constructor(private router: Router, private http: Http, private route: ActivatedRoute) {

    }

    public adminAcademicYear1() {
        var url = "../admin/json/editDateEvaluateRound/2558/1";
        
        this.http.get(url).subscribe(response => this.adminSentSucess1(response),
        error => this.GetSentError(error), () => console.log("getlistByDepartment !")
        );
    }
    public adminSentSucess1(response: any) {
       
        this.AcademicYearTamp1 = response.json(JSON.stringify(response._body));
        this.AcademicYearTamp1 = this.AcademicYearTamp1.resObj;
        console.log( this.AcademicYearTamp1 );
        console.log("sucsess !");
    }


    public adminAcademicYear2() {
        var url = "../admin/json/editDateEvaluateRound/2558/2";
        
        this.http.get(url).subscribe(response => this.adminSentSucess2(response),
        error => this.GetSentError(error), () => console.log("getlistByDepartment !")
        );
    }
    public adminSentSucess2(response: any) {
       
        this.AcademicYearTamp2 = response.json(JSON.stringify(response._body));
        this.AcademicYearTamp2 = this.AcademicYearTamp2.resObj;
        console.log("Result2:",  this.AcademicYearTamp2 );
        console.log("sucsess !");
    }


    public GetSentError(error: string) {
        console.log("getAdminAcademicYearError.")
    }


    public getlistKPI() {

        var url = "../admin/pbp/academicKPI/init";
        this.http.get(url).subscribe(response => this.GetlistKPISucess(response),
            error => this.GetlistKPIJsonError(error), () => console.log(" Sent Success !"));
    
    }

    public GetlistKPISucess(response: any) {

        // this.model = response.json(JSON.stringify(response._body));
        // this.model = this.model.resObj;
        // this.facultyName = this.model.facultyName;
        // this.workTypeName = this.model.workTypeName;
        // this.academicYear = this.model.academicYear;
        // this.academicKPIList = this.model.academicKPIList;

        // this.academicYearList = this.model.academicYearList;
        // this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        // this.facultyList = this.model.facultyList;


        // this.searchAtti.facultyCode  = this.model.facultyCodeSelect;
        // this.searchAtti.academicYear =this.model.academicYear;
        // this.searchAtti.workTypeCode =this.model.workTypeCode;
        console.log(" List Success  !");
    }
    public GetlistKPIJsonError(error: any) {

        console.log(" Error !");
    }

}