import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { RouterModule }   from '@angular/router';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';
declare var jQuery: any;

const URL = 'http://localhost:8080/PBP3/pam/person/uploadPersonProfilePicture2';


@Component({
    templateUrl: 'app/baiwa/html/home.component.html'
})
export class home implements OnInit, AfterViewInit {

    public libPath: string;
    public profile: any;
    public work: any[];
    public sumasix: any;
    public sumasix2: any;
    public user: any;
    public url:string;
    public personId:string;
    public imgUpload:boolean;
    public updateImg:boolean;

    public uploader: FileUploader = new FileUploader({ url: URL });
    constructor(private commonService: CommonService, private http: Http) {
        this.libPath = "/PBP3/baiwa/libs/";
        this.profile = this.defaultProfile();
        this.work = this.defaultWork();
        this.imgUpload = true;

    }
    ngOnInit() {

        this.GetUserSession();
        this.uploader.queue;
        
        this.uploader.onBuildItemForm = (fileItem: any, form: any) => {

        console.log("PersonId :"+this.personId);
        form.append(  'PersonId', this.personId  );

        };

    }
    ngAfterViewInit() {
    }

    public defaultProfile() {
        return {
            "thaiName": "",
            "thaiSurname": "",
            "facultyDesc": "",
            "departmentDesc": "",
            "employeeType": "",
            "rateNo": "",
            "academicRank": "",
            "maxEducation": "",
            "email": "",
            "personId": "",
        };
    }

    public defaultWork() {
        return [{
            "orderNo": "",
            "axisName": "",
            "axisValue": "",
            "axisName2": "",
            "axisValue2": "",
            "mean": ""
        }];
    }



    public sumaryAsix() {
        this.sumasix = 0;
        this.sumasix2 = 0;

        for (var i = 0; i < this.work.length; i++) {
            this.sumasix = parseFloat(this.sumasix) + parseFloat(this.work[i].axisValue);
            this.sumasix2 = parseFloat(this.sumasix2) + parseFloat(this.work[i].axisValue2);
        }
    }
    public GetPersonByAcadamy(user: String) {
        var url = "../person/getPersonByAcademicYear/" + user + "/2558"
        this.http.get(url).subscribe(response => this.GetPersonSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );
    }

    public GetPersonSucess(response: any) {
        this.profile = response.json(JSON.stringify(response._body));
        this.imgUpload = this.profile.picture;
        this.personId = this.profile.personId;

    }
    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }
    public GetRadarPlotNewSearch( year: String) {
        this.GetRadarPlotNew(this.user.userName, year , "1");
    }
    public GetRadarPlot() {
        this.GetRadarPlotNew(this.user.userName, this.user.currentAcademicYear , "1");
    }
    public GetRadarPlotNew(user: String, year: String, num: String) {
        var url = "../person/getRadarPlotNew/" + user + "/" + year + "/" + num;
        this.url = url;
        this.http.get(url).subscribe(response => this.GetRadarPlotSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );
    }
    public GetRadarPlotSucess(response: any) {
        this.work = response.json(JSON.stringify(response._body));
        this.sumaryAsix();
        this.createChart();
    }

    public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetuserSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !"));
    }
    public GetuserSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        
        this.GetPersonByAcadamy(this.user.userName);
        this.GetRadarPlotNew(this.user.userName, this.user.currentAcademicYear, "1");
    }

    public createChart(): void {
        

        jQuery("#KendoChart").kendoChart({
            title: {
                text: "คะแนนประจำปี"
            },
            dataSource: {
                transport: {
                    read: {
                        url: this.url,
                        cache: false,
                        dataType: "json"
                    }
                }
            },
            seriesDefaults: {
                type: "radarLine"
            },
            series: [{
                name: "คะแนนรวมรออนุมัติ",
                field: "axisValue2",
                color: '#FF8000'
            },
                {

                    name: "คะแนนรวมอนุมัติ",
                    field: "axisValue",
                    color: '#138021'
                }
            ],
            categoryAxis: {
                field: "axisName"
            },
            valueAxis: {
                labels: {
                    format: "{0}",
                    visible: true,
                },
                min: 0,
                max: 705.0
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });

        jQuery("#grid").kendoGrid({

            dataSource: {
                transport: {
                    read: {
                        url: "/PBP/json/person/getRadarPlot",
                        dataType: "Json"
                    }
                }
            },
            columns: [
                { field: "axisName", title: "ประเภทภาระงาน " },
                { field: "axisValue", title: "คะแนน" }
            ]
        });

    }
    public changeUpload(){
        console.log("imageChange");
        this.imgUpload = false;
        this.updateImg = false;
        
  
    }
    public cancleUpload(item:any){
        item.remove();

        //this.uploader.clearQueue()
        console.log("cancleUpload");
        this.imgUpload = true;
        this.updateImg = true;
    }
    public UpdateImage(){
        this.updateImg = true;
        this.imgUpload = true;
        this.uploader.clearQueue();


    }



}