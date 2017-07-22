import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';




@Component({
    templateUrl: 'app/baiwa/html/AdminFacultyEditDepartment.component.html'
})

export class AdminFacultyEditDepartment implements OnInit {

    public Faculty_id: any;
    public Department: any;
    public model: any;
    public resObj: any;
    public name: any;
    public result: any;
    public year: any;
    public academicYear: any;
    public Depart_Id: any;
    constructor(private http: Http, private route: ActivatedRoute, private router: Router) {


    }

    ngOnInit() {

        this.route.params.subscribe(params => this.Depart_Id = params["Depart_Id"]);
        if (this.Depart_Id) {
            console.log("Depart_Id :", this.Depart_Id);
            //this.editcar(this.para_carId);

        }
        this.adminFaculty();
    }

    public adminFaculty() {
        // admin/json//
        var url = "../admin/json/editDepartmentJson/" + this.Depart_Id;//987
        return this.http.get(url).subscribe(response => this.adminFacultySucess(response),
            error => this.adminFacultyError(error), () => console.log("DepartmentName !"));
    }

    public adminFacultySucess(response: any) {

        this.model = response.json(JSON.stringify(response._body));

        this.resObj = this.model.resObj;
        this.name = this.resObj.name;
        this.academicYear = this.resObj.academicYear;
        this.Department = this.resObj.department.name;

        console.log("Succuss !");

    }

    public adminFacultyError(error: String) {

        console.log("Error.")

    }

    public adminSaveFacultyedit() {

        this.resObj.name = this.name;

        var url = "../admin/json/editDepartmentJsonSave";//ติดไว้ก่อน
        this.http.post(url, this.resObj).subscribe(response => this.SaveFacultySJsonSucess(response),
            error => this.adminFacultyError(error), () => console.log("AdminUserCreate : Success saveUser !"));
    }

    public SaveFacultySJsonSucess(response: any) {

        //Todo
        //Show Status
        //window.location.href = '#/AdminFaculty';
        this.router.navigate(['/AdminFaculty']);
        this.result = response.json(JSON.stringify(response._body));
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    }

    public adminSaveFacultyDelete() {

        this.resObj.name = this.name;

        var url = "../admin/pbp/faculty/deleteDepartmentById/"+ this.Depart_Id;//ติดไว้ก่อน
        this.http.get(url, this.resObj).subscribe(response => this.DeleteFacultySJsonSucess(response),
            error => this.adminFacultyError(error), () => console.log("Delete: Success !"));

    }
    public DeleteFacultySJsonSucess(response: any) {

        
        this.router.navigate(['/AdminFaculty']);
        this.result = response.json(JSON.stringify(response._body));
        console.log("Delete: Ready SaveUserJsonSucess !");
    }


}