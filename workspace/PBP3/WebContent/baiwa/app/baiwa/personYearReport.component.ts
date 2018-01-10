import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/personYearReport.component.html'
})
export class personYearReport {
    user: any;
    year: any;
    acdemicyear: any;
    evaluateRoundValue: any;
    profile: any;
    evaluateRoundList: any;
    public libPath: string;

    constructor(private http: Http) {
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
        console.log('User');
        console.log( this.user);
        this.year = this.user.academicYearList;
        //this.year = ["2557","2558","2559"];
        this.acdemicyear = this.user.currentAcademicYear;

        this.GetPersonByAcadamy( this.user.userName,this.acdemicyear);
        return true;
    }

    public GetPersonByAcadamy(user: String, year: String) {
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year
        this.http.get(url).subscribe(response => this.GetPersonSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );
    }

       public GetPersonSucess(response: any) {
           
        this.profile = response.json(JSON.stringify(response._body));
        console.log( this.profile );
        //this.imageProfilePath = this.sanitize(this.profile.picture)
     
        
        this.evaluateRoundValue = this.profile.evaluateRound;
        console.log( this.evaluateRoundValue );
        if (this.profile.employeeType == 'ข้าราชการ') {
            this.evaluateRoundList = this.profile.evaluateRoundList;
             console.log( this.evaluateRoundList );
        }
    }
    GetPersonError(error: any) {

    }
    changYear(value: any) {
        this.acdemicyear = value;
        console.log("change year" + value);
        //this.year = this.repositories.find(repository => repository.name === this.selectedRepositoryName);

    }
    clickDownload() {
        window.location.href = '../report/printReportYear.htm?year=' + this.acdemicyear + '&round='+this.evaluateRoundValue;
    }



}