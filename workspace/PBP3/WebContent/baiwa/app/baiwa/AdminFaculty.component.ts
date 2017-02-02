import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';



@Component({
    templateUrl: 'app/baiwa/html/AdminFaculty.component.html'
})

export class AdminFaculty implements OnInit {
    public Faculty: any[];
    public Facultyname: any[];
    public Department: any;
    public academicYearSelect: any;
    public academicYear: any;
    public academicList: any;

    constructor(private router: Router,private http: Http) {
        this.Facultyname = this.facultylistdefult();
        this.Faculty = this.Facultyde();
        

    }
    facultylistdefult() {
        return [{
            "status": "",
            "academicYearList": [{}],
            "facultyList": [
                { name: "" }
            ]
        }]
    }
    Facultyde() {
        return [{
            name: ""
        }]

    }

    ngOnInit() {

        var year =new Date().getFullYear()+542;
        this.adminFaculty(year);

    }

    public adminFaculty(year: any) {
        //console.log(year);
        this.academicYearSelect  = year;
        var url = "../admin/json/getFaculty/"+year;
        return this.http.get(url).subscribe(response => this.adminFacultySucess(response),
            error => this.adminFacultyError(error), () => console.log("DepartmentName !"));
    }

    public adminFacultySucess(response: any) {

        this.Facultyname = response.json(JSON.stringify(response._body));
        this.Faculty = this.Facultyname[0].facultyList;
        
        this.academicYear  = this.Facultyname[0].academicYear;
        this.academicList = this.Facultyname[0].academicYearList;
        console.log("SS !");
    }

    public adminFacultyError(error: String) {

        console.log("GetadminFacultyError.")

    }

    private bntBack(){
       
    }
    public AdminFacultycreateDepartment(facultyId: String){
        this.router.navigate(['/AdminFacultycreateDepartment', facultyId]);
    }

    public AdminFacultyCreate(year: String){
        this.router.navigate(['/AdminFacultyCreate', year]);
    }

    public AdminFacultyeditFaculty(facultyId: String){
        this.router.navigate(['/AdminFacultyeditFaculty', facultyId]);
    }

    public AdminFacultyEditDepartment(departmentId: String){
        this.router.navigate(['/AdminFacultyEditDepartment', departmentId]);
    }
    public changeYear(year: String){
        this.adminFaculty(year);
        console.log(year);
    }

}