import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';



@Component({
    templateUrl: 'app/baiwa/html/AdminChainOfCommandinit.component.html'
})

export class AdminChainOfCommandinit implements OnInit {
    public FacultyWrappers: any;
    public FacultyWrapper: any;
    public academicYearSelect: any;
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
    public academicList: any;
    constructor(private http: Http,private router: Router) {
       
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
       

        var year =new Date().getFullYear()+542;
        this.chainOfCommand(year);
       
    }


    public chainOfCommand(year:any) {

        this.academicYear  = year;
        var url = "../admin/json/getFacultyWrapper/"+year;
        return this.http.get(url).subscribe(response => this.chainOfCommandSucess(response),
            error => this.chainOfCommandError(error), () => console.log("DepartmentName !"));
    }


    public chainOfCommandSucess(response: any) {

        this.FacultyWrappers = response.json(JSON.stringify(response._body));
        this.FacultyWrapper = this.FacultyWrappers[0];
         this.academicList = this.FacultyWrappers[0].academicYearList;
        this.academicYearSelect = this.FacultyWrapper.academicYearSelect;
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

    public AdminChainOfCommandinitPresident(){
        console.log("result : SS")
        this.router.navigate(['/AdminChainOfCommandinitPresident']);
    }

    public AdminChainOfCommandinitDean(result: String){
        console.log("result :",result)
        this.router.navigate(['/AdminChainOfCommandinitDean', result]);
    }
    
    public AdminChainOfCommandinitHead(result: String){
          console.log("result :",result)
          this.router.navigate(['/AdminChainOfCommandinitHead', result]);
    }

    public changeYear(year: String){
        this.chainOfCommand(year);
        console.log(year);
    }

}
