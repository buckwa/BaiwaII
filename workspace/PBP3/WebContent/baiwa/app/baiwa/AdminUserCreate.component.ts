import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';

//Dev  Amuletkill !
@Component({
    templateUrl: 'app/baiwa/html/AdminUserCreate.component.html'
})
export class AdminUserCreate {


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
    submitted = false;
    onSubmit() { 
        
        this.submitted = true; 
        console.log("GetUserSubmitted :" + this.submitted );

    }

    // TODO: Remove this when we're done
    get diagnostic() {
        return JSON.stringify(this.person());
    }


    constructor(private http: Http) {
        this.model();
        this.person();

    }
    ngOnInit() {
        this.getCreateUserJson();

    }
    ngAfterViewInit() {

    }

    public model() {
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

    public person() {
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

        console.log("GetUserJsonSucess !");
    }

    public GetUserJsonError(response: any) {

        console.log("GetUserJsonError :" + response);
    }

}