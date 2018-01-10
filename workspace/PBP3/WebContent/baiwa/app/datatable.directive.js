"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var jQueryDataTableDirective = (function () {
    function jQueryDataTableDirective(el) {
        this.tagId = "";
        this.el = el.nativeElement;
    }
    jQueryDataTableDirective.prototype.ngOnInit = function () {
        this.tagId = (jQuery.trim(this.el.id) == "") ? "UNKNOW" : this.el.id;
        this.log("init id=" + this.el.id);
        this.log("init");
        // console.log("input" , this.config);
    };
    jQueryDataTableDirective.prototype.ngOnDestroy = function () {
        // Speak now or forever hold your peace
        this.log("ngOnDestroy");
    };
    jQueryDataTableDirective.prototype.show = function () {
        this.makeDatable();
    };
    jQueryDataTableDirective.prototype.makeDatable = function () {
        var defaultConfig = {
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
                setTimeout(function () {
                    table.draw(false);
                    // console.log("refresh datatable 0.5s");
                }, 800);
            }
        };
        if (this.config) {
            if (!jQuery.isPlainObject(this.config)) {
                console.log("config object only {} !!", this.config);
            }
            else {
                defaultConfig = jQuery.extend({}, defaultConfig, this.config);
            }
        }
        // console.log("config", defaultConfig.data);
        var table = jQuery(this.el).DataTable(defaultConfig);
        if (jQuery.isFunction(defaultConfig.rowClick)) {
            console.log("Event row Click install ....");
            jQuery(this.el).on('click', 'tr', function () {
                var rowData = table.row(this).data();
                // console.log("click row in datatable :)");
                defaultConfig.rowClick(rowData);
            });
        }
        this.DATA_TABLE = table;
    };
    jQueryDataTableDirective.prototype.log = function () {
        var msg = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            msg[_i - 0] = arguments[_i];
        }
        // console.log("[jQueryDataTableDirective] | " + this.tagId + " =>" , msg);
    };
    __decorate([
        core_1.Input('jquery-datatable'), 
        __metadata('design:type', Object)
    ], jQueryDataTableDirective.prototype, "config", void 0);
    jQueryDataTableDirective = __decorate([
        core_1.Directive({ selector: '[jquery-datatable]', exportAs: 'DataTableDirect' }), 
        __metadata('design:paramtypes', [core_1.ElementRef])
    ], jQueryDataTableDirective);
    return jQueryDataTableDirective;
}());
exports.jQueryDataTableDirective = jQueryDataTableDirective;
//# sourceMappingURL=datatable.directive.js.map