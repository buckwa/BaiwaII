import { Directive,Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload';

const URL = 'http://localhost:8080/PBP3/person/uploadMultiFile';

// class FileSelectDirective
@Directive({ selector: '[ng2FileSelect]' })

// // class FileDropDirective
 @Directive({ selector: '[ng2FileDrop]' })


// const URL = '/api/';
@Component({
    templateUrl: 'app/baiwa/html/importwork.component.html'
})
// class FileSelectDirective



export class importwork implements OnInit {

     
    public uploader:FileUploader = new FileUploader({url: URL});
  public hasBaseDropZoneOver:boolean = false;
  public hasAnotherDropZoneOver:boolean = false;

    // 555+
    constructor() {

    }

    ngOnInit() {

        console.log(this.uploader); 
        this.uploader.onBeforeUploadItem = (fileItem: any) => {
        // fileItem.formData.push(  'data', '33'  );
        console.log(this.uploader.onBeforeUploadItem);

        // this.uploader.onBuildItemForm = (fileItem: any, form: any) => {


        // form.append(  'data2', '2'  );
        // form.append( { 'data3': '3' } );
        // };
        }
        this.uploader.onBuildItemForm = (fileItem: any, form: any) => {


        form.append(  'data', '2'  );

        };

           
           
}

    ngAfterViewInit() {

    }


}