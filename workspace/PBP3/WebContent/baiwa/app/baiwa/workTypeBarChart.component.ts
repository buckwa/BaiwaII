import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/workTypeBarChart.component.html'
})
export class workTypeBarChart  {

public libPath: string;

    constructor() {
        this.libPath = "/PBP3/baiwa/libs/";

    }
    ngOnInit() {
this.kendoChart();
       
    }
    ngAfterViewInit() {
        
    }

  public kendoChart(): void {

        	 jQuery("#chart1").kendoChart({
                 dataSource: {
                     transport: {
                         read: {
                         	 url: "../person/getWorkTypeBarchart/1",
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
        	        	max: 1000,
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
                        	 url: "../person/getWorkTypeBarchart/2",
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
                           	 url: "../person/getWorkTypeBarchart/3",
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
                           	 url: "../person/getWorkTypeBarchart/4",
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
                           	 url: "../person/getWorkTypeBarchart/5",
                               dataType: "json"
                           }
                       } 
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