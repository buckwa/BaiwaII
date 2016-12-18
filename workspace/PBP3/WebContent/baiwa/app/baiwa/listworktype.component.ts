import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';

const URL = 'http://localhost:8080/PBP3/person/importwork_file';

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

        window.setTimeout(() => {
            var temp = !this.uploader.getNotUploadedItems().length;
            this.uploadalert = true;
            console.log("status upload :" + temp)
            this.uploader.clearQueue();
        }, 600);


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
        var tamp =0;
            for(var i=0;i<len;i++) {
                
                if(this.FormAddInput[i].value == null ){
                
                    console.log("Required Na !");
                    tamp =1;
                }

            }
            
            if(tamp==0){
                var url = "../person/importwork"
                this.http.post(url,this.inport5).subscribe(response => this.savesucess(response),
                error => this.GetError(error), () => console.log("save sucess na !"));

            }



    }

    public savesucess (response :any){

            this.academicKPIId = response.json(JSON.stringify(response._body));
            this.academicKPIId = this.academicKPIId.resObj;
            this.statusActiveUpload = true;
            this.savealert = true;
            this.valid = false;
    }

    public exitModal (){

             this.statusActiveUpload =  false;
             this.uploader.clearQueue();
    }




    

}