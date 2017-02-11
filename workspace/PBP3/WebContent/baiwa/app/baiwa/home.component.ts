import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { RouterModule }   from '@angular/router';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';
import {DomSanitizer} from '@angular/platform-browser';
import {Observable} from 'rxjs/Rx';
import {MaxLengthPipe} from './../maxLength.pipe';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';
declare var jQuery: any;

const URL1 = 'http://localhost:8080/PBP3/pam/person/uploadPersonProfilePicture2';


@Component({
    templateUrl: 'app/baiwa/html/home.component.html',

})
export class home implements OnInit, AfterViewInit {

    public libPath: string;
    public profile: any;
    public work: any[];
    public sumasix: any;
    public sumasix2: any;
    public user: any;
    public url: string;
    public personId: string;
    public imgUpload: boolean;
    public updateImg: boolean;
    public imageProfilePath: any;
    sanitizedUrl;
    tmpUrl;
    public messageList: any[];
    totalMessage: any;
    public maxVal: any;
    public currentAcademicYear: any;

    public uploader: FileUploader = new FileUploader({ url: URL1 });
    constructor(private router: Router, private commonService: CommonService, private http: Http, private sanitizer: DomSanitizer) {
        this.libPath = "/PBP3/baiwa/libs/";
        this.profile = this.defaultProfile();
        this.work = this.defaultWork();
        this.imgUpload = true;
        this.messageList = this.defaultMessage();

    }
    ngOnInit() {

        this.GetUserSession();

        this.uploader.queue;

        this.uploader.onBuildItemForm = (fileItem: any, form: any) => {

            console.log("PersonId :" + this.personId);
            form.append('PersonId', this.personId);

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
    defaultMessage() {
        return [{
            "createBy": "",
            "messageDetail": "",
            "messageId": "",
            "topicId": ""
        }]
    }



    public sumaryAsix() {
        this.sumasix = 0;
        this.sumasix2 = 0;
        var maxVal;
        for (var i = 0; i < this.work.length; i++) {
            this.sumasix = parseFloat(this.sumasix) + parseFloat(this.work[i].axisValue);
            this.sumasix2 = parseFloat(this.sumasix2) + parseFloat(this.work[i].axisValue2);

            if (this.work[i].axisValue > this.work[i].axisValue2 && this.work[i].axisValue > maxVal) {
                maxVal = this.work[i].axisValue;
            } else if (this.work[i].axisValue < this.work[i].axisValue2 && this.work[i].axisValue2 > maxVal) {
                maxVal = this.work[i].axisValue2;
            }
        }
        this.maxVal = maxVal;
    }
    public GetPersonByAcadamy(user: String, year: String) {
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year
        this.http.get(url).subscribe(response => this.GetPersonSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );
    }

    public GetPersonSucess(response: any) {
        this.profile = response.json(JSON.stringify(response._body));
        //this.imageProfilePath = this.sanitize(this.profile.picture)
        this.imgUpload = this.profile.picture;
        this.personId = this.profile.personId;
        this.getImageLocal(this.personId);
        this.getMessage();
        this.countMessage();

    }
    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }
    public GetRadarPlotNewSearch(year: String) {
        this.GetRadarPlotNew(this.user.userName, year, this.profile.evaluateRound);
        this.currentAcademicYear = year;
    }
    public GetRadarPlot() {
        this.GetRadarPlotNew(this.user.userName, this.currentAcademicYear, this.profile.evaluateRound);
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
        this.currentAcademicYear = this.user.currentAcademicYear;
        this.GetPersonByAcadamy(this.user.userName, this.currentAcademicYear);

        if (this.user.isAdmin == true) {
            this.router.navigate(['/AdminAcademicKPI']);
        }


        setTimeout(() => this.GetRadarPlotNew(this.user.userName, this.currentAcademicYear, this.profile.evaluateRound), 250);


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
                max: this.maxVal
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
    public changeUpload() {
        console.log("imageChange");
        this.imgUpload = false;
        this.updateImg = false;


    }

    public UploadPicture(item: any) {
        item.upload();
        if (!item.isSuccess) {
            jQuery("#myModal").modal('hide');
            //this.getImageLocal(this.personId);
            window.location.reload();
        }
        //this.uploader.clearQueue()
        console.log("uploadsucess");
    }


    public cancleUpload(item: any) {
        item.remove();

        //this.uploader.clearQueue()
        console.log("cancleUpload");
        this.imgUpload = true;
        this.updateImg = true;
    }
    public UpdateImage() {
        this.updateImg = true;
        this.imgUpload = true;
        this.uploader.clearQueue();


    }
    public sanitize(url: string) {
        return this.sanitizer.bypassSecurityTrustUrl(url);
    }

    getImage(url: string) {
        return Observable.create(observer => {
            let req = new XMLHttpRequest();
            req.open('get', url);
            req.responseType = "arraybuffer";
            req.onreadystatechange = function () {
                if (req.readyState == 4 && req.status == 200) {
                    observer.next(req.response);
                    observer.complete();
                }
            };
            req.send();
        });
    }

    public getImageLocal(personID: String) {
        // var data = {'profileImg' : profileImg}
        let url = "../person/getImageFile/" + personID;

        this.getImage(url).subscribe(imageData => {
            console.log("imageReturn :" + imageData.image);
            this.tmpUrl = URL.createObjectURL(new Blob([imageData]));
            this.imageProfilePath = this.sanitize(this.tmpUrl);
        });


        // the below will throw not implemented error
        this.http.get(url).subscribe(image => {
            console.log("imageUrl :" + image.url);
            console.log(image.arrayBuffer());
        });
    }
    public recalculate() {
        this.commonService.loading();
        var url = "../person/recalculate";
        return this.http.get(url).subscribe(response => this.recalsucess(response),
            error => this.recalError(error), () => console.log("editdone !")
        );
    }
    recalsucess(response: any) {
        this.commonService.unLoading();
        console.log("recal sucess");
        window.location.reload();
    }
    recalError(error: any) {
        this.commonService.unLoading();
        console.log("recal error");
    }
    getMessage() {
        var url = "../person/getUserMassage";

        this.http.post(url, this.profile).subscribe(response => this.GetMessageSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );

    }
    GetMessageSucess(response: any) {
        this.messageList = response.json(JSON.stringify(response._body));

    }
    countMessage() {
        var url = "../person/countMessage";
        this.http.get(url).subscribe(response => this.countMessageSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !")
        );

    }
    countMessageSucess(response: any) {
        this.totalMessage = response.json(JSON.stringify(response._body));

    }





}