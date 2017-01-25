import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';


@Component({
    templateUrl: 'app/baiwa/html/AdminFacultyCreate.component.html'
})

export class AdminFacultyCreate implements OnInit {
    public Faculty: any;
    public Department: any;
    public model: any;
    public resObj: any;
    public name: any;
    public result:any;
    public year: any;
    constructor(private http: Http, private route: ActivatedRoute) {


    }

    ngOnInit() {

        this.route.params.subscribe(params => this.year = params["year"]);
        if (this.year) {
            console.log("FacultyID :", this.year);
            //this.editcar(this.para_carId);
            
        }



        this.adminFaculty();

    }

    public adminFaculty() {

        var url = "../admin/json/createFaculty/"+this.year;
        return this.http.get(url).subscribe(response => this.adminFacultySucess(response),
            error => this.adminFacultyError(error), () => console.log("DepartmentName !"));
    }

    public adminFacultySucess(response: any) {

        this.model  = response.json(JSON.stringify(response._body));
        this.resObj = this.model.resObj;
        this.name   = this.resObj.name;

        console.log("Succuss !");
    }

    public adminFacultyError(error: String) {

        console.log("Error.")

    }

     public adminSaveFacultyedit() {
        this.resObj.name  = this.name  ; 
        var url = "../admin/json/createFaculty";//ติดไว้ก่อน
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