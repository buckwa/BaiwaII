import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';

@Component({
    templateUrl: 'app/baiwa/html/initApprove.component.html'
})
export class InitApporve implements OnInit {
    public academicPersonList:any[];
    public tabPerson:boolean;
    public personWork:any;
    public personWorkList:any[];

     constructor(private commonService: CommonService, private http: Http) {
         this.tabPerson = false;

    }

    ngOnInit(){
        this.initTotle();


    }
    initTotle(){
        var url = "../head/init";
        this.http.get(url).subscribe(response => this.initTotleSucess(response),
            error => this.initTotlError(error), () => console.log("editdoneUser !"));
    }
    initTotleSucess(response:any){
        var bodyJson;
        bodyJson = response.json(JSON.stringify(response._body));
        this.academicPersonList = bodyJson.department.academicPersonList;

    }
    initTotlError(error:any){
        console.log("error getTotle");

    }
    clickPerson(index:string){
        this.tabPerson = true;
        this.personWork = this.academicPersonList[index];
        console.log("index of personlist is "+index);
        this.personWorkList = this.personWork.academicKPIUserMappingList;

    }
    blackpage(){
        this.tabPerson = false;
    }

}