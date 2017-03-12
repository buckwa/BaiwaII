import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';

@Component({
    templateUrl: 'app/baiwa/html/initApproveByKPI.component.html'
})
export class initApproveByKPI implements OnInit {

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

    public academicPersonList: any[];
    public tabPerson: boolean;
    public personWork: any;
    public personWorkList: any[];
    public academicKPIUserMappingList: any[];
    public json: any;
    public nameDepart: any;
    public workTypeCode: any;
   public headApprove: any;
    user: any;
    year: any;


    constructor(private router: Router, private commonService: CommonService, private http: Http) {

        this.tabPerson = false;

    }

    ngOnInit() {

        this.GetUserSession();

    }
    public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetUserSessionSucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }
    public GetUserSessionSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.nameDepart = this.user.facultyName;
        this.year = this.user.currentAcademicYear;

        this.academicKPI();

    }
    public GetUserSessionError(error: String) {
        console.log("GetPersonError.")

    }

    academicKPI() {
        this.commonService.loading();
        var url = "../admin/pbp/academicKPI/initApproveByKPI2/1/" + this.user.currentAcademicYear + "/" + this.user.facultyCode + "/" + this.user.departmentName;
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
        this.workTypeCode = '1';
        this.academicYearList = this.model.academicYearList;
        this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        this.facultyList = this.model.facultyList;
        this.headApprove =  this.model.headApprove;
        this.commonService.unLoading();
        console.log(" List Success  !");
    }

    public GetlistKPIJsonError(error: any) {
        console.log(" Error !");
    }



    public GetSearchKpI() {
        this.commonService.loading();
        var url = "../admin/pbp/academicKPI/initApproveByKPI2/" + this.workTypeCode + "/" + this.user.currentAcademicYear + "/" + this.user.facultyCode + "/" + this.user.departmentName;
        this.http.get(url).subscribe(response => this.GetSearchKPISucess(response),
            error => this.GetlistKPIJsonError(error), () => console.log(" Sent Success !"));
    }

    public GetSearchKPISucess(response: any) {

        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        this.facultyName = this.model.facultyName;
        this.workTypeName = this.model.workTypeName;
        this.academicYear = this.model.academicYear;
        this.academicKPIList = this.model.academicKPIList;
        this.headApprove =  this.model.headApprove;
        this.academicYearList = this.model.academicYearList;
        this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        this.facultyList = this.model.facultyList;

        this.commonService.unLoading();

        console.log(" List Success  !");
    }

    public clickA(k_ID: any, summary: string) {
        if (summary != '0') {
            this.router.navigate(['/approveworkByKPI', k_ID, 'A']);
        }
    }

    public clickC(K_ID: any, summary: string) {
        if (summary != '0') {
            this.router.navigate(['/approveworkByKPI', K_ID, 'C']);
        }

    }

}