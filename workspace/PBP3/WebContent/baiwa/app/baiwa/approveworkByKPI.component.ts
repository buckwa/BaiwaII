import { Component, ViewChild ,OnInit} from '@angular/core';
import { Router, ActivatedRoute,NavigationCancel  } from '@angular/router';
import { URLSearchParams, Http} from '@angular/http';
import {CommonService} from './../service/Common.service';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/approveworkByKPI.component.html'
})
export class approveworkByKPI implements OnInit {
    public sub:any;
    public code:any;
    public codeNew:any;
    public status:any;
    public academicKPIUserMappingList:any[];
    public pointKPI: any;
    public mark: String;
    public pointLPIList: any[];
    public statusKpi: boolean;
    public fileWork: any[];
    chFilework: boolean;
    public person:any;
    public academicKPI:any;
    public indexKPI:any;
    public Model:any;
    public workcode:any;
    public user: any;
    public profile: any;
    public replyMessage: any;

    public messageList: any[];

        constructor(private route: ActivatedRoute,private http:Http,private commonService: CommonService, private router: Router){
         this.pointKPI = this.setdefualtpoitkpi();
         this.academicKPI =this.setacademicKPIdefault();
         this.academicKPIUserMappingList = this.kpiusermappingList();
    }
    setdefualtpoitkpi() {
        return {
            "name": "",
            "kpiUserMappingId": "",
            "calResultStr": "",
            "status":"",
            "description":"",
            "academicKPIAttributeValueList": [{}]
        }
    }
        setacademicKPIdefault(){
        return {
            "name":"",
            "unitDesc":"",
            "mark":""
        }
    }
    kpiusermappingList(){
        return[{
            "status":"",
            "kpiUserMappingId":"",
            "academicKPIAttributeValueList":[{}]
        }]
    }
    ngOnInit(){
         this.sub = this.route.params.subscribe(params => {
            this.code = params['code']; // (+) converts string 'id' to a number
            this.status = +params['status'];
            //In a real app: dispatch action to load the details here.
        });

        this.route.params.subscribe(params => this.code = params["code"]);
        if (this.code) {
            console.log("code :", this.code);
           
        }
        this.route.params.subscribe(params => this.status = params["status"]);
        if (this.status) {
            console.log("status :", this.status);
            
        }

        this.route.params.subscribe(params => this.workcode = params["workcode"]);
        if (this.status) {
            console.log("workcode :", this.workcode);
            
        }



     this.GetUserSession();
     //this.getwork();

    }
    public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetUserSessionSucess(response),
            error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }
    public GetUserSessionSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.GetPersonByAcadamy(this.user.userName, this.user.currentAcademicYear);

       
    }
    public GetUserSessionError(error: String) {
        console.log("GetPersonError.")

    }

    public GetPersonByAcadamy(user: String, year: String) {
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year
        this.http.get(url).subscribe(response => this.GetPersonSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );
    }

    public GetPersonSucess(response: any) {
        this.profile = response.json(JSON.stringify(response._body));
        this.getwork();

    }
    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }

    getwork(){
        //this.personWorkList = this.personWork.academicKPIUserMappingList;
        this.commonService.loading();
        ///initByUserNameNew/{code}/{status}/{facultyCode}/{department_desc}/{employeeType}
        var url ="../head/initByUserNameNew/"+this.code+"/"+this.status+"/" + this.user.facultyCode + "/"+this.user.departmentName+"/"+this.profile.evaluateRound;
        this.http.get(url).subscribe(response => this.getkpiworksucess(response),
            error => this.getkpiworkeror(error), () => console.log("editdoneUser !"));
    }
    getkpiworkeror(error:any){
        console.log("error !")   
    }
    getkpiworksucess(response:any){
        var json = response.json(JSON.stringify(response._body));
        this.academicKPIUserMappingList = json.academicKPIUserMappingList;
        //this.person = json.department.academicPersonList[0].thaiName +"  "+ json.department.academicPersonList[0].thaiSurname;
        //console.log("getkpimapping :" +this.academicKPIUserMappingList)
        this.commonService.unLoading();
    }
    blackpage(){
       
          this.router.navigate(['/initApproveByKPI',  this.workcode  ]);
    }
    public ClickGetPointKPI(Code: string,indexKPI:string) {
        this.indexKPI = indexKPI;
        this.codeNew = Code;
        var url = "../person/getImportWorkNew/" + Code
        return this.http.get(url).subscribe(response => this.GetKPISucess(response),
            error => this.getkpiworkeror(error), () => console.log("editdoneUser !"));
    }

    public ClickGetPointKPINew(Code: string,indexKPI:string) {
        this.indexKPI = indexKPI;
        var url = "../person/getImportWorkNew/" + Code
        return this.http.get(url).subscribe(response => this.GetKPISucessNew(response),
            error => this.getkpiworkeror(error), () => console.log("editdoneUser !"));
    }
    
    public GetKPISucessNew(response: any) {
        this.Model = response.json(JSON.stringify(response._body));
        this.pointKPI = this.Model.academicKPIUserMapping;

        if(this.pointKPI.messageList !=null){
            this.messageList = this.pointKPI.messageList ;
        }
        

    }

    public GetKPISucess(response: any) {
        this.Model = response.json(JSON.stringify(response._body));
        this.pointKPI = this.Model.academicKPIUserMapping;
        this.pointLPIList = this.pointKPI.academicKPIAttributeValueList;
        this.fileWork = this.pointKPI.academicKPIAttachFileList;
        this.academicKPI = this.pointKPI.academicKPI;
        if (this.fileWork.length == 0) {
            this.chFilework = true;
        } else {
            this.chFilework = false;
        }
        if (this.pointKPI.status == "APPROVED") {
            this.statusKpi = true;
        } else {
            this.statusKpi = false;
        }
        if(this.pointKPI.messageList !=null && this.status =='C' ){
            this.messageList = this.pointKPI.messageList ;
        }
        

    }
    approveKPIWork(KPIId:string){
        var url = "../head/approveWork/"+KPIId;
        this.http.get(url).subscribe(response => this.approveSucess(response),
            error => this.getkpiworkeror(error), () => console.log("editdoneUser !"));
    }
    unapproveKPIWork(KPIId:string){
        var url = "../head/unApprove/"+KPIId;
        this.http.get(url).subscribe(response => this.unApproveSucess(response),
            error => this.getkpiworkeror(error), () => console.log("editdoneUser !"));
        

    }
    unApproveSucess(response:any){
        var temp = response.json(JSON.stringify(response._body));
        if (temp.status == '0'){
        this.academicKPIUserMappingList[this.indexKPI].status = 'CREATE';
        this.statusKpi = false;
        }
        jQuery("#myModal").modal('hide');
        console.log("unApproveSucess!");
    }
    approveSucess(response:any){
        var temp = response.json(JSON.stringify(response._body));
        if (temp.status == '0'){
            this.academicKPIUserMappingList[this.indexKPI].status = 'APPROVED';
            this.statusKpi = true;
        }
        jQuery("#myModal").modal('hide');

        console.log("ApproveSucess!");
    }

    public sentReplyPBPMessage(){
        if(this.replyMessage!=null){
        this.Model.replyMessage = this.replyMessage;
        var url = "../head/pbp/replyMessage";//ติดไว้ก่อน
        this.http.post(url, this.Model).subscribe(response => this.ReplyPBPMessageSucess(response),
            error => this.ReplyPBPMessageError(error), () => console.log("AdminUserCreate : Success saveUser !"));
        }
    }
    
    ReplyPBPMessageSucess(response:any){
        var temp = response.json(JSON.stringify(response._body));
        this.ClickGetPointKPINew(this.codeNew ,this.indexKPI);
    }
    ReplyPBPMessageError(response:any){
        console.log("Error!");
      
    }

}