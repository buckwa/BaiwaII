import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';


@Component({
    templateUrl: 'app/baiwa/html/AdminFacultyeditFaculty.component.html'
})

export class AdminFacultyeditFaculty implements OnInit {
    public Faculty: any;
    public Facultyname: any[];
    public FacultyID: any[];
    public FacultyJson: any[];
    public model: any;
    public result: any;

    constructor(private router: Router,private http: Http, private route: ActivatedRoute) {
        this.Faculty = this.defaultFaculty();

    }

    ngOnInit() {
         this.route.params.subscribe(params => this.FacultyID = params["FacultyID"]);
        if (this.FacultyID) {
            console.log("FacultyID :", this.FacultyID);
            //this.editcar(this.para_carId);
        } 

        this.adminFacultyedit();
    }

    public defaultFaculty() {
        return {
            "name": "",
        }
    }

    public adminFacultyedit() {

        var url = "../admin/json/editFaculty/"+this.FacultyID;
        return this.http.get(url).subscribe(response => this.adminFacultyeditSucess(response),
            error => this.adminFacultyeditError(error), () => console.log("DepartmentName !"));
    }

    public adminFacultyeditSucess(response: any) {

        this.Facultyname = response.json(JSON.stringify(response._body));
        this.Faculty = this.Facultyname[0].name;
        this.model = this.Facultyname[0];

        console.log("SS !");
    }

    public adminFacultyeditError(error: String) {

        console.log("GetadminFacultyError.")

    }

    public adminSaveFacultyedit() {

        this.model.name =  this.Faculty  ;
        var url = "../admin/json/editFacultySave/";//ติดไว้ก่อน
        this.http.post(url, this.model ).subscribe(response => this.SaveFacultySJsonSucess(response),
            error => this.SaveFacultySJsonError(error), () => console.log("AdminUserCreate : Success saveUser !"));
    }

    public SaveFacultySJsonSucess(response: any) {
        
        //Todo
        //Show Status

          this.router.navigate(['/AdminFaculty']);
        this.FacultyJson = response.json(JSON.stringify(response._body));
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    }

    public SaveFacultySJsonError(response: any) {
        //Todo
        console.log("Error !");
    }


    public adminSaveFacultyDelete() {


        var url = "../admin/pbp/faculty/deleteFacultyById/"+ this.FacultyID;//ติดไว้ก่อน
        this.http.get(url).subscribe(response => this.DeleteFacultySJsonSucess(response),
            error => this.SaveFacultySJsonError(error), () => console.log("Delete: Success !"));

    }
    public DeleteFacultySJsonSucess(response: any) {


          this.router.navigate(['/AdminFaculty']);
        this.result = response.json(JSON.stringify(response._body));
        console.log("Delete: Ready SaveUserJsonSucess !");
    }
    

}