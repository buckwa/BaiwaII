import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';



@Component({
    templateUrl: 'app/baiwa/html/AdminAcademicKPI.component.html'
})

export class AdminAcademicKPI implements OnInit {
    ngOnInit() {


    }
    public adminFacultyeditSucess(response: any) {
    }

    public adminFacultyeditError(error: String) {


    }
}