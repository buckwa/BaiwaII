import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';


@Component({
    templateUrl: 'app/baiwa/html/AdminAcademicKPIcreate.component.html'
})

export class AdminAcademicKPIcreate implements OnInit {

    public workTypeCode: any;
    public academicYear: any;
    public facultyCode: any;
    public CreateAtti: any;
    public model: any;
    public academicUnitList: any;
    submitted = false;

    public ModelCreate() {
        return {
            "name": "",
            "mark": "",
            "unitCode": "",
            "multiplyValue": "",
            "orderNo": "",
            "description": "",
        }
    }


    ngOnInit() {
        this.CreateAtti = this.ModelCreate();

        this.route.params.subscribe(params => this.workTypeCode = params["workTypeCode"]);
        if (this.workTypeCode) {
            console.log("workTypeCode :", this.workTypeCode);
            //this.editcar(this.para_carId);
        }
        this.route.params.subscribe(params => this.academicYear = params["academicYear"]);
        if (this.academicYear) {
            console.log("academicYear :", this.academicYear);
            //this.editcar(this.para_carId);
        }
        this.route.params.subscribe(params => this.facultyCode = params["facultyCode"]);
        if (this.facultyCode) {
            console.log("facultyCode :", this.facultyCode);
            //this.editcar(this.para_carId);
        }

        this.getCreateKPIJson();

    }
    constructor(private router: Router,private http: Http, private route: ActivatedRoute) {

        

    }
    onSubmit() {
        this.submitted = true;

        this.SetCreateKPI();

        console.log("true");

    }

    public getCreateKPIJson() {

        var url = "../admin/pbp/academicKPI/create.htm/" + this.workTypeCode + "/" + this.academicYear + "/" + this.facultyCode;
        this.http.get(url).subscribe(response => this.GetKPIJsonSucess(response),
            error => this.GetKPIJsonError(error), () => console.log("Success  !"));

    }

    public GetKPIJsonSucess(response: any) {

        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;

        this.academicUnitList = this.model.academicUnitList;
        console.log("Success GetKPIJsonSucess  !")
    }

    public GetKPIJsonError(error: any) {
        console.log("Error !")

    }

    public SetCreateKPI() {

        this.model.name = this.CreateAtti.name;
        this.model.mark = this.CreateAtti.mark;
        this.model.unitCode = this.CreateAtti.unitCode;
        this.model.multiplyValue = this.CreateAtti.multiplyValue;
        this.model.orderNo = this.CreateAtti.orderNo;
        this.model.description = this.CreateAtti.description;
        this.SentCreateKPI();

    }

    public SentCreateKPI() {

        var url = "../admin/pbp/academicKPI/create";
        this.http.post(url, this.model).subscribe(response => this.CreateSuccess(response),
            error => this.GetKPIJsonError(error), () => console.log("AdminUserCreate : Success saveUser !"));
    }

    public CreateSuccess(response:any) {

        alert("Success !");
        this.router.navigate(['/AdminAcademicKPI']);
    }

}