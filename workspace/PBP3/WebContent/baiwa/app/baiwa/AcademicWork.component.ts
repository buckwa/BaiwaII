import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';

const URL = 'http://localhost:8080/PBP3/person/uploadMultiFile';


@Component({
    templateUrl: 'app/baiwa/html/AcademicWork.component.html'
})
export class AcademicWork implements OnInit, AfterViewInit {
    public user: any;
    public academy: any;
    public academyList: any[];
    public kpiuserList: any[] = [];
    public pointKPI: any;
    public mark: String;
    public pointLPIList: any[];
    public kpival: any[][];

    public uploader: FileUploader = new FileUploader({ url: URL });

    constructor(private commonService: CommonService, private http: Http) {
        this.academy = this.setdefualtkpi();
        this.kpiuserList = [];
        this.kpival = [];
        this.pointKPI = this.setdefualtpoitkpi();


    }
    setdefualtkpi() {
        return {
            "academicYear": "",
            "totalInMapping": "",
            "calResultStr": "",
            "pBPWorkTypeList": [{
                "name": "",
                "totalInWorkType": "",
                "academicKPIUserMappingList": [{}]
            }]
        }
    }
    setdefualtpoitkpi() {
        return {
            "name": "",
            "kpiUserMappingId": "",
            "calResultStr": "",
            "academicKPIAttributeValueList": [{}]
        }
    }

    ngOnInit() {
        this.GetUserSession();
        
        this.uploader.onBuildItemForm = (fileItem: any, form: any) => {
            form.append('data', '2');
        };

    }
    ngAfterViewInit() {

    }

    public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetUserSessionSucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }
    public GetUserSessionSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.GetAcademicWork(this.user.userName, this.user.currentAcademicYear, "1");

    }
    public GetUserSessionError(error: String) {
        console.log("GetPersonError.")

    }

    public GetAcademicWork(user: String, year: String, round: String) {
        this.commonService.loading();
        var url = "../person/getAcademicWork/" + user + "/" + year + "/" + round
        return this.http.get(url).subscribe(response => this.GetUserAcademicSucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));

    }
    public GetUserAcademicSucess(response: any) {
        this.academy = response.json(JSON.stringify(response._body));
        this.academyList = this.academy.pBPWorkTypeList;
        //this.kpiuserList =this.academy.pBPWorkTypeList.academicKPIUserMappingList;
        for (var i = 0; i < this.academy.pBPWorkTypeList.length; i++) {
            this.kpiuserList.push(this.academy.pBPWorkTypeList[i].academicKPIUserMappingList)
        }
        this.commonService.unLoading();
        this.mapKpi();
    }
    public ClickGetPointKPI(Code: String, mark: String) {
        this.mark = mark;
        var url = "../person/getImportWork/" + Code
        return this.http.get(url).subscribe(response => this.GetKPISucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }
    public GetKPISucess(response: any) {
        this.pointKPI = response.json(JSON.stringify(response._body));
        this.pointLPIList = this.pointKPI.academicKPIAttributeValueList;

    }

    public mapKpi() {
        for (var i = 0; i < this.kpiuserList.length; i++) {
            this.kpival[i] = [];
            for (var j = 0; j < this.kpiuserList[i].length; j++) {
                if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 2) {
                    var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[1].value ;
                    this.kpival[i][j] = temp+"%";

                } else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 3) {
                    this.kpival[i][j] = "";

                } else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 4) {
                    var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[2].value ;
                    this.kpival[i][j] = temp + "%";

                } else {
                    this.kpival[i][j] = "";
                }

            }

        }


    }


}