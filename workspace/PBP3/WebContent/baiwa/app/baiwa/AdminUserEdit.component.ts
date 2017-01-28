import { Component, OnInit, ViewChild, AfterViewInit  } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';
declare var jQuery: any;

//Dev  Amuletkill !
@Component({
    selector: 'input-controls',
    templateUrl: 'app/baiwa/html/AdminUserEdit.component.html'
})
export class AdminUserEdit {


    javascript = false;
    angular = false;
    csharp = false;


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

        this.route.params.subscribe(params => this.user = params["user"]);
        if (this.user) {
            console.log("User :", this.user);
            //this.editcar(this.para_carId);
        } else {

        }


        this.getCreateUserJson();
        this.Datetime();

    }
    ngAfterViewInit() {
        this.makeDatePicker();
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
        var url = "../admin/json/editUser/" + this.user + "/work";
        this.http.get(url).subscribe(response => this.GetUserJsonSucess(response),
            error => this.GetUserJsonError(error), () => console.log("AdminUserCreate : Success getlistByDepartment !"));
    }

    public GetUserJsonSucess(response: any) {

        console.log("AdminUserCreate : Ready GetUserJsonSucess");


        this.modelUser = response.json(JSON.stringify(response._body));
        this.modelUser = this.modelUser.resObj;

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
        console.log(this.groupList);
        //this.getEditUserJson();
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

        var url = "../admin/json/editUserSave";//ติดไว้ก่อน
        this.http.post(url, this.modelUser).subscribe(response => this.SaveUserJsonSucess(response),
            error => this.SaveUserJsonError(error), () => console.log("AdminUserCreate : Success saveUser !"));


    }

    public SaveUserJsonSucess(response: any) {
        //Todo
        //Show Status
        alert("บันทึกเรียบร้อย !");
        window.location.href = '#/AdminWorkUser';
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    }

    public SaveUserJsonError(response: any) {
        //Todo
        console.log("AdminUserCreate : Success SaveUser Error !");
    }



    public GetUserEditJsonError(response: any) {
        console.log("AdminUserCreate : Error");
    }


    private makeDatePicker() {
  

     } 


  Datetime(){
  
        jQuery("#birthdateStr").daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
            format: 'MM/DD/YYYY'
        }

        });

          jQuery("#workingDateStr").daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
            format: 'MM/DD/YYYY'
        }

        });

          jQuery("#assignDateStrU").daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
            format: 'MM/DD/YYYY'
        }

        });

          jQuery("#retireDateStrU").daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,

        locale: {
            format: 'MM/DD/YYYY'
        }

        });



  }






}