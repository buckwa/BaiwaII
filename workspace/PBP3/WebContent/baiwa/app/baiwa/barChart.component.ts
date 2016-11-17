import { Component, Injectable,Input,ViewChild,OnInit,Output,EventEmitter } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;


@Component({
    templateUrl: 'app/baiwa/html/barChart.component.html'
})

export class barChart implements OnInit  {
        constructor(private http: Http ) {
    }
    ngOnInit(){
        this.getbarChart();
    }
    getbarChart(){
        jQuery("#KendoChart").kendoChart({
                 dataSource: {
                     transport: {
                         read: {
                         	 url: "../head/getBarchart",
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
        	        valueAxis: {
        	        	min: 0,
        	        	max: 6000 ,
        	        	majorUnit: 1000 
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
        		                url:    "../head/getBarchart",
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