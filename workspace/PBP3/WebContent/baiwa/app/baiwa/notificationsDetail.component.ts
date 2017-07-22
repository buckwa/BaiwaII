import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';

import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';


@Component({
    templateUrl: 'app/baiwa/html/notificationsDetail.component.html'
})
export class notificationsDetail {

    public libPath: string;
    sub: any;
    code: any;
    public messageList: any[];
    public nMessage: any;
    public user: any;
    public profile:any;

    constructor(private router: Router,private http: Http, private route: ActivatedRoute) {
        this.libPath = "/PBP3/baiwa/libs/";
        this.sub = this.route.params.subscribe(params => {
            this.code = +params['code'];

            this.messageList = this.defaultMessage();
            this.nMessage = this.newMessage();
        });

    }
    ngOnInit() {
        this.GetUserSession();



    }
    ngAfterViewInit() {
        console.log("Code kpi mapping :" + this.code);

    }
    defaultMessage() {
        return [{
            "createBy": "",
            "messageDetail": "",
            "messageId": "",
            "topicId": ""
        }]
    }
    newMessage() {
        return {
            "messageId": "",
            "topicId": "",
            "messageDetail": "",
            "imagePath": "",
            "status": "",
            "userName": "",
        }
    }
    public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetuserSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !"));
    }
    public GetuserSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.GetPersonByAcadamy(this.user.userName,this.user.currentAcademicYear);

        this.getMessage();
    }

    getMessage() {
        var url = "../person/getMassageByKPI/" + this.code;

        this.http.get(url).subscribe(response => this.GetMessageSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );

    }
    GetMessageSucess(response: any) {
        this.messageList = response.json(JSON.stringify(response._body));
        this.UpdateFlagMessage(this.messageList[0].messageId);

    }
    GetPersonError(error: any) {
        console.log("error")

    }
    public replyMessage() {
        var url = "../person/replyMessage";
        this.nMessage.topicId = this.code;
        this.nMessage.createBy = this.profile.thaiName + " " + this.profile.thaiSurname;

        this.http.post(url, this.nMessage).subscribe(response => this.replyMessageSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );

    }
    replyMessageSucess(response: any) {
        this.nMessage.messageDetail = "";
        console.log("addmessageSucess!");
        this.getMessage();

    }

    UpdateFlagMessage(messageId: any) {
        var url = "../person/updateFlagMessage/" + messageId;
        this.http.get(url).subscribe(response => this.updateSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );

    }
    updateSucess(response: any) {
        console.log("UpdateStatus Message Sucess..");
    }
    backToHome(){
     
         this.router.navigate(['/home']);
    }

    public GetPersonByAcadamy(user: String,  year: String) {
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year
        this.http.get(url).subscribe(response => this.GetPersonSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );
    }

    public GetPersonSucess(response: any) {
        this.profile = response.json(JSON.stringify(response._body));

    }



}