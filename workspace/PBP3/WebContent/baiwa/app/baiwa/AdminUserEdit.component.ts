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
    public Faculty: any[];
    public Facultyname: any[];
    public Department: any[]=[];


    submitted = false;

    constructor(private http: Http, private route: ActivatedRoute) {
        this.model = this.ModelUser();
        this.person = this.PersonUser();

        console.log("Sub : ", this.sub);

    }


    ngOnInit() {
        this.adminFaculty();
        this.route.params.subscribe(params => this.user = params["user"]);
        if (this.user) {
            console.log("User :", this.user);
            //this.editcar(this.para_carId);
        } else {

        }



        this.Datetime();

    }
    ngAfterViewInit() {
        this.getCreateUserJson();
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
        this.person.department = this.modelUser.person.departmentDesc;
        this.person.faculty = this.modelUser.person.facultyDesc;
       
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


        setTimeout(()=>{

            for (var i = 0; i < this.modelUser.groups.length; i++) {
                for (var j = 0; j < this.groupList.length; j++) {
                    if (this.modelUser.groups[i] == this.groupList[j].groupId) {
                        let roleName = this.groupList[j].groupName;
                        console.log("Finde Roles", roleName);
                        jQuery("#" + roleName).attr("checked", true);
                        break;
                    }
                }
            }
        }, 800);


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

        let temp: any[] = jQuery('input[type="checkbox"]:checked');

        this.model.groups = [];

        for (var i = 0; i < temp.length; i++) {



            console.log('temp=', temp[i].value)
            this.model.groups.push(temp[i].value);
            console.log('groups=', this.model.groups);
        }

        var birthdateStr = jQuery("#birthdateStr").val();
        var workingDateStr = jQuery("#workingDateStr").val();


        this.model.person.birthdateStr = birthdateStr;
        this.model.person.workingDateStr = workingDateStr;



        console.log("AdminUserCreate : Ready SaveUser");

        var url = "../admin/json/editUserSave";//ติดไว้ก่อน
        this.http.post(url, this.model).subscribe(response => this.SaveUserJsonSucess(response),
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


    Datetime() {

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




    public adminFaculty() {
        //console.log(year);
        var year =new Date().getFullYear()+542;
        var url = "../admin/json/getFaculty/"+year;
        return this.http.get(url).subscribe(response => this.adminFacultySucess(response),
            error => this.adminFacultyError(error), () => console.log("DepartmentName !"));
    }

    public adminFacultySucess(response: any) {

        this.Facultyname = response.json(JSON.stringify(response._body));
        this.Faculty = this.Facultyname[0].facultyList;


        this.setdepartment();

        console.log("SS !");
    }

    public adminFacultyError(error: String) {

        console.log("GetadminFacultyError.")

    }

    public Adddepartment(faculty: String){


        for(var i=0;i <this.Faculty.length;i++){
            console.log("this.Faculty[i].name ",this.Faculty[i].name);
            console.log("this.person.faculty ",faculty);
                if(this.Faculty[i].name == faculty){
                   this.Department = this.Faculty[i].departmentList;
                   break;
                }
        }
    }


    public setdepartment(){


        for(var i=0;i <=this.Faculty.length;i++){
            console.log("this.Faculty[i].name ",this.Faculty[i].name);
            console.log("this.person.faculty ", this.person.faculty);
                if(this.Faculty[i].name ==  this.person.faculty){
                   this.Department = this.Faculty[i].departmentList;
                   break;
                }
        }
    }



}