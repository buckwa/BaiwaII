import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';
declare var jQuery: any;

const URL1 = 'http://localhost:8080/PBP3/admin/json/UploadFile_Profile';
//Dev  Amuletkill !
@Component({
    templateUrl: 'app/baiwa/html/AdminUserCreate.component.html'
})
export class AdminUserCreate {

    public uploader: FileUploader = new FileUploader({ url: URL1 });
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
    public CheckPass = false;

    public model: any;
    public person: any;
    public Filename: any;
    submitted = false;

    constructor(private http: Http) {
        this.model = this.ModelUser();
        this.person = this.PersonUser();

    }
    ngOnInit() {
        this.getCreateUserJson();
        this.Datetime();
        this.uploader.onBuildItemForm = (fileItem: any, form: any) => {
            form.append('academicKPIId', "1");
        };
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
            "picture": ""
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
    }

    public GetUserJsonError(response: any) {

        console.log("AdminUserCreate :GetUserJsonError " + response);
    }


    public onSubmit() {


        this.submitted = true;

        // if (this.model.password == this.model.passwordConfirmation) {
        //     this.CheckPass = true;
        // }

        // if (this.CheckPass) {

        // }


            console.log("GetUserSubmitted :" + this.submitted);
            this.saveUser();

    }

    saverange(newValue) {


        console.log(newValue);
        //   this.range = newValue;
        //   this.Platform.ready().then(() => {
        //      this.rootRef.child("users").child(this.UserID).child('range').set(this.range)
        //   })
    }

    public saveUser() {
        //Ready 


        let temp:any[] = jQuery('input[type="checkbox"]:checked');

        for(var i =0;i<temp.length ;i++){
                // console.log("Tamp",temp[i].value);
                for(var j =0;j < this.groupList.length ;j++){
                    if( temp[i].value == this.groupList[j].groupName){
                        this.groupList[j].enable = true;

                    }
                }
        }
        this.model.groupList = this.groupList;

        var birthdateStr = jQuery("#birthdateStr").val();
        var workingDateStr = jQuery("#workingDateStr").val();
        var retireDateStr = jQuery("#retireDateStr").val();
         var assignDateStr = jQuery("#assignDateStr").val();

        this.model.person.birthdateStr = birthdateStr;
        this.model.person.workingDateStr = workingDateStr;
        this.model.person.retireDateStr = retireDateStr;
        this.model.person.assignDateStr = assignDateStr;


        console.log("AdminUserCreate : Ready SaveUser");

        this.model.person = this.person
        var url = "../admin/json/createuserSave";
        this.http.post(url, this.model).subscribe(response => this.SaveUserJsonSucess(response),
            error => this.SaveUserJsonError(error), () => console.log("AdminUserCreate : Success saveUser !"));


    }

    public SaveUserJsonSucess(response: any) {
        //Todo

        alert("บันทึกเรียบร้อย !");
        window.location.href = '#/AdminWorkUser';
        console.log("AdminUserCreate : Ready SaveUserJsonSucess !");
    }

    public SaveUserJsonError(response: any) {
        //Todo
        console.log("AdminUserCreate : Success SaveUser Error !");
    }

    public uploadFileAll() {
        this.uploader.uploadAll();
        if (this.uploader.uploadAll()) {
            console.log("uploadsucess1");
        }
        console.log("uploadsucess2");
    }
    file: File;
    onChangefile(event) {
        let eventObj: MSInputMethodContext = <MSInputMethodContext>event;
        let target: HTMLInputElement = <HTMLInputElement>eventObj.target;
        let files: FileList = target.files;
        this.file = files[0];
        //this.person.fileData =this.file;
        this.person.picture = this.file.name;

        this.Filename = this.file.name;

        console.log(this.file);
    }



    Datetime() {

        jQuery("#birthdateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            // timePicker: true,
            locale: {
                format: 'DD/MM/YYYY'
            }

        });

        jQuery("#workingDateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            // timePicker: true,
            locale: {
                format: 'DD/MM/YYYY'
            }

        });

        jQuery("#assignDateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            // timePicker: true,
            locale: {
                format: 'DD/MM/YYYY'
            }

        });

        jQuery("#retireDateStr").daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            // timePicker: true,
            locale: {
                format: 'DD/MM/YYYY'
            }

        });



    }

}