import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/AdminAcademicKPI.component.html'
})

export class AdminAcademicKPI implements OnInit {

    public model: any;
    public facultyName: any;
    public workTypeName: any;
    public academicYear: any;
    public academicYearList: any;
    public pBPWorkTypeList: any;
    public facultyList: any;
    public academicKPIList: any;
    public searchAtti: any;
    public academicYearSelect: any;

    public ModelSearch() {
        return {
            "workTypeCode": "",
            "academicYear": "",
            "facultyCode": "",
        }
    }

    ngOnInit() {
        var year =new Date().getFullYear()+542;
        this.academicYearSelect = year;

        this.searchAtti = this.ModelSearch();

    }

    constructor(private commonService: CommonService, private router: Router, private http: Http) {

        this.getlistKPI();
    }

    public getlistKPI() {
        //console.log("getlistKPI : Ready getlistByDepartment ");
        var url = "../admin/pbp/academicKPI/init";
        this.http.get(url).subscribe(response => this.GetlistKPISucess(response),
            error => this.GetlistKPIJsonError(error), () => console.log(" Sent Success !"));
    }

    public GetlistKPISucess(response: any) {

        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        this.facultyName = this.model.facultyName;
        this.workTypeName = this.model.workTypeName;
        this.academicYear = this.model.academicYear;
        this.academicKPIList = this.model.academicKPIList;

        this.academicYearList = this.model.academicYearList;
        this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        this.facultyList = this.model.facultyList;


        this.searchAtti.facultyCode  = this.model.facultyCodeSelect;
        this.searchAtti.academicYear =this.model.academicYear;
        this.searchAtti.workTypeCode =this.model.workTypeCode;
        console.log(" List Success  !");
    }
    public GetlistKPIJsonError(error: any) {

        console.log(" Error !");
    }
    //workTypeCode: any,academicYear:any,facultyCodeSelect:any
    public GetSearchKpI() {
        var url = "../admin/pbp/academicKPI/search/" + this.searchAtti.workTypeCode + "/" + this.searchAtti.academicYear + "/" + this.searchAtti.facultyCode;
        this.http.get(url).subscribe(response => this.GetlistKPISucess(response),
            error => this.GetlistKPIJsonError(error), () => console.log(" Sent Success !"));
    }

    public GetSearchKPISucess(response: any) {

        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        this.facultyName = this.model.facultyName;
        this.workTypeName = this.model.workTypeName;
        this.academicYear = this.model.academicYear;
        this.academicKPIList = this.model.academicKPIList;


        this.searchAtti.facultyCode  = this.model.facultyCodeSelect;
        this.searchAtti.academicYear =this.model.academicYear;
        this.searchAtti.workTypeCode =this.model.workTypeCode;

        // this.academicYearList = this.model.academicYearList;
        // this.pBPWorkTypeList =this.model.pBPWorkTypeList ;
        // this.facultyList = this.model.facultyList;
        console.log(" Search Success  !", this.academicKPIList);
    }
    public GetCreateKPI() {
        this.router.navigate(['/AdminAcademicKPIcreate', this.searchAtti.workTypeCode, this.searchAtti.academicYear, this.searchAtti.facultyCode]);
        console.log(" GetCreateKPI !");
    }
    public GetUpdateKPI(academicKPIId: any) {
        this.router.navigate(['/AdminAcademicKPIedit', academicKPIId]);
        console.log(" GetUpdateKPI !");
    }

    public GetDeleteKPI(academicKPIId: any) {

        this.commonService.confirm("Are you sure you want to delete?", jQuery.proxy(function (isOk: any) {
            if (isOk) {
                //action 
                var url = "../admin/pbp/academicKPI/delete.htm/" + academicKPIId;
                this.http.get(url).subscribe(response => this.getTimeTableSucess(response),
                    error => this.GetPersonError(error), () => console.log("callsevice done !"));
               
            }
        }, this));


        console.log(" GetDeleteKPI !");
    }
    public getTimeTableSucess() {
        location.reload();

    }
}