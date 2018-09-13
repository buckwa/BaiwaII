import { Component, Injectable,Input,ViewChild,OnInit,Output,EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/departmentBarChart.component.html'
})

export class departmentBarChart implements OnInit  {
		 public json: any;
		 public jsonUser: any;
    public nameDepart: any[];
    public mean1: any;
    public headDepart:any
	public departmentname:string;

	public academicYearList :any;
	public academicYear :any;

    constructor(private http: Http) {
    }
    ngOnInit() {
	   this.getUserName();
       
    }
     public getDepartment() {
        var url = "../dean/initDepartmentBarChart";
        return this.http.get(url).subscribe(response => this.GetkendoSucess(response),
            error => this.GetDepartmentNameError(error), () => console.log("DepartmentName !"));
    }
    public getUserName() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetUserNameSucess(response),
            error => this.GetDepartmentNameError(error), () => console.log("DepartmentName !"));
	}
	public GetUserNameSucess(response: any) {
		this.jsonUser = response.json(JSON.stringify(response._body));
		console.log("this.jsonUser",this.jsonUser);

		this.academicYear = this.jsonUser.currentAcademicYear;
		this.academicYearList =this.jsonUser.academicYearList ;
		this.getDepartment();
	}



    public GetkendoSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));
		this.nameDepart = this.json.resObj.faculty;
		console.log("this.json",this.json);

		
        //this.mean1 = this.json.mean1;
		// this.departmentname = this.nameDepart[0].departmentDesc

		this.headDepart = this.jsonUser.departmentName;
		this.createChart(this.headDepart,this.academicYear);
        
    }

    public GetDepartmentNameError(error: String) {
        console.log("GetDepartmentNameError.")

    }
    changSelection(value:any){
		console.log("headDepart"+this.headDepart ,"year"+this.academicYear);


        this.createChart(value,this.academicYear);

	}
	
    changSelectionYear(year:any){
        console.log("headDepart"+this.headDepart ,"year"+year);


        this.createChart( this.headDepart,year);

	}
    createChart(value:any,academicYear: String){
		


		for (var i = 0; i < this.nameDepart.length; i++) { 
			if(this.nameDepart[i].departmentDesc == value){
				value= this.nameDepart[i].email;
			}
		}


		console.log("this.nameDepart",this.nameDepart);
         jQuery("#chart").kendoChart({
                 dataSource: {
                     transport: {
                         read: {
                         	 url: "../dean/getDepartmentBarchart/"+value+"/1/"+academicYear,
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
        	            text: "ระดับคะแนนในภาควิชา"
        	        },
        	        series: [{
        	            type: "column",
        	            field: "axisValue",
        	            name: "ระดับคะแนน"
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
        	
        	 
        	 jQuery("#grid").kendoGrid({     
        		    
        		    dataSource: {
        		        transport: {
        		            read: {
        		                url:    "../dean/getDepartmentBarchart/"+value+"/1/"+academicYear,
        		                dataType: "Json"
        		            }
        		        }
        		    }
        	 ,
             chema: {
           	  data: function(response) {
           	    for (var i = 0; i < response.length; i++) {
           	      response[i].orderNo = new Number(response[i].orderNo);
           	    }
           	    return response;
           	  }
           	}    
        	 ,
        		    columns   : [        		       
        		        { field: "axisName", title: "บุคลากร" },
        		        { field: "axisValue", title: "คะแนน" }
        		    ]
        		});        	 
    }
}