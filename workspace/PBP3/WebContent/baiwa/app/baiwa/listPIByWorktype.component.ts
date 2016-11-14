import { Component, ViewChild ,OnInit} from '@angular/core';

import { Router, ActivatedRoute,NavigationCancel  } from '@angular/router';

import { URLSearchParams, Http} from '@angular/http';

@Component({
    templateUrl: 'app/baiwa/html/listKPIByWorktype.component.html'
})
export class listKPIByWorktype implements OnInit {

    public acadenmicwork:any;
    public code:any;
    private sub: any;
    private year:any;
    public listacadamicwork:any;
    public academicKPIList:any[];
    constructor(private route: ActivatedRoute,private http:Http){
        
    }

    
   ngOnInit(){
       this.sub = this.route.params.subscribe(params => {
       this.code = +params['code']; // (+) converts string 'id' to a number
       this.year = +params['year'];
       // In a real app: dispatch action to load the details here.
    });
    this.getAcademicList();
       
    }

    public blackpage(){
        window.location.href = "#/anonymous";
        
    }
    public getAcademicList(){

        var url = "../person/anonymous/listKPIByWorktype/"+this.code+"/"+this.year
        this.http.get(url).subscribe(response => this.getlistSucess(response),
            error => this.dataError(error), () => console.log("editdone !")
        );

    }
    getlistSucess(response:any){
        this.listacadamicwork = response.json(JSON.stringify(response._body));
        this.academicKPIList = this.listacadamicwork.academicKPIList;
        console.log("getlistSucess")
    }
     dataError(error:any){
        console.log("geterror"+error);
    }

}