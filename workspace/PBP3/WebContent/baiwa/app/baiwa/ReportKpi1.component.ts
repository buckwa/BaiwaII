import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';

declare var jQuery: any;
@Component({
    templateUrl: 'app/baiwa/html/ReportKpi1.component.html'
})
export class ReportKpi1  {
    public searchAtti: any;
    public academicYearList: any;
    public pBPWorkTypeList: any;
    public facultyList: any;
    public academicKPIList: any;
    public user: any;
    public model: any;
    public currentAcademicYear: any;
    public dataList: any;

    public ModelSearch() {
        return {
            "workTypeCode": "",
            "academicYear": "",
            "facultyName": "",
        }
    }

  
    constructor(private commonService: CommonService, private http: Http) {
                
     }
    ngOnInit() {
        this.searchAtti = this.ModelSearch();
        
        this.GetUserSession();
    }
    ngAfterViewInit() {
        
    }

    public getlistKPI() {
        //console.log("getlistKPI : Ready getlistByDepartment ");
        var url = "../admin/pbp/academicKPI/init";
        this.http.get(url).subscribe(response => this.GetlistKPISucess(response),
            error => this.GetlistKPIJsonError(error), () => console.log(" Sent Success !"));
    }

    public GetlistKPISucess(response: any) {
        
        this.model = response.json(JSON.stringify(response._body));
        this.model = this.model.resObj;
        console.log("RR");
        console.log(this.model);
        this.academicYearList = this.model.academicYearList;
        this.pBPWorkTypeList = this.model.pBPWorkTypeList;
        if (this.user.isAdmin == true) {
            this.facultyList = this.model.facultyList;
            this.searchAtti.facultyName  = "วิศวกรรมศาสตร์";
        }else{
            this.user.facultyName;

            var temp =[{
                "name": this.user.facultyName,
            }];
            this.facultyList = temp;
            this.searchAtti.facultyName  = this.user.facultyName;
        }
        
        this.searchAtti.academicYear =this.model.academicYear;
        this.searchAtti.workTypeCode =this.model.workTypeCode;

        console.log("RR 2");
        console.log(this.facultyList);
        this.createChart();
        this.kendoGrid();
    }
    public GetlistKPIJsonError(error: any) {
        
                console.log(" Error !");
    }

    public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetuserSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !"));
    }

    public GetuserSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        console.log(this.user);
        this.currentAcademicYear = this.user.currentAcademicYear;


      

        this.getlistKPI();
    }

    public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }

    public createChart(){

        console.log("../dean/getReportBarchart/"+this.searchAtti.academicYear+"/"+this.searchAtti.facultyName+"/"+this.searchAtti.workTypeCode+"");
        
        
        jQuery("#chart").kendoChart({
                dataSource: {
                    transport: {
                        read: {
                             url: "../dean/getReportBarchart/"+this.searchAtti.academicYear+"/"+this.searchAtti.facultyName+"/"+this.searchAtti.workTypeCode+"",
                            dataType: "json"
                        }
                    } 
                },
                chema: {
                    data: function(response) {
                      for (var i = 0; i < response.length; i++) {
                        response[i].orderNo = new Number(response[i].orderNo);
                      }
                      return response;
                    }
                  }                 
               ,
                   title: {
                       text: "จำนวนผลงานในภาควิชา"
                   },
                   series: [{
                       type: "column",
                       field: "axisValue",
                       name: "จำนวนผลงาน"
                   }],
                   categoryAxis: {
                       field: "axisName",
                       labels: {
                           rotation: -90
                       }
                   },
                   tooltip: {
                       visible: true,
                       template: "#= series.name #: #= value #"
                   }
               });
           
   }

   public kendoGrid() {
    var url ="../dean/getReportBarchart/"+this.searchAtti.academicYear+"/"+this.searchAtti.facultyName+"/"+this.searchAtti.workTypeCode+"";
    this.http.get(url).subscribe(response =>  this.KendoSucess(response),
        error => this.GetPersonError(error), () => console.log("getRadarPlotNewByYear1 !"));
        
    }


    public KendoSucess(response: any){
        this.dataList = response.json(JSON.stringify(response._body));
        console.log("TD");
        console.log(this.dataList);
    };




}