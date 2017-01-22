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
    public listByDepartments: any;
    public listByDepartment: any;
    public department: any;
    public academicPersonList: any;
    public DepartmentName: any;
    constructor(private http: Http) {
       
          this.facultyList = this.facultyListJson();
          this.FacultyWrapper=this.FacultyWrapperJson();
    }


    facultyListJson() {
        return [{
          "name":"",
          "departmentList":[{
                "name":"",
                "head":{
                     "email":null,
                    "thaiName":"",
                    "thaiSurname":"",
                }
          }],
          "dean":{
               "thaiName":"",
               "thaiSurname":"",
               "email":null,
          },
        }]
    }

    FacultyWrapperJson() {
        return {
          "academicYear":"",
          "president":{
              "thaiName":"",
              "thaiSurname":"",
          },
          "facultyList":{},
        }
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
        //this.facultyList = this.facultyListJson();
        this.facultyList = this.FacultyWrapper.facultyList;
          

        console.log("SSS !");
    }

    public chainOfCommandError(error: String) {

        console.log("GetchainOfCommandError.");
    }

    public clickShowDepartment(departID: String){

        var url = "../admin/json/getlistByDepartment/"+departID
        this.http.get(url).subscribe(response => this.GetImportSucess(response),
        error => this.GetError(error), () => console.log("getlistByDepartment !")
        );
    }

    public GetImportSucess (response:any){

        this.listByDepartments  = response.json(JSON.stringify(response._body));
        this.DepartmentName  = this.listByDepartments[0].department.name;
        this.listByDepartment  = this.listByDepartments[0].department.academicPersonList;
        // this.department = this.listByDepartment.department;
        // this.academicPersonList = this.department.academicPersonList;
        console.log("SS2 !");
    }
    public GetError(error: String) {
        console.log("GetPersonError.")

    }
}
