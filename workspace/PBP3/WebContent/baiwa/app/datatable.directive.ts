import { Directive, Input, OnInit, OnDestroy, ElementRef, DoCheck} from '@angular/core';
import { TemplateRef, ViewContainerRef } from '@angular/core';
declare var jQuery: any;

@Directive({ selector: '[jquery-datatable]' ,exportAs:'DataTableDirect' })
export class jQueryDataTableDirective implements OnInit {

    private el: HTMLElement;
    constructor(el: ElementRef) { this.el = el.nativeElement; }
    private tagId: string = "";
    public DATA_TABLE:any;
    @Input('jquery-datatable') config: any;

    ngOnInit() {
        this.tagId = (jQuery.trim(this.el.id) == "") ? "UNKNOW" : this.el.id;
        this.log("init id=" + this.el.id);
        this.log("init");

        // console.log("input" , this.config);
    }

    ngOnDestroy() {
        // Speak now or forever hold your peace
        this.log("ngOnDestroy");
    }

    public show(){
        this.makeDatable();
    }
    
    private makeDatable() {
        let defaultConfig: any = {
            "paging": true,
            "ordering": true,
            "info": true,
            "lengthChange": true,
            "searching": false,
            "destroy": true,
            scrollX: true,
            "lengthMenu": [10, 20, 50, 100],
            "pageLength": 20,
            // scrollCollapse: true,
            "initComplete": function (settings, json) {
                // console.log('DataTables has finished its initialisation.');
                setTimeout(() => {
                    table.draw(false);
                    // console.log("refresh datatable 0.5s");
                }, 800);
            }
        };

        if (this.config) {
            if (!jQuery.isPlainObject(this.config)) {
                console.log("config object only {} !!" , this.config);
            } else {
                defaultConfig = jQuery.extend({}, defaultConfig, this.config);
            }
        }

        // console.log("config", defaultConfig.data);
        let table = jQuery(this.el).DataTable(defaultConfig);
        if (jQuery.isFunction(defaultConfig.rowClick)) {
            console.log("Event row Click install ....");
            jQuery(this.el).on('click', 'tr', function () {
                let rowData = table.row(this).data();
                // console.log("click row in datatable :)");
                defaultConfig.rowClick(rowData);
            });
        }
        this.DATA_TABLE = table;
    }

    private log(...msg: any[]) {
        // console.log("[jQueryDataTableDirective] | " + this.tagId + " =>" , msg);
    }

}
