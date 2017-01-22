import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/AdminWorkUser.component.html'
})
export class AdminWorkUser implements OnInit {

     @ViewChild('personTimeTable') timetabletable;

    public data: any;
    public datalist: any;

    public makeDataTable: any = {
        "searching": false,
        "bPaginate": false,
        "paging": false,
        "bLengthChange": false,
        "bInfo": false,
        "bAutoWidth": false,
        "columns": [
            { "data": "username" },
            { "data": "firstLastName" },
            { "data": "facultyDesc" },
            { "data": "departmentDesc" },
            { "data": "employeeType" },
            { "data": "academicYear" },
            {
                data: null,
                className: "center",
                defaultContent: '<a href="" class="editor_edit">Edit</a> / <a href="" class="editor_remove">Delete</a>'
            }

        ]
    };

    constructor(private commonService:CommonService,private http:Http) {


    }
    ngOnInit() {
           this.getDatatabel1();
       
    }
     public getDatatabel1(){
        var url = "../admin/json/GetUserlist";
         this.http.get(url).subscribe(response => this.getTimeTableSucess(response),
            error => this.GetPersonError(error), () => console.log("callsevice done !"));

    }
    getTimeTableSucess(response:any){
        this.datalist = response.json(JSON.stringify(response._body));
        this.makeDataTable.data = this.datalist[0].currentPageItem;
        this.timetabletable.show();
        
    }
    GetPersonError(error:any){
        console.log("call service error"+error);

    }


}