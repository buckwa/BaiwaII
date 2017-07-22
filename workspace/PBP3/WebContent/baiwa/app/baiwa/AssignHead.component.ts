import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';

import { Router, ActivatedRoute,NavigationCancel  } from '@angular/router';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/AssignHead.component.html'
})

export class AssignHead implements OnInit {
	public json: any;
    public nameDepart: any;
    public mean1: any;
    public academicYearList: any;
    public currentAcademicYear: any;
	public user: any;
    public departmentName: any;
	public Person1: any;
    public Person2: any;

	constructor(private http: Http,private router: Router,private commonService: CommonService) {
    }
    ngOnInit() {

		this.GetUserSession();

    }

	public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetuserSucess(response),
            error => this.GetuserError(error), () => console.log("editdone !"));
    }

    public GetuserSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.academicYearList = this.user.academicYearList;
        this.currentAcademicYear = this.user.currentAcademicYear;
        this.departmentName = this.user.departmentName;

        this.GetPersonList();
    }

    public GetuserError(response: any) {
        console.log("Error !")

    }

    public GetPersonListError(response: any) {
        console.log("Error !")

    }

	public GetPersonList() {
        this.commonService.loading();
        var url = "../head/AssignHeadY/"+this.departmentName;
        return this.http.get(url).subscribe(response => this.GetPersonListSucess(response),
            error => this.GetPersonListError(error), () => console.log("editdone !"));
    }

    public GetPersonListSucess(response: any) {
       this.Person1 = response.json(JSON.stringify(response._body));

        this.GetPersonListN();
    }

	public GetPersonListN() {
        var url = "../head/AssignHeadN/"+this.departmentName;
        return this.http.get(url).subscribe(response => this.GetPersonListNSucess(response),
            error => this.GetPersonListError(error), () => console.log("editdone !"));
    }

    public GetPersonListNSucess(response: any) {
       this.Person2 = response.json(JSON.stringify(response._body));
       this.commonService.unLoading();
    }


	public DeletePersonList(username :any) {
        var url = "../head/AssignHeadDelete/"+username+"/2";
        return this.http.get(url).subscribe(response => this.DeletePersonListNSucess(response),
            error => this.GetPersonListError(error), () => console.log("editdone !"));
    }

    public DeletePersonListNSucess(response: any) {
       alert("บันทึกเรียบร้อย");
       location.reload();
    }


	public EditPersonList(username :any) {
        var url = "../head/AssignHeadEdit/"+username+"/1";
        return this.http.get(url).subscribe(response => this.EditPersonListNSucess(response),
            error => this.GetPersonListError(error), () => console.log("editdone !"));
    }

    public EditPersonListNSucess(response: any) {
       alert("บันทึกเรียบร้อย");
       location.reload();

    }


}