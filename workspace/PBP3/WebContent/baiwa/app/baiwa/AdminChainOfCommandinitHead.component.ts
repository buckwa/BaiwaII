import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/AdminChainOfCommandinitHead.component.html'
})

export class AdminChainOfCommandinitHead implements OnInit {

    @ViewChild('personTimeTable') timetabletable;


    constructor(private commonService:CommonService,private http:Http) {

            
    }

     public makeDataTable: any = {
        "searching": false,
        "bPaginate": false,
        "paging": true,
        "bLengthChange": false,
        "bInfo": false,
        "bAutoWidth": false,
        "ajax": "app/baiwa/jsonTimeTable1.txt",
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


    ngOnInit() {
        this.datatable();   

    }

     public datatable(): void {

        jQuery("#DTable").DataTable({
            "searching": false,
            "bPaginate": false,
            "paging": true,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "ajax": "app/baiwa/jsonTimeTable1.txt",
            "columns": [
                { "data": "code" },
                { "data": "name" },
                { "data": "sub" },
                { "data": "hour" },
            ]
        });
    }

    ngAfterViewInit() {
        
    }
    //  public getDatatabel1(){
    //     var url = "../personTimeTable/1";
    //      this.http.get(url).subscribe(response => this.getTimeTableSucess(response),
    //         error => this.GetPersonError(error), () => console.log("callsevice done !"));
    // }

    // getTimeTableSucess(response:any){
    //     this.makeDataTable.data = response.json(JSON.stringify(response._body));
    //     this.timetabletable.show();  
    // }
  
    // GetPersonError(error:any){
    //     console.log("call service error"+error);
    // }



}