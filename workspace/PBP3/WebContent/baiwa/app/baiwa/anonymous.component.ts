import { Component, Injectable,Input,ViewChild,OnInit,Output,EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';



@Component({
    templateUrl: 'app/baiwa/html/anonymous.component.html'
})

export class anonymous implements OnInit  {
    public listKpi:any;
    public pBPWorkTypeList:any[];
    public acadamicRound:any[];
    public listacadamicwork:any;
    public facultyCode:any;
    public code:string;
    public year:string;

        constructor(private http: Http ) {
    }

     ngOnInit() {
        var url = "../person/anonymous/init"
        this.http.get(url).subscribe(response => this.getjSonSucess(response),
            error => this.dataError(error), () => console.log("editdone !")
        );

    }
    getjSonSucess(response:any){
        this.listKpi = response.json(JSON.stringify(response._body));
        this.pBPWorkTypeList = this.listKpi.pBPWorkTypeWrapper.pBPWorkTypeList;
        this.facultyCode = this.pBPWorkTypeList[0].facultyCode;

        this.acadamicRound = this.listKpi.academicYearWrapper.academicYearEvaluateRoundList;

        console.log("getsucess"+ response);
    }
    dataError(error:any){
        console.log("geterror"+error);
    }

    




}