import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';


@Component({
    templateUrl: 'app/baiwa/html/listworktype.component.html'
})
export class listworktype implements OnInit, AfterViewInit {

    public user:any;
    public libPath: string;
    public Inport: any;

    public inport0: any;
    public inport1: any;
    public inport2: any;
    public inport3: any[];
    public inport4: any[];


    constructor(private commonService: CommonService, private http: Http) {
        this.libPath = "/PBP3/baiwa/libs/";
        this.Inport = this.defaultInport();
        //this.inport0 = this.jsondefualt();

    }
    ngOnInit() {

       this.GetUserSession();



    }
    ngAfterViewInit() {

    }


    public defaultInport() {
        return {
            "status": "",
            "rownum": "",
            "createDate": "",
            "updateDate": "",
            "createBy": "",
            "updateBy": "",
            "name": "",
            "code": "",
            "description": "",
            "academicKPIId": "",
            "academicRuleId": "",
            "academicYear": "",
            "workTypeCode": "",
            "mark": "",
            "unitCode": "",
            "unitDesc": "",
            "facultyCode": "",
            "specialP1": "",
            "specialP2": "",
            "specialP3": "",
            "specialP4": "",
            "specialP5": "",
            "totalStudentFrom": "",
            "totalStudentTo": "",
            "multiplyValue": "",
            "academicUnitList": "",
            "academicKPIAttributeList": "",
            "fileData": "",
            "orderNo": "",
            "fromRegis": "",
            "errorDesc": "",
            "academicKPIUserMappingId": "",
            "replyMessage": "",
            "ratio": "",
            "remark": "",
            "tmpFileNameList": "",
            "index": "",
            "createDateTimeStr": "",
            "updateDateTimeStr": "",
            "createDateStr": "",
            "updateDateStr":"",

        };
    }

    public jsondefualt(){
        return {
            "workTypeName":"",
            "academicKPIList":{}
    }
    }

  

    public GetDataInport(facultyCode: String , currentAcademicYear:String) {
        var url = "../person/getAllWorkList/" + currentAcademicYear + "/"+facultyCode
        this.http.get(url).subscribe(response => this.GetSucess(response),
            error => this.GetError(error), () => console.log("editdoneInportt !")
        );

    }

    public GetSucess(response: any) {

        this.Inport = response.json(JSON.stringify(response._body));

        this.inport0  =this.Inport[0].academicKPIList;


    }

    public GetError(error: String) {
        console.log("GetPersonError.")

    }

    public GetUserSession(){
        var url ="../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetUserSessionSucess(response),
        error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }
    public GetUserSessionSucess(response:any){
        this.user =response.json(JSON.stringify(response._body));

        this.GetDataInport(this.user.facultyCode,this.user.currentAcademicYear);

        
    }
    public GetUserSessionError (error :String){
        console.log("GetPersonError.")

    }

}