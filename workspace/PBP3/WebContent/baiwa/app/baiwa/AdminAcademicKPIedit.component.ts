import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';


@Component({
    templateUrl: 'app/baiwa/html/AdminAcademicKPIedit.component.html'
})

export class AdminAcademicKPIedit implements OnInit {


    public academicKPIId: any;
    public workTypeCode: any;
    public academicYear: any;
    public facultyCode: any;
    public CreateAtti: any;
    public model: any;
    public academicUnitList: any;
    public academicKPIAttributeList: any;
    public CreateAcademicAtti: any;
    public modelAttribute: any;

    submitted = false;

    public ModelCreate() {
        return {
            "name": "",
            "mark": "",
            "unitCode": "",
            "multiplyValue": "",
            "orderNo": "",
            "description": "",
            "specialP1": "",
            "specialP2": "",
            "specialP3": "",
            "specialP4": "",
            "specialP5": "",
            "totalStudentFrom": "",
            "totalStudentTo": "",
            "fromRegis": ""
        }
    }

    public ModelCreateAcademic() {
        return {
            "name": "",
            "isCalculate": "",
            "isValidateNumber": "",
            "mandatory": "",
        }
    }


    ngOnInit() {

        this.CreateAtti = this.ModelCreate();
        this.CreateAcademicAtti = this.ModelCreateAcademic();

        this.route.params.subscribe(params => this.academicKPIId = params["academicKPIId"]);
        if (this.academicKPIId) {
            console.log("academicKPIId :", this.academicKPIId);
            //this.editcar(this.para_carId);
        }

        this.SetUpdateKPI();
    }

    constructor(private router: Router, private http: Http, private route: ActivatedRoute) {

    }


    onSubmit() {
        this.submitted = true;
        this.SetCreateAttiToKTP();
        console.log("true");

    }

    public SetUpdateKPI() {

        var url = "../admin/pbp/academicKPI/edit/" + this.academicKPIId;
        this.http.get(url).subscribe(response => this.GetKPIJsonSucess(response),
            error => this.GetKPIJsonError(error), () => console.log("Success  !"));

    }

    public GetKPIJsonSucess(response: any) {

        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;

        this.academicUnitList = this.model.academicUnitList;
        this.academicKPIAttributeList = this.model.academicKPIAttributeList;
        //this.CreateAcademicAtti = this.academicKPIAttributeList;


        this.SetCreateKPIToAtti();
        console.log("Success GetKPIJsonSucess  !")
    }

    public GetKPIJsonError(error: any) {
        console.log("Error !")

    }

    public SetCreateKPIToAtti() {

        this.CreateAtti.name = this.model.name;
        this.CreateAtti.mark = this.model.mark;
        this.CreateAtti.unitCode = this.model.unitCode;
        this.CreateAtti.multiplyValue = this.model.multiplyValue;
        this.CreateAtti.orderNo = this.model.orderNo;
        this.CreateAtti.description = this.model.description;
        this.CreateAtti.specialP1 = this.model.specialP1;
        this.CreateAtti.specialP2 = this.model.specialP2;
        this.CreateAtti.specialP3 = this.model.specialP3;
        this.CreateAtti.specialP4 = this.model.specialP4;
        this.CreateAtti.specialP5 = this.model.specialP5;
        this.CreateAtti.totalStudentFrom = this.model.totalStudentFrom;
        this.CreateAtti.totalStudentTo = this.model.totalStudentTo;
        this.CreateAtti.fromRegis = this.model.fromRegis;


    }

    public SetCreateAttiToKTP() {

        this.model.name = this.CreateAtti.name;
        this.model.mark = this.CreateAtti.mark;
        this.model.unitCode = this.CreateAtti.unitCode;
        this.model.multiplyValue = this.CreateAtti.multiplyValue;
        this.model.orderNo = this.CreateAtti.orderNo;
        this.model.description = this.CreateAtti.description;
        this.model.specialP1 = this.CreateAtti.specialP1;
        this.model.specialP2 = this.CreateAtti.specialP2;
        this.model.specialP3 = this.CreateAtti.specialP3;
        this.model.specialP4 = this.CreateAtti.specialP4;
        this.model.specialP5 = this.CreateAtti.specialP5;
        this.model.totalStudentFrom = this.CreateAtti.totalStudentFrom;
        this.model.totalStudentTo = this.CreateAtti.totalStudentTo;
        this.model.fromRegis = this.CreateAtti.fromRegis;
        this.model.academicKPIAttributeList = this.academicKPIAttributeList;
        this.SentCreateKPI();

    }


    public SentCreateKPI() {

        var url = "../admin/pbp/academicKPI/edit";
        this.http.post(url, this.model).subscribe(response => this.CreateSuccess(response),
            error => this.GetKPIJsonError(error), () => console.log("AdminUserCreate : Success saveUser !"));
    }

    public CreateSuccess(response: any) {

        alert("Success !");
        this.router.navigate(['/AdminAcademicKPI']);
    }

    onChange(event) {
        var isChecked = event.currentTarget.checked;
        console.log(isChecked)
    }

    public addNewAttribute() {

        var url = "../admin/pbp/academicKPI/addNewAttribute.htm/" + this.academicKPIId;
        this.http.get(url).subscribe(response => this.CreateAttributeSuccess(response),
            error => this.GetKPIJsonError(error), () => console.log("AdminUserCreate : Success saveUser !"));
    }

    public deleteAttribute(academicKPIId: any, academicKPIAtributeId: any) {

        var url = "../admin/pbp/academicKPI/deleteAttribute.htm/" + academicKPIId + "/" + academicKPIAtributeId;
        this.http.get(url).subscribe(response => this.CreateAttributeSuccess(response),
            error => this.GetKPIJsonError(error), () => console.log("AdminUserCreate : Success saveUser !"));
    }

    public CreateAttributeSuccess(response: any) {
        this.modelAttribute = response.json(JSON.stringify(response._body));
        this.modelAttribute = this.modelAttribute.resObj;
        this.academicKPIAttributeList = this.modelAttribute.academicKPIAttributeList;

        location.reload();

    }

    public back() {
      this.router.navigate(['/AdminAcademicKPI']);
    }

}