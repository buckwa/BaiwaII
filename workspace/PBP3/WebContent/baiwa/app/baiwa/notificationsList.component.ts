import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/notificationsList.component.html'
})


export class notificationsList  {

    public libPath: string;

    public messageList:any[];
    public pagingMessage:any;
    public totalMessage:any;
    public pageCount:any[];
    public page:any[];
     public start;
     public end;
    constructor(private http: Http,private commonService: CommonService) {
        this.libPath = "/PBP3/baiwa/libs/";
        this.messageList = this.defaultMessage();
        this.pageCount = this.pagecountD();
        this.pagingMessage = this.defaultPaging();
    }
    ngOnInit() {

       
    }
    ngAfterViewInit() {
        this.CountMessageAll();
        
        
    }

    defaultMessage(){
        return [{
            "createBy":"",
            "messageDetail":"",
            "messageId":"",
            "topicId":""
        }]
    }
    defaultPaging(){
        return {
            "pageStart":"",
            "pageEnd":"",
            "totalMessage":"",
            "data":""
        }
    }
    pagecountD(){
        return [{
            "pageStart":"",
            "pageEnd":"",
        }]
    }
     getMessage(){
        var url = "../person/getMassageAll/"+this.pagingMessage.pageStart+"/"+this.pagingMessage.pageEnd;
        this.http.get(url).subscribe(response => this.GetMessageSucess(response),
            error => this.GetMessageError(error), () => console.log("notificationsList")
        );

    }
    GetMessageSucess(response:any){
        this.messageList =  response.json(JSON.stringify(response._body));
        this.commonService.unLoading();

    }
    GetMessageError(error:any){
    this.commonService.unLoading();
        console.log("error");
    }
    CountMessageAll(){
         this.commonService.loading();
        var url = "../person/countMessage";
         this.http.get(url).subscribe(response => this.countMessageSucess(response),
            error => this.GetMessageError(error), () => console.log("editdone !")
        );
    }
    countMessageSucess(response:any){
        var totalMessage =  response.json(JSON.stringify(response._body));
        this.totalMessage = totalMessage;
        this.pagingMessage.totalMessage = totalMessage;
        
        this.countPaging();
        

    }
    countPaging(){
        var npage  ;
        var a = [];
        var totalpage = this.totalMessage/20 ;
        var modPage = this.totalMessage%20
        if (modPage >0){
            totalpage = totalpage+1;
        }
        if ( totalpage >1){
            for (var i =1 ;i< totalpage ;i++){
            a.push(i);
            }
            this.start = 0;
            this.end =20;
        }else{
            this.start = 0;
            this.end =totalpage;
             a.push(1);
        }
        this.page = a;
        this.pagingMessage.pageStart = this.start
        this.pagingMessage.pageEnd = this.end
        //console.log("page is :"+ this.page);
        this.getMessage();
    }
    clickPage(val:any){
         this.commonService.loading();
        var modPage = this.totalMessage%20
        console.log("click value:"+val);
        if (val >1 && val != this.page.length){
            this.start = (val-1)*20;
            //this.end = val*20
        }else if (val == this.page.length ){
            this.start = (val-1)*20;
            this.end = modPage;
        }
        this.pagingMessage.pageStart = this.start;
       // this.pagingMessage.pageEnd = this.end
        this.getMessage();
        console.log("Strat Page :"+this.start+" EndPage:"+this.end );
    }


}