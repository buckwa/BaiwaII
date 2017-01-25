import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';



@Component({
    templateUrl: 'app/baiwa/html/AdminAcademicYear.component.html'
})

export class AdminAcademicYear implements OnInit {
    public AcademicYear: any = {
        "name": ""
    };
    constructor(private http: Http) {

    }

    ngOnInit() {
        this.adminAcademicYear();

    }
    public adminAcademicYear() {
        var url = "../admin/json/getAdminAcademicYear";
        return this.http.get(url).map(r => r.json()).subscribe(response => this.adminAcademicYearSucess(response),
            error => this.adminAcademicYearError(error), () => console.log("DepartmentName !"));

    }

    public adminAcademicYearSucess(response: any) {
        console.log(response);
        this.AcademicYear = response.resObj.academicYear;

        console.log("sucsess !");
    }

    public adminAcademicYearError(error: string) {
        console.log("getAdminAcademicYearError.")
    }
}