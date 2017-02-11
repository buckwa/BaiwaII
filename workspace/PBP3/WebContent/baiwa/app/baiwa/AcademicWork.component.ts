import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';
import {DomSanitizer} from '@angular/platform-browser';
import {Observable} from 'rxjs/Rx';
declare var jQuery: any;

const URL1 = 'http://localhost:8080/PBP3/person/importwork_file';


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
    public statusKpi: boolean;
    public fileWork: any[];
    public tmpUrl: any;
    public codeKpi: string;
    public academicYearList :any;
    public currentAcademicYear :any;
    public profile:any;
    fielPath;
    public f: File;
    chFilework: boolean;

    public uploader: FileUploader = new FileUploader({ url: URL1 });

    constructor(private commonService: CommonService, private http: Http, private sanitizer: DomSanitizer) {
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
            "academicKPI":{
                "unitDesc":""
            },
            "academicKPIAttributeValueList": [{}]
        }
    }


    ngOnInit() {
        this.GetUserSession();

        this.uploader.onBuildItemForm = (fileItem: any, form: any) => {
            form.append('academicKPIId', this.codeKpi);
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
        this.academicYearList = this.user.academicYearList;
        this.currentAcademicYear = this.user.currentAcademicYear;

        this.GetPersonByAcadamy(this.user.userName, this.user.currentAcademicYear);
         setTimeout(() => this.GetAcademicWork(this.user.userName, this.currentAcademicYear, this.profile.evaluateRound), 250);


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
        this.kpiuserList =[];
        for (var i = 0; i < this.academy.pBPWorkTypeList.length; i++) {
            this.kpiuserList.push(this.academy.pBPWorkTypeList[i].academicKPIUserMappingList)
        }
        this.commonService.unLoading();
        this.mapKpi();
    }
    public ClickGetPointKPI(Code: string, mark: String) {
        this.uploader.clearQueue();
        this.mark = mark;
        this.codeKpi = Code;
        var url = "../person/getImportWork/" + Code
        return this.http.get(url).subscribe(response => this.GetKPISucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }
    public GetKPISucess(response: any) {
        this.pointKPI = response.json(JSON.stringify(response._body));
        this.pointLPIList = this.pointKPI.academicKPIAttributeValueList;
        this.fileWork = this.pointKPI.academicKPIAttachFileList;
        if (this.fileWork.length == 0) {
            this.chFilework = true;
        } else {
            this.chFilework = false;
        }

        // FileUploader.prototype.addToQueue(this.f,null,null);
        if (this.pointKPI.status == "APPROVED") {
            this.statusKpi = true;
        } else {
            this.statusKpi = false;
        }


    }

    public mapKpi() {
        for (var i = 0; i < this.kpiuserList.length; i++) {
            this.kpival[i] = [];
            for (var j = 0; j < this.kpiuserList[i].length; j++) {
                if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 2) {
                    var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[1].value;
                    this.kpival[i][j] = temp + "%";

                } else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 3) {
                    this.kpival[i][j] = "";

                } else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 4) {
                    var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[2].value;
                    this.kpival[i][j] = temp + "%";

                } else {
                    this.kpival[i][j] = "";
                }

            }

        }


    }


    getImage(url: string) {
        return Observable.create(observer => {
            let req = new XMLHttpRequest();
            req.open('get', url);
            req.responseType = "arraybuffer";
            req.onreadystatechange = function () {
                if (req.readyState == 4 && req.status == 200) {
                    observer.next(req.response);
                    observer.complete();
                }
            };
            req.send();
        });
    }

    public getFile(KpiID: String) {
        // var data = {'profileImg' : profileImg}
        let url = "../person/getAcademicWork_File/" + KpiID;

        this.getImage(url).subscribe(imageData => {
            this.f = imageData;

            console.log("imageReturn :" + imageData.length);

            //var blob: Blob = new Blob(imageData, JSON.stringify('_body'));
            this.tmpUrl = URL.createObjectURL(new Blob([imageData]));
            this.fielPath = this.sanitize(this.tmpUrl);
            //this.f = new File(new Blob([imageData]),"name.txt",{type: "image/png"});
            //FileUploader.addToQueue();
            console.log("file");
        });


        // the below will throw not implemented error
        this.http.get(url).subscribe(image => {
            console.log("imageUrl :" + image.url);
            console.log(image.arrayBuffer());
        });
    }

    public sanitize(url: string) {
        return this.sanitizer.bypassSecurityTrustUrl(url);
    }
    public uploadFileAll() {
        this.uploader.uploadAll();

        window.setTimeout(() => {
            var temp = !this.uploader.getNotUploadedItems().length;
            this.ClickGetPointKPI(this.codeKpi, this.mark);
            console.log("status upload :" + temp)
            this.uploader.clearQueue();
        }, 600);


    }
    public deleteFile(attachFileId: any, fileName: string) {
        var url = "../person/deleteAttachFile/" + this.codeKpi + "/" + fileName + "/" + attachFileId;
        this.commonService.confirm("คุณต้องการลบเอกสารแบบใช่หรื่อไม่?", jQuery.proxy(function (isOk) {
            console.log("isOk", isOk);
            if (isOk) {
                this.http.get(url).subscribe(response => this.deletesucess(response),
            error => this.deleteError(), () => console.log("editdoneUser !"));
            } 
        }, this));
    }
        
    public deletesucess(response: any) {

        console.log("deletesucess!")
        this.ClickGetPointKPI(this.codeKpi, this.mark);
    }
    public deleteError() {
        console.log("deleteError!")
    }

     public GetPersonByAcadamy(user: String,  year: String) {
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year
        this.http.get(url).subscribe(response => this.GetPersonSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );
    }
    public GetPersonSucess(response: any) {
        this.profile = response.json(JSON.stringify(response._body));

    }
    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }

    public changeYear(year: any){

        this.GetAcademicWork(this.user.userName, year, this.profile.evaluateRound);
    }


}