import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/personYearReport.component.html'
})
export class personYearReport  {
    user:any;
    year:any;
    acdemicyear:any;

 public libPath: string;

    constructor(private http:Http) {
        this.libPath = "/PBP3/baiwa/libs/";

    }
    ngOnInit() {

       this.GetUserSession();
    }
    ngAfterViewInit() {
        
    }
    public GetUserSession() {
        var url = "../person/getUserSession";
        this.http.get(url).subscribe(response => this.GetuserSucess(response),
            error => this.GetPersonError(error), () => console.log("callsevice done !"));

    }
    public GetuserSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.year = this.user.academicYearList;
        //this.year = ["2557","2558","2559"];
        this.acdemicyear = this.user.currentAcademicYear;
        return true;
    }
    GetPersonError(error:any){

    }
        changYear(value:any){
        this.acdemicyear = value;
        console.log("change year"+value);
        //this.year = this.repositories.find(repository => repository.name === this.selectedRepositoryName);

    }
    clickDownload(){
        window.location.href='../report/printReportYear.htm?year='+this.acdemicyear+'&round=undefined';
    }



}