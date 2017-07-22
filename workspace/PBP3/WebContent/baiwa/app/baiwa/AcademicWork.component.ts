import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';
import {DomSanitizer} from '@angular/platform-browser';
import {Observable} from 'rxjs/Rx';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';
declare var jQuery: any;

const URL1 = '../person/importwork_file';


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
    public academicYearList: any;
    public currentAcademicYear: any;
    public profile: any;
    public evaluateRoundList: any;
    public evaluateRoundValue: any;
    public Model: any;
    public status: any;
    public result: any;
    fielPath;
    public f: File;
    chFilework: boolean;
    public replyMessage: any;

    public messageList: any[];
    public uploader: FileUploader = new FileUploader({ url: URL1 });

    constructor(private router: Router,private commonService: CommonService, private http: Http, private sanitizer: DomSanitizer) {
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
            "academicKPI": {
                "unitDesc": ""
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



        setTimeout(() => this.GetAcademicWork(this.user.userName, this.currentAcademicYear, this.evaluateRoundValue), 250);




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
        this.kpiuserList = [];
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
        var url = "../person/getImportWorkNew/" + Code
        return this.http.get(url).subscribe(response => this.GetKPISucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }
    public GetKPISucess(response: any) {
        this.Model = response.json(JSON.stringify(response._body));
        this.pointKPI = this.Model.academicKPIUserMapping;
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

        if (this.pointKPI.messageList != null && this.pointKPI.status != "APPROVED") {
            this.messageList = this.pointKPI.messageList;
        }
    }

    public mapKpi() {
        for (var i = 0; i < this.kpiuserList.length; i++) {
            this.kpival[i] = [];
            for (var j = 0; j < this.kpiuserList[i].length; j++) {

                for (var k = 0; k < this.kpiuserList[i][j].academicKPIAttributeValueList.length; k++) {

                    if(this.kpiuserList[i][j].academicKPIAttributeValueList[k].name == 'สัดส่วน(%)'){
                          var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[k].value;
                          this.kpival[i][j] = temp + "%";
                    }
                }

                // if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 2) {
                //     var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[1].value;
                //     this.kpival[i][j] = temp + "%";

                // } else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 3) {
                //     this.kpival[i][j] = "";

                // } else if (this.kpiuserList[i][j].academicKPIAttributeValueList.length == 4) {
                //     var temp = this.kpiuserList[i][j].academicKPIAttributeValueList[2].value;
                //     this.kpival[i][j] = temp + "%";

                // } else {
                //     this.kpival[i][j] = "";
                // }

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

    public GetPersonByAcadamy(user: String, year: String) {
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year
        this.http.get(url).subscribe(response => this.GetPersonSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );
    }
    public GetPersonSucess(response: any) {
        this.profile = response.json(JSON.stringify(response._body));
        this.evaluateRoundValue = this.profile.evaluateRound;

        if (this.profile.employeeType == 'ข้าราชการ') {
            this.evaluateRoundList = this.profile.evaluateRoundList;

        }

    }
    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }

    public changeYear(year: any) {

        this.GetAcademicWork(this.user.userName, year, this.evaluateRoundValue);
    }
    public changeRound() {

        this.GetAcademicWork(this.user.userName, this.currentAcademicYear, this.evaluateRoundValue);
    }


    public sentReplyPBPMessage() {
        if (this.replyMessage != null) {
            this.Model.replyMessage = this.replyMessage;
            var url = "../head/pbp/replyMessage";//ติดไว้ก่อน
            this.http.post(url, this.Model).subscribe(response => this.ReplyPBPMessageSucess(response),
                error => this.ReplyPBPMessageError(error), () => console.log("AdminUserCreate : Success saveUser !"));
        }
    }

    ReplyPBPMessageSucess(response: any) {
        var temp = response.json(JSON.stringify(response._body));
        this.ClickGetPointKPINew(this.codeKpi, this.mark);
    }
    ReplyPBPMessageError(response: any) {
        console.log("Error!");

    }


    public ClickGetPointKPINew(Code: String, indexKPI: String) {
        this.mark = indexKPI;
        var url = "../person/getImportWorkNew/" + Code
        return this.http.get(url).subscribe(response => this.GetKPISucessNew(response),
            error => this.ReplyPBPMessageError(error), () => console.log("editdoneUser !"));
    }

    public GetKPISucessNew(response: any) {
        this.Model = response.json(JSON.stringify(response._body));
        this.pointKPI = this.Model.academicKPIUserMapping;

        if (this.pointKPI.messageList != null) {
            this.messageList = this.pointKPI.messageList;
        }


    }

    public clickedDeteleImport() {

        this.commonService.confirm("Are you sure you want to delete?", jQuery.proxy(function (isOk: any) {
            if (isOk) {
                //action 
                var url = "../pam/person/deleteImportWork/" + this.codeKpi
                return this.http.get(url).subscribe(response => this.GetSSDeteleImport(response),
                    error => this.GetERRDeteleImport(error), () => console.log("editdoneUser !"));

            }
        }, this));


    }

    GetSSDeteleImport(response: any) {
        this.result = response.json(JSON.stringify(response._body));
        location.reload();
        setTimeout(() => this.GetAcademicWork(this.user.userName, this.currentAcademicYear, this.evaluateRoundValue), 250);
        

    }

    GetERRDeteleImport(response: any) {
        console.log("Error!");
    }

    clickedEditImport(){


        var keys = Object.keys(this.pointLPIList);
        var len = keys.length;
        var tamp =1;
            for(var i=0;i<len;i++) {
                
                if(this.pointLPIList[i].value == null ){
                
                    console.log("Required Now !");
                    tamp =0;
                }


                if(this.pointLPIList[i].name =='สัดส่วน(%)'){
       
                         if(this.pointLPIList[i].value > 100){
                            console.log("Number limit !");
                            tamp =0;
                         }

                }
            }
            
            if(tamp==1){
                 this.Model.academicKPIAttributeValueList = this.pointLPIList;
    var url = "../pam/person/editImportwork";
    this.http.post(url, this.Model).subscribe(response => this.EditSuccess(response),
    error => this.EditError(error), () => console.log("AdminUserCreate : Success saveUser !"));

            }




           


    }

    public EditSuccess(response: any) {
        this.result = response.json(JSON.stringify(response._body));
        
        if(this.result.status=='S001'){
             alert("Success !");
            
        setTimeout(() => this.GetAcademicWork(this.user.userName, this.currentAcademicYear, this.evaluateRoundValue), 250);

        }else{
             alert("Error !");
        }
       
    }

    public EditError(response: any) {

        alert("Error !");
       
    }

}