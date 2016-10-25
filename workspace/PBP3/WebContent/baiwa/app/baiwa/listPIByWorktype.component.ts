import { Component, ViewChild } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery;


@Component({
    templateUrl: 'app/baiwa/html/listKPIByWorktype.component.html'
})
export class listKPIByWorktype  {

    constructor(){

    }

    public blackpage(){
        window.location.href = "#/anonymous";
    }

}