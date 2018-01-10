import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';


@Component({
    templateUrl: 'app/baiwa/html/previousAcademicWork.component.html'
})
export class PreviousAcademicWorkComponent  {

    public academicPersonList: any[];
    public tabPerson: boolean;
    public personWork: any;
    public personWorkList: any[];
    public academicKPIUserMappingList: any[];
    public academicYearList: any;
    public json: any;
    public nameDepart: any;
    public profile: any;
    public currentAcademicYear: any;
    user: any;
    year: any;

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
        this.academicYearList = this.user.academicYearList;
        this.currentAcademicYear = this.user.currentAcademicYear;
        
        this.GetPersonByAcadamy(this.user.userName, this.user.currentAcademicYear);


    }
    public GetUserSessionError(error: String) {
        console.log("GetPersonError.")

    }

    public GetPersonByAcadamy(user: String, year: String) {
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year
        this.http.get(url).subscribe(response => this.GetPersonSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );
    }

    public GetPersonSucess(response: any) {
        
       this.profile = response.json(JSON.stringify(response._body));
       this.initTotle(this.currentAcademicYear);

    }
    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }

    initTotle(year: any) {
        this.commonService.loading();
        var url = "../head/initByYear/"+this.profile.evaluateRound+"/"+year;
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

    public changeYear(year: any) {
        // alert(year);
        this.initTotle(year);
    }


}