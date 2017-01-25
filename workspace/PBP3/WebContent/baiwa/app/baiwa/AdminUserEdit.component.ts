import { Component, OnInit, ViewChild, AfterViewInit  } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';
//Dev  Amuletkill !
@Component({
    templateUrl: 'app/baiwa/html/AdminUserEdit.component.html'
})
export class AdminUserEdit {


    public modelUser: any;
    public groupList: any;
    public lovSexList: any;
    public lovEmployeeTypeList: any;
    public lovPositionList: any;
    public lovWorkLineList: any;
    public lovFacultyList: any;
    public lovInsigniaList: any;
    public lovMarriedStatusList: any;
    public lovEducationList: any;
    public lovWorkingStatusList: any;
    public evaluateRoundList: any;
    public tampvalue: any;
    public sub: any;
    public model: any;
    public person: any;
    public user: any;
    public modelEdit: any;
    submitted = false;

    constructor(private http: Http, private route: ActivatedRoute) {
        this.model = this.ModelUser();
        this.person = this.PersonUser();

        console.log("Sub : ", this.sub);

    }
    ngOnInit() {
        this.getCreateUserJson();
        this.route.params.subscribe(params => this.user = params["user"]);
        if (this.user) {
            console.log("User :", this.user);
            //this.editcar(this.para_carId);
        } else {

        }

    }
    ngAfterViewInit() {

    }

    public ModelUser() {
        return {
            "username": "",
            "password": "",
            "passwordConfirmation": "",
            "enabled": "",
            "groups": "",
            "person": {

            }
        }
    }

    public PersonUser() {
        return {

            "thaiName": "",
            "thaiSurname": "",
            "sex": "",
            "birthdate": "",
            "rateNo": "",
            "employeeType": "",
            "position": "",
            "level": "",
            "workLine": "",
            "salary": "",
            "workTelNo": "",
            "belongTo": "",
            "faculty": "",
            "workingDate": "",
            "assignDate": "",
            "retireDate": "",
            "maxInsignia": "",
            "maxEducation": "",
            "taxNo": "",
            "workingStatus": "",
            "marriedStatus": "",
            "workNumber": "",
            "insureNo": "",
            "fund": "",
            "address": "",
            "zipCode": "",
            "telNo": "",
            "email": "",
            "oldWorklineCode": "",
            "worklineCode": "",
            "worklineName": "",
        }
    }

    public getCreateUserJson() {
        console.log("AdminUserCreate : Ready getlistByDepartment ");
        var url = "../admin/json/createuser";
        this.http.get(url).subscribe(response => this.GetUserJsonSucess(response),
            error => this.GetUserJsonError(error), () => console.log("AdminUserCreate : Success getlistByDepartment !"));
    }

    public GetUserJsonSucess(response: any) {

        console.log("AdminUserCreate : Ready GetUserJsonSucess");


        this.modelUser = response.json(JSON.stringify(response._body));
        this.modelUser = this.modelUser[0];

        this.model = this.modelUser;
        this.person = this.modelUser.person;

        this.groupList = this.modelUser.groupList;
        this.lovSexList = this.modelUser.person.lovSexList;
        this.lovEmployeeTypeList = this.modelUser.person.lovEmployeeTypeList;
        this.lovPositionList = this.modelUser.person.lovPositionList;
        this.lovWorkLineList = this.modelUser.person.lovWorkLineList;
        this.lovFacultyList = this.modelUser.person.lovFacultyList;
        this.lovInsigniaList = this.modelUser.person.lovInsigniaList;
        this.lovMarriedStatusList = this.modelUser.person.lovMarriedStatusList;
        this.lovEducationList = this.modelUser.person.lovEducationList;
        this.lovWorkingStatusList = this.modelUser.person.lovWorkingStatusList;
        this.evaluateRoundList = this.modelUser.person.evaluateRoundList;

        console.log("AdminUserCreate :GetUserJsonSucess !");
        this.getEditUserJson();
    }


    public GetUserJsonError(response: any) {

        console.log("AdminUserCreate :GetUserJsonError " + response);
    }


    public onSubmit() {

        this.submitted = true;
        console.log("GetUserSubmitted :" + this.submitted);
        this.saveUser();

    }

    public saveUser() {
        //Ready 

        console.log("AdminUserCreate : Ready SaveUser");

        var url = "../admin/json/createuserSave";//ติดไว้ก่อน
        this.http.post(url, this.model).subscribe(response => this.SaveUserJsonSucess(response),
            error => this.SaveUserJsonError(error), () => console.log("AdminUserCreate : Success saveUser !"));


    }

    public SaveUserJsonSucess(response: any) {
        //Todo
        //Show Status
        window.location.href = 'http://www.google.com/';
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    }

    public SaveUserJsonError(response: any) {
        //Todo
        console.log("AdminUserCreate : Success SaveUser Error !");
    }

    public getEditUserJson() {
        console.log("AdminUserCreate : Ready getlistByDepartment ");
        var url = "../admin/json/editUser/" + this.user;
        this.http.get(url).subscribe(response => this.GetUserEditJsonSucess(response),
            error => this.GetUserEditJsonError(error), () => console.log("AdminUserCreate : Success getlistByDepartment !"));

    }

    public GetUserEditJsonSucess(response: any) {

        console.log("AdminUserCreate : Ready GetUserJsonSucess");
        this.modelEdit = response.json(JSON.stringify(response._body));

    }

    public GetUserEditJsonError(response: any) {
        console.log("AdminUserCreate : Error");
    }

}