import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';


@Component({
    templateUrl: 'app/baiwa/html/notificationsDetail.component.html'
})
export class notificationsDetail  {

public libPath: string;

    constructor() {
        this.libPath = "/PBP3/baiwa/libs/";

    }
    ngOnInit() {

       
    }
    ngAfterViewInit() {
        
    }



}