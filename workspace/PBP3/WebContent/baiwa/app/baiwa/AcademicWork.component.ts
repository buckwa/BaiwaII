import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';


@Component({
    templateUrl: 'app/baiwa/html/AcademicWork.component.html'
})
export class AcademicWork implements OnInit, AfterViewInit{
    public user:any;
    public academy:any;
    public academyList:any[];
    public kpiuserList:any[]=[];

    constructor(private commonService: CommonService, private http: Http) {
        this.academy = this.setdefualtkpi();
        this.kpiuserList=[];


    }
    setdefualtkpi(){
        return {
            "academicYear":"",
            "totalInMapping":"",
            "calResultStr":"",
            "pBPWorkTypeList":[{
                "name":"",
                "totalInWorkType":"",
                "academicKPIUserMappingList":[{}]
            }]
        }
    }

     ngOnInit() {
         this.GetUserSession();

    }
    ngAfterViewInit() {

    }

      public GetUserSession(){
        var url ="../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetUserSessionSucess(response),
        error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));
    }
    public GetUserSessionSucess(response:any){
        this.user =response.json(JSON.stringify(response._body));
        this.GetAcademicWork(this.user.userName,this.user.currentAcademicYear,"1");
        
    }
    public GetUserSessionError (error :String){
        console.log("GetPersonError.")

    }

    public GetAcademicWork (user:String,year:String,round:String){
        var url ="../person/getAcademicWork/"+user+"/"+year+"/"+round
        return this.http.get(url).subscribe(response => this.GetUserAcademicSucess(response),
        error => this.GetUserSessionError(error), () => console.log("editdoneUser !"));

    }
    public GetUserAcademicSucess (response:any){
        this.academy =response.json(JSON.stringify(response._body));
        this.academyList = this.academy.pBPWorkTypeList;
        //this.kpiuserList =this.academy.pBPWorkTypeList.academicKPIUserMappingList;
        for(var i=0;i<this.academy.pBPWorkTypeList.length;i++){
            this.kpiuserList.push(this.academy.pBPWorkTypeList[i].academicKPIUserMappingList)
        }
        

    }


}