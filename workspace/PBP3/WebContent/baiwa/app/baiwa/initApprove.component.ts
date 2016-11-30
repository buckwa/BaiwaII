import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';

@Component({
    templateUrl: 'app/baiwa/html/initApprove.component.html'
})
export class InitApporve implements OnInit {
    public academicPersonList: any[];
    public tabPerson: boolean;
    public personWork: any;
    public personWorkList: any[];
    public academicKPIUserMappingList: any[];
    public json: any;
    public nameDepart: any;
    user: any;
    year:any;


    constructor(private commonService: CommonService, private http: Http) {
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
        this.initTotle();

    }
    public GetUserSessionError(error: String) {
        console.log("GetPersonError.")

    }
    initTotle() {
        this.commonService.loading();
        var url = "../head/init";
        this.http.get(url).subscribe(response => this.initTotleSucess(response),
            error => this.initTotlError(error), () => console.log("editdoneUser !"));
    }
    initTotleSucess(response: any) {
        var bodyJson;
        bodyJson = response.json(JSON.stringify(response._body));
        this.academicPersonList = bodyJson.department.academicPersonList;
        this.commonService.unLoading();


    }
    initTotlError(error: any) {
        console.log("error getTotle");

    }

}