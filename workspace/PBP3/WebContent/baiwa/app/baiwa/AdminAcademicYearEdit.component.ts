import { Component, Injectable, Input, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonService } from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';
import {Location} from '@angular/common';

@Component({
    templateUrl: 'app/baiwa/html/AdminAcademicYearEdit.component.html'
})

export class AdminAcademicYearEditComponent implements OnInit {
   
    public year: string;
    public startDate: string;
    public endDate: string;


    constructor(private route: ActivatedRoute, private router: Router,private _location: Location) {

    }

    ngOnInit() {
        this.route.params.subscribe(params => this.year = params["year"]);
        this.route.params.subscribe(params => this.startDate = params["startDate"]);
        this.route.params.subscribe(params => this.endDate = params["endDate"]);

        if (this.year && this.startDate && this.endDate) {
            console.log("Input Year :", this.year);
            console.log("Input StartDate :", this.startDate);
            console.log("Input EndDate :", this.endDate);
        }

    }

    public backClicked(){
        this._location.back();
    }

}