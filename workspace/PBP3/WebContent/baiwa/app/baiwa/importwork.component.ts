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



export class importwork  {

     
    public uploader:FileUploader = new FileUploader({url: URL});


    // 555+
    constructor() {

    console.log('55+');
    //this.uploader.kpiUserMappingId='3333';

    }

    ngOnInit() {

        this.uploader.onBeforeUploadItem = (fileItem: any) => {
        fileItem.formData.push( { kpiUserMappingId: '33' } );


    }}

    ngAfterViewInit() {

    }


}