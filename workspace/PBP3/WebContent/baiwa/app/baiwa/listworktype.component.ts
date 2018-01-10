import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';
declare var jQuery: any;
const URL = '../person/importwork_file';

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
   
    public mark: String;
    public codeKpi: string;
    public inport5: any;
    public FormAddInput: any;
    public inport_sento: any;
    public academicKPIId: any;
    public uploader:FileUploader = new FileUploader({url: URL});
    public hasBaseDropZoneOver:boolean = false;
    public hasAnotherDropZoneOver:boolean = false;
    public savealert: boolean  = false;
    public uploadalert: boolean  = false;
    public statusActiveUpload: boolean = false;
    public validType:boolean;
    public valid: boolean = true;
    public currentAcademicYear :any;
    public profile :any;
    public evaluateRoundValue :any;
    public fileWork: any[];


    constructor(private commonService: CommonService, private http: Http) {
        this.libPath = "/PBP3/baiwa/libs/";
        this.Inport = this.defaultInport();
        this.inport5 = this.defaultImport();
        this.FormAddInput =this.defaultFormAddInput();
    }
    ngOnInit() {

       this.GetUserSession();

       this.uploader.onBuildItemForm = (fileItem: any, form: any) => {
        form.append(  'academicKPIId', this.academicKPIId  );
        };
        //this.uploadalert =true;


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

        

   public defaultImport() {
        return {       
            "name": "",
            "code": "",
            "mark": "",
            "unitDesc": "",          
        };
    }

   public defaultFormAddInput() {
        return [{       
            "name": "",
            "code": "",
            "description": "",
            "academicKPIAtributeId": "",    
            "academicKPIId": "",   
            "academicKPICode": "",  
            "value": "",   
            "isCalculate": "",   
            "academicYear": "",   
            "ratio": "",   
            "isValidateNumber": ""  
        }];
    }


    public jsondefualt(){
        return {
            "workTypeName":"",
            "academicKPIList":"",
    }
    }

  

    public GetDataInport(facultyCode: String , currentAcademicYear:String) {
        
        var url = "../person/getAllWorkList/" + currentAcademicYear + "/"+facultyCode
        this.http.get(url).subscribe(response => this.GetSucess(response),
            error => this.GetError(error), () => console.log("editdoneInportt !")
        );

    }

    public uploadFileAll() {
        this.uploader.uploadAll();

        
        this.uploader.onCompleteItem = (item:any, response:any, status:any, headers:any) => {
            console.log("ImageUpload:uploaded:", item, status);
           
            this.ClickGetPointKPI(this.academicKPIId, this.mark);
             this.uploadalert = true;
        };
    }



    public GetSucess(response: any) {

        this.Inport = response.json(JSON.stringify(response._body));

        this.inport0  =this.Inport[0].academicKPIList;
        this.inport1  =this.Inport[1].academicKPIList;
        this.inport2  =this.Inport[2].academicKPIList;
        this.inport3  =this.Inport[3].academicKPIList;
        this.inport4  =this.Inport[4].academicKPIList;
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
        this.currentAcademicYear =this.user.currentAcademicYear;
        this.GetPersonByAcadamy(this.user.userName, this.currentAcademicYear);
        

        
    }

    public GetPersonByAcadamy(user: String, year: String) {
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year
        this.http.get(url).subscribe(response => this.GetPersonSucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdone !")
        );
    }


    public GetPersonSucess(response: any) {
        this.profile = response.json(JSON.stringify(response._body));

        this.evaluateRoundValue = this.profile.employeeTypeNo;

        this.GetDataInport(this.user.facultyCode,this.user.currentAcademicYear);
    }
    public GetUserSessionError (error :String){
        console.log("GetPersonError.")

    }

    public clickedAddImport(academicKPICode: String){


        var url = "../person/getAcademicKPI/" + academicKPICode + "/"+this.user.facultyCode+ "/"+this.user.currentAcademicYear
        this.http.get(url).subscribe(response => this.GetImportSucess(response),
            error => this.GetError(error), () => console.log("editdoneImportt2 !")
        );

        
    }

    public GetImportSucess (response:any){

        this.inport5  =response.json(JSON.stringify(response._body));
        this.FormAddInput = this.inport5.academicKPIAttributeList;




    }

    public clickedSaveImport(){

        var keys = Object.keys(this.FormAddInput);
        var len = keys.length;
        var tamp =1;
            for(var i=0;i<len;i++) {
                
                if(this.FormAddInput[i].value == null ){
                
                    console.log("Required Now !");
                    tamp =0;
                }


                if(this.FormAddInput[i].name =='สัดส่วน(%)'){
       
                         if(this.FormAddInput[i].value > 100){
                            console.log("Number limit !");
                            tamp =0;
                         }

                }
            }
            
            if(tamp==1){
                var url = "../person/importwork"
                this.http.post(url,this.inport5).subscribe(response => this.savesucess(response),
                error => this.GetError(error), () => console.log("save sucess na !"));

            }



    }

    public savesucess (response :any){

            this.academicKPIId = response.json(JSON.stringify(response._body));
            
            console.log( this.academicKPIId);
            
            //  if(this.academicKPIId.resObj=="0"){
            //      alert("ไม่สามารถ บันทึกรายการได้ กรุณาติดต่อผู้ดูแลระบบ");
            //  }else{}
                 this.academicKPIId = this.academicKPIId.resObj;
                this.statusActiveUpload = true;
                this.savealert = true;
                this.valid = false;
                this.ClickGetPointKPI(this.academicKPIId, this.mark);
       


           
    }

    public exitModal (){

             this.statusActiveUpload =  false;
             this.uploader.clearQueue();
            this.uploadalert = false;
            this.savealert = false;
            this.valid = true;

    }


    public ClickGetPointKPI(Code: string, mark: String) {
  
        
        this.academicKPIId = Code;
        console.log("../person/getImportWorkNew/" + Code);
        var url = "../person/getImportWorkNew/" + Code
        return this.http.get(url).subscribe(response => this.GetKPISucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }

        public GetKPISucess(response: any) {
            
        var Model = response.json(JSON.stringify(response._body));
        console.log(Model);
        this.fileWork = Model.academicKPIUserMapping.academicKPIAttachFileList;
       
      
    }
    
    public deleteFile(attachFileId: any, fileName: string) {
        console.log(this.academicKPIId );
        var url = "../person/deleteAttachFile/" + this.academicKPIId + "/" + fileName + "/" + attachFileId;
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
        this.ClickGetPointKPI(this.academicKPIId, this.mark);
    }

}