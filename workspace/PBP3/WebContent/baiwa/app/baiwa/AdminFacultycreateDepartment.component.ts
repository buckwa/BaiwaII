import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';




@Component({
    templateUrl: 'app/baiwa/html/AdminFacultycreateDepartment.component.html'
})

export class AdminFacultycreateDepartment implements OnInit {

    public Faculty_id: any;
    public Department: any;
    public model: any;
    public resObj: any;
    public name: any;
    public result:any;
    public year: any;
    public academicYear: any;
    constructor(private http: Http, private route: ActivatedRoute) {


    }

    ngOnInit() {

        this.route.params.subscribe(params => this.Faculty_id = params["Faculty_id"]);
        if (this.Faculty_id) {
            console.log("Faculty_id :", this.Faculty_id);
            //this.editcar(this.para_carId);

        }
        this.adminFaculty();
    }

    public adminFaculty() {

        var url = "../admin/json/createDepartmentGet/"+this.Faculty_id;
        return this.http.get(url).subscribe(response => this.adminFacultySucess(response),
            error => this.adminFacultyError(error), () => console.log("DepartmentName !"));
    }

    public adminFacultySucess(response: any) {

        this.model = response.json(JSON.stringify(response._body));

        this.resObj = this.model.resObj;
        this.name   = this.resObj.name;
        this.academicYear =  this.resObj.academicYear;
        this.Department = this.resObj.department.name;

        console.log("Succuss !");

    }

    public adminFacultyError(error: String) {

        console.log("Error.")

    }

    public adminSaveFacultyedit() {

        this.resObj.department.name =this.Department 
        this.resObj.name  = this.name  ; 
        var url = "../admin/json/createDepartmentPost";//ติดไว้ก่อน
        this.http.post(url, this.resObj ).subscribe(response => this.SaveFacultySJsonSucess(response),
            error => this.adminFacultyError(error), () => console.log("AdminUserCreate : Success saveUser !"));
    }

    public SaveFacultySJsonSucess(response: any) {
        
        //Todo
        //Show Status
        window.location.href = '#/AdminFaculty';
        this.result = response.json(JSON.stringify(response._body));
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    }




}