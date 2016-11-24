import { Component, Injectable,Input,ViewChild,OnInit,Output,EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/deanWorkTypeBarChart.component.html'
})

export class deanWorkTypeBarChart implements OnInit  {
	     public json: any;
    public nameDepart: any;
    public mean1: any;
    constructor(private http: Http) {
    }
    ngOnInit() {
        this.DepartmentName();
    }

    public DepartmentName() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetkendoSucess(response),
            error => this.GetDepartmentNameError(error), () => console.log("DepartmentName !"));
    }

    public GetkendoSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.facultyName;
        //this.mean1 = this.json.mean1;
        this.getbarChart();
    }

    public GetDepartmentNameError(error: String) {
        console.log("GetDepartmentNameError.")

    }
    getbarChart(){

        jQuery("#chart1").kendoChart({
                 dataSource: {
                     transport: {
                         read: {
                         	 url: "../dean/getWorkTypeBarchart/1",
                             dataType: "json"
                         }
                     }
                 },
        	        title: {
        	            text: "ระดับคะแนนในภาควิชา ด้านวิชาการ"
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
        	        valueAxis: {
        	        	min: 0,
        	        	max: 10000,
        	        	majorUnit: 500
       	        	},
                    tooltip: {
                        visible: true,
                        template: "#= series.name #: #= value #"
                    }
        	    });
        	
            
        
       
       	 jQuery("#chart2").kendoChart({
                dataSource: {
                    transport: {
                        read: {
                        	 url: "../dean/getWorkTypeBarchart/2",
                            dataType: "json"
                        }
                    }
                },
       	        title: {
       	            text: "ระดับคะแนนในภาควิชา ด้านงานพัฒนาวิชาการ"
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
       	     	valueAxis: {
    	        	min: 0,
    	        	max: 1000,
    	        	majorUnit: 100
       	    	},
             tooltip: {
                 visible: true,
                 template: "#= series.name #: #= value #"
             }
       	    });
       	

          	 jQuery("#chart3").kendoChart({
                   dataSource: {
                       transport: {
                           read: {
                           	 url: "../dean/getWorkTypeBarchart/3",
                               dataType: "json"
                           }
                       }
                   },
          	        title: {
          	            text: "ระดับคะแนนในภาควิชา ด้านงานวิจัย"
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
	          	    valueAxis: {
        	        	min: 0,
        	        	max: 1000,
        	        	majorUnit: 100
          	    	},
                  tooltip: {
                      visible: true,
                      template: "#= series.name #: #= value #"
                  }
          	    });
          	

          	 jQuery("#chart4").kendoChart({
                   dataSource: {
                       transport: {
                           read: {
                           	 url: "../dean/getWorkTypeBarchart/4",
                               dataType: "json"
                           }
                       }
                   },
          	        title: {
          	            text: "ระดับคะแนนในภาควิชา ด้านงานบริการวิชาการ"
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
          	      	valueAxis: {
        	        	min: 0,
        	        	max: 1000,
        	        	majorUnit: 100
          	    	},
                  tooltip: {
                      visible: true,
                      template: "#= series.name #: #= value #"
                  }
          	    });
          	
              

          	 jQuery("#chart5").kendoChart({
                   dataSource: {
                       transport: {
                           read: {
                           	 url: "../dean/getWorkTypeBarchart/5",
                               dataType: "json"
                           }
                       },
                   },
          	        title: {
          	            text: "ระดับคะแนนในภาควิชา ด้านงานทำนุบำรุงศิลป"
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
          	      	valueAxis: {
        	        	min: 0,
        	        	max: 1000,
        	        	majorUnit: 100
          	    	},
                  tooltip: {
                      visible: true,
                      template: "#= series.name #: #= value #"
                  }
          	    });
          	

    }
}