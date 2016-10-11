import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';


@Component({
    templateUrl: 'app/baiwa/html/home.component.html'
})
export class home {

public libPath :string ;

    constructor() {
        this.libPath ="/PBP3/baiwa/libs/";
        
    }
}