import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';



@Component({
    templateUrl: 'app/baiwa/html/AdminChainOfCommandinit.component.html'
})

export class AdminChainOfCommandinit implements OnInit {
    public FacultyWrappers: any;
    public FacultyWrapper: any;
    public academicYear: any;
    public facultyList: any;
    public president: any;
    public thaiName: any;
    public thaiSurname: any;
    constructor(private http: Http) {
       

    }

    

    ngOnInit() {
        this.chainOfCommand();
       
    }


    public chainOfCommand() {

        var url = "../admin/json/getFacultyWrapper";
        return this.http.get(url).subscribe(response => this.chainOfCommandSucess(response),
            error => this.chainOfCommandError(error), () => console.log("DepartmentName !"));
    }


    public chainOfCommandSucess(response: any) {

        this.FacultyWrappers = response.json(JSON.stringify(response._body));
        this.FacultyWrapper = this.FacultyWrappers[0];
        this.academicYear = this.FacultyWrapper.academicYear;
        this.thaiName = this.FacultyWrapper.president.thaiName;
        this.thaiSurname = this.FacultyWrapper.president.thaiSurname;
        this.facultyList = this.FacultyWrapper.facultyList;
          

        console.log("SS !");
    }

    public chainOfCommandError(error: String) {

        console.log("GetchainOfCommandError.")

    }


}
