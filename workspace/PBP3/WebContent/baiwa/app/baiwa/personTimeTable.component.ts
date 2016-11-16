import { Component, ViewChild, OnInit} from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/personTimeTable.component.html'
})
export class personTimeTable implements OnInit {

    //public timetable: any;
    public data: any;
    public a: any;
    public user:any;

    @ViewChild('personTimeTable') timetabletable;
    @ViewChild('personTimeTable2') timetabletable2;


    public makeDataTable: any = {
        "searching": false,
        "bPaginate": false,
        "paging": false,
        "bLengthChange": false,
        "bInfo": false,
        "bAutoWidth": false,
        "columns": [
            { "data": "subjectCode" },
            { "data": "subjectName" },
            { "data": "lecOrPrac" },
            { "data": "teachHr" },
            { "data": "pracHr" },
            { "data": "degreeStr" },
            { "data": "totalStudent" },
            { "data": "secNo" },
            { "data": "teachDayStr" },
            { "data": "remark" }
        ]
    };
    public makeDataTable2: any = {
        "searching": false,
        "bPaginate": false,
        "paging": false,
        "bLengthChange": false,
        "bInfo": false,
        "bAutoWidth": false,
        "columns": [
            { "data": "subjectCode" },
            { "data": "subjectName" },
            { "data": "lecOrPrac" },
            { "data": "teachHr" },
            { "data": "pracHr" },
            { "data": "degreeStr" },
            { "data": "totalStudent" },
            { "data": "secNo" },
            { "data": "teachDayStr" },
            { "data": "remark" }
        ]
    };




    constructor(private commonService:CommonService,private http:Http) {

    }
    ngOnInit() {
        this.GetUserSession();
        
    }
   
    public getDatatabel1(){
        var url = "../personTimeTable/getTimeTable/"+this.user.currentAcademicYear+"/"+this.user.userName+"/1";
         this.http.get(url).subscribe(response => this.getTimeTableSucess(response),
            error => this.GetPersonError(error), () => console.log("callsevice done !"));

    }
    public getDatatabel2(){
        var url = "../personTimeTable/getTimeTable/"+this.user.currentAcademicYear+"/"+this.user.userName+"/2";
         this.http.get(url).subscribe(response => this.getTimeTableSucess2(response),
            error => this.GetPersonError(error), () => console.log("callsevice done !"));

    }
    getTimeTableSucess(response:any){
        this.makeDataTable.data = response.json(JSON.stringify(response._body));
        this.timetabletable.show();
        
    }
    getTimeTableSucess2(response:any){
        this.makeDataTable2.data = response.json(JSON.stringify(response._body));
        this.timetabletable2.show();
        
    }

    public GetUserSession() {
        var url = "../person/getUserSession";
        this.http.get(url).subscribe(response => this.GetuserSucess(response),
            error => this.GetPersonError(error), () => console.log("callsevice done !"));

    }
    public GetuserSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.getDatatabel1();
        this.getDatatabel2();
        return true;
    }
    GetPersonError(error:any){
        console.log("call service error"+error);

    }



}