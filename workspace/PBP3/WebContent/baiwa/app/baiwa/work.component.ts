import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;
declare var kendo: any;

@Component({
    templateUrl: 'app/baiwa/html/work.component.html'
})
export class work  {



    constructor() {


    }
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
            "ajax": "app/baiwa/jsonTimeTable_work.txt",
            "columns": [
                { "data": "code" },
                { "data": "name" },
                { "data": "sub" },
                { "data": "hour" },
                { "data": "point" },
                { "data": "class" },
                { "data": "student" },
                { "data": "section" },
                { "data": "time" },
                { "data": "other" }
            ]
        });

    }
    ngAfterViewInit() {
        
    }



}