import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';


@Component({
    templateUrl: 'app/baiwa/html/AcademicWork.component.html'
})
export class AcademicWork implements OnInit, AfterViewInit{
    public user:any;
    public academy:any;
    public academyList:any;
    public kpiuserList:any[];

    constructor(private commonService: CommonService, private http: Http) {


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
        this.kpiuserList = this.academyList[0].academicKPIUserMappingList;

    }


}