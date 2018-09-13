import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { RouterModule }   from '@angular/router';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';
import {DomSanitizer} from '@angular/platform-browser';
import {Observable} from 'rxjs/Rx';
import { Router, ActivatedRoute, NavigationCancel  } from '@angular/router';
declare var jQuery: any;



@Component({
  selector: 'my-app',
  template: `<router-outlet></router-outlet>`

})
export class AppComponent { 
  

  constructor(private router: Router, private http: Http, private sanitizer: DomSanitizer) {
   


  }
  ngOnInit() {
   
    this.GetUserSession();

  }  

  public GetUserSession() {
    var url = "../person/getUserSession";
    return this.http.get(url).subscribe(response => this.GetuserSucess(response),
        error => this.GetPersonError(error), () => console.log("editdone !"));
}
public GetuserSucess(response: any) {
  console.log("GetPersonSS.")


    
}
public GetPersonError(error: String) {
  console.log("GetPersonError.")
  
  // this.router.navigate(['../../logout.htm']);

  document.location.href = '../logout.htm';
}


}
