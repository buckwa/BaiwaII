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

    @ViewChild('personTimeTable') namelistcstable;

    public makeDataTable: any = {
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
    };


    constructor() {
        this.data = {
            "data": [
                { "code": "1102001", "name": "Computer Programming", "sub": "T", "hour": "3", "point": "4", "class": "AA", "student": "45", "section": "1", "time": "mon", "other": "-" },
                { "code": "1102002", "name": "Computer Programming2", "sub": "T", "hour": "3", "point": "4", "class": "AA", "student": "45", "section": "1", "time": "mon", "other": "-" },
                { "code": "1102003", "name": "Computer Programming3", "sub": "T", "hour": "3", "point": "4", "class": "AA", "student": "45", "section": "1", "time": "mon", "other": "-" }
            ]
        }

    }
    ngOnInit() {
        //this.timetable = this.data.DataTable;
        //this.makeDataTable.data = this.data;     
        this.datatable();
        this.dataTB();
    }
    public datatable(): void {
        jQuery("#DTable").DataTable({

            "searching": false,
            "bPaginate": false,
            "paging": false,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "ajax": "app/baiwa/jsonTimeTable.txt",
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
        });

    }
    public dataTB (){

        this.a = jQuery("#D2Table").DataTable({

            "searching": false,
            "bPaginate": false,
            "paging": false,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "ajax": "app/baiwa/jsonTimeTable2.txt",
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
        });

    }



}