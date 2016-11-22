import { Component, ViewChild ,OnInit} from '@angular/core';

import { Router, ActivatedRoute,NavigationCancel  } from '@angular/router';

import { URLSearchParams, Http} from '@angular/http';

import {CommonService} from './../service/Common.service';

@Component({
    templateUrl: 'app/baiwa/html/approvework.component.html'
})
export class approvework implements OnInit {
    public sub:any;
    public email:any;
    public rond:any;
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

        constructor(private route: ActivatedRoute,private http:Http,private commonService: CommonService){
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
       this.email = params['email']; // (+) converts string 'id' to a number
       this.rond = +params['rond'];
       // In a real app: dispatch action to load the details here.
    });
    this.getwork();

    }
    getwork(){
        //this.personWorkList = this.personWork.academicKPIUserMappingList;
        this.commonService.loading();
        var url ="../head/initByUserName/"+this.email+"/1"
        this.http.get(url).subscribe(response => this.getkpiworksucess(response),
            error => this.getkpiworkeror(error), () => console.log("editdoneUser !"));
    }
    getkpiworkeror(error:any){
        console.log("error !")   
    }
    getkpiworksucess(response:any){
        var json = response.json(JSON.stringify(response._body));
        this.academicKPIUserMappingList = json.department.academicPersonList[0].academicKPIUserMappingList;
        this.person = json.department.academicPersonList[0].thaiName +"  "+ json.department.academicPersonList[0].thaiSurname;
        //console.log("getkpimapping :" +this.academicKPIUserMappingList)
        this.commonService.unLoading();
    }
    blackpage(){
        window.location.href = "#/initApprove";
    }
    public ClickGetPointKPI(Code: string,indexKPI:string) {
        this.indexKPI = indexKPI;
        var url = "../person/getImportWork/" + Code
        return this.http.get(url).subscribe(response => this.GetKPISucess(response),
            error => this.getkpiworkeror(error), () => console.log("editdoneUser !"));
    }
    public GetKPISucess(response: any) {
        this.pointKPI = response.json(JSON.stringify(response._body));
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
        console.log("unApproveSucess!");
    }
    approveSucess(response:any){
        var temp = response.json(JSON.stringify(response._body));
        if (temp.status == '0'){
            this.academicKPIUserMappingList[this.indexKPI].status = 'APPROVED';
            this.statusKpi = true;
        }
        console.log("ApproveSucess!");
    }
}