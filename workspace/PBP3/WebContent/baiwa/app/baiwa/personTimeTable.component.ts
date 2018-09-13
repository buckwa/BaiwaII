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
    public user:any;
    public year:any[];
    public acdemicyear:string;

    @ViewChild('personTimeTable') timetabletable;
    @ViewChild('personTimeTable2') timetabletable2;


    public makeDataTable: any = {
        "searching": false,
        "bPaginate": false,
        "paging": false,
        "bLengthChange": false,
        "bInfo": false,
        "bAutoWidth": false,
        "columns": [
            { "data": "subjectCode" },
            { "data": "subjectName" },
            { "data": "lecOrPrac" },
            { "data": "teachHr" },
            { "data": "credit" },
            { "data": "degreeStr" },
            { "data": "totalStudent" },
            { "data": "secNo" },
            {
                "data": "teachDayStr", "width": "20%",
                "render": function (data, type, full, meta) {
                //     console.log(full);
                //     // full.teachtimeStr
                // // var res =  full.teachtimeStr.split(",");
                // var result ="";

                // var resS = full.teachTime1.substring(0, 5);
                // var resE = full.teachTime2.substring(0, 5);

                // // var res =  full.teachtimeStr.split("<BR>");

                
                // result = data+" "+resS+"-"+resE+full.teachtimeStr;
    
                // return result;
                var result ="";
                var resultDisplay ="";
// console.log(full);
                if(full.teachtimeStr==null){
                    result="";
                }else{
                    result=full.teachtimeStr;
                }
                

                var resS = full.teachTime1.substring(0, 5);
                var resE = full.teachTime2.substring(0, 5);
                var resSE = full.teachDay+" "+resS+"-"+resE;

                result = resSE + result;
                var resArray =  result.split(",");
                resArray = resArray.sort();
                

                
        
                for (var i = 0; i < resArray.length; i++) { 
                    var arrayDisplay =  resArray[i].split(" ");
                    var resultDisDayTime ="";
                    if(arrayDisplay[0]=="1"){resultDisDayTime="อาทิตย์";
							}else if(arrayDisplay[0]=="2"){resultDisDayTime="จันทร์";
							}else if(arrayDisplay[0]=="3"){resultDisDayTime="อังคาร";
							}else if(arrayDisplay[0]=="4"){resultDisDayTime="พุธ";
							}else if(arrayDisplay[0]=="5"){resultDisDayTime="พฤหัส.";
							}else if(arrayDisplay[0]=="6"){resultDisDayTime="ศุกร์";
                            }else if(arrayDisplay[0]=="7"){resultDisDayTime="เสาร์";}
                    resArray[i] = resultDisDayTime+" "+ arrayDisplay[1];
                }
                // console.log(resArray);
                // resultDisplay = resultDisplay + resArray[0] +"<BR>";
                if(resArray.length==1){
                    resultDisplay = resultDisplay + resArray[0] +"<BR>";
                }else{
                for (var i = 0; i < resArray.length; i++) { 

                               // console.log("I :",i);
                if( i === resArray.length-1){
                var arrayDisplayNew =  resArray[i].split(" ");
                resultDisplay = resultDisplay + resArray[i] +"<BR>";
                break;
                }
                    var arrayDisplay =  resArray[i].split(" ");
                    var arrayDisplayNew =  resArray[i+1].split(" ");
                        // console.log(arrayDisplay[0]);
                        // console.log(arrayDisplayNew[0]);
                        // console.log(resArray[i]);
                        // console.log(resArray[i+1]);

                        
                        
                    if(arrayDisplay[0]==arrayDisplayNew[0]){
                        resultDisplay = resultDisplay + resArray[i] +"  ";
                    }else{
                        resultDisplay = resultDisplay + resArray[i] +"<BR>";
                    }
                }
            }
                 console.log(resultDisplay);


                return resultDisplay;
                }
            },
            { "data": "remark" }
        ]
    };
    public makeDataTable2: any = {
        "searching": false,
        "bPaginate": false,
        "paging": false,
        "bLengthChange": false,
        "bInfo": false,
        "bAutoWidth": false,
        "columns": [
            { "data": "subjectCode" },
            { "data": "subjectName" },
            { "data": "lecOrPrac" },
            { "data": "teachHr" },
            { "data": "credit" },
            { "data": "degreeStr" },
            { "data": "totalStudent" },
            { "data": "secNo" },
            {
                "data": "teachDayStr", "width": "20%",
                "render": function (data, type, full, meta) {
                //     console.log(full);
                //     // full.teachtimeStr
                // // var res =  full.teachtimeStr.split(",");
                // var result ="";

                // var resS = full.teachTime1.substring(0, 5);
                // var resE = full.teachTime2.substring(0, 5);

                // // var res =  full.teachtimeStr.split("<BR>");

                
                // result = data+" "+resS+"-"+resE+full.teachtimeStr;
    
                // return result;
                var result ="";
                var resultDisplay ="";
// console.log(full);
                if(full.teachtimeStr==null){
                    result="";
                }else{
                    result=full.teachtimeStr;
                }
                

                var resS = full.teachTime1.substring(0, 5);
                var resE = full.teachTime2.substring(0, 5);
                var resSE = full.teachDay+" "+resS+"-"+resE;

                result = resSE + result;
                var resArray =  result.split(",");
                resArray = resArray.sort();
                

                
        
                for (var i = 0; i < resArray.length; i++) { 
                    var arrayDisplay =  resArray[i].split(" ");
                    var resultDisDayTime ="";
                    if(arrayDisplay[0]=="1"){resultDisDayTime="อาทิตย์";
							}else if(arrayDisplay[0]=="2"){resultDisDayTime="จันทร์";
							}else if(arrayDisplay[0]=="3"){resultDisDayTime="อังคาร";
							}else if(arrayDisplay[0]=="4"){resultDisDayTime="พุธ";
							}else if(arrayDisplay[0]=="5"){resultDisDayTime="พฤหัส.";
							}else if(arrayDisplay[0]=="6"){resultDisDayTime="ศุกร์";
                            }else if(arrayDisplay[0]=="7"){resultDisDayTime="เสาร์";}
                    resArray[i] = resultDisDayTime+" "+ arrayDisplay[1];
                }
                // console.log(resArray);
                // resultDisplay = resultDisplay + resArray[0] +"<BR>";
                if(resArray.length==1){
                    resultDisplay = resultDisplay + resArray[0] +"<BR>";
                }else{
                    console.log("Head :",resArray.length);
                for (var i = 0; i < resArray.length; i++) { 

                    // console.log("I :",i);
                    if( i === resArray.length-1){
                        var arrayDisplayNew =  resArray[i].split(" ");
                        resultDisplay = resultDisplay + resArray[i] +"<BR>";
                        break;
                    }

                    var arrayDisplay =  resArray[i].split(" ");
                    var arrayDisplayNew =  resArray[i+1].split(" ");

                    console.log(resArray[i+1]);
                        // console.log(arrayDisplay[0]);
                        // console.log(arrayDisplayNew[0]);
                        // console.log(resArray[i]);
                        // console.log(resArray[i+1]);

                        
                        
                    if(arrayDisplay[0]==arrayDisplayNew[0]){
                        resultDisplay = resultDisplay + resArray[i] +"  ";
                    }else{
                        resultDisplay = resultDisplay + resArray[i] +"<BR>";
                    }

              

                }
            }
                 console.log(resultDisplay);


                return resultDisplay;
                }
            },
            { "data": "remark" }
        ]
    };




    constructor(private commonService:CommonService,private http:Http) {

    }
    ngOnInit() {
        this.GetUserSession();
        
    }
   
    public getDatatabel1(){
        var url = "../personTimeTable/getTimeTable/"+this.acdemicyear+"/"+this.user.userName+"/1";
         this.http.get(url).subscribe(response => this.getTimeTableSucess(response),
            error => this.GetPersonError(error), () => console.log("callsevice done !"));

    }
    public getDatatabel2(){
        var url = "../personTimeTable/getTimeTable/"+this.acdemicyear+"/"+this.user.userName+"/2";
         this.http.get(url).subscribe(response => this.getTimeTableSucess2(response),
            error => this.GetPersonError(error), () => console.log("callsevice done !"));

    }
    getTimeTableSucess(response:any){
        this.makeDataTable.data = response.json(JSON.stringify(response._body));
        console.log( response);
        this.timetabletable.show();
        
    }
    getTimeTableSucess2(response:any){
        this.makeDataTable2.data = response.json(JSON.stringify(response._body));
        this.timetabletable2.show();
        
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
        this.getDatatabel1();
        this.getDatatabel2();
        return true;
    }
    GetPersonError(error:any){
        console.log("call service error"+error);

    }
    changYear(value:any){
        this.acdemicyear = value;
        console.log("change year"+value)
        this.getDatatabel1();
        this.getDatatabel2();
        //this.year = this.repositories.find(repository => repository.name === this.selectedRepositoryName);

    }



}