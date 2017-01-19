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
var Common_service_1 = require('./../service/Common.service');
var http_1 = require('@angular/http');
var AdminChainOfCommandinitEdit = (function () {
    function AdminChainOfCommandinitEdit(commonService, http) {
        this.commonService = commonService;
        this.http = http;
        this.makeDataTable = {
            "searching": false,
            "bPaginate": false,
            "paging": true,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "ajax": "app/baiwa/jsonTimeTable1.txt",
            "columns": [
                { "data": "subjectCode" },
                { "data": "subjectName" },
                { "data": "lecOrPrac" },
                { "data": "teachHr" },
                { "data": "pracHr" },
                { "data": "degreeStr" },
                { "data": "totalStudent" },
                { "data": "secNo" },
                { "data": "teachDayStr" },
                { "data": "remark" }
            ]
        };
    }
    AdminChainOfCommandinitEdit.prototype.ngOnInit = function () {
        this.datatable();
    };
    AdminChainOfCommandinitEdit.prototype.datatable = function () {
        jQuery("#DTable").DataTable({
            "searching": false,
            "bPaginate": false,
            "paging": true,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "ajax": "app/baiwa/jsonTimeTable1.txt",
            "columns": [
                { "data": "code" },
                { "data": "name" },
                { "data": "sub" },
                { "data": "hour" },
            ]
        });
    };
    AdminChainOfCommandinitEdit.prototype.ngAfterViewInit = function () {
    };
    __decorate([
        core_1.ViewChild('personTimeTable'), 
        __metadata('design:type', Object)
    ], AdminChainOfCommandinitEdit.prototype, "timetabletable", void 0);
    AdminChainOfCommandinitEdit = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminChainOfCommandinitEdit.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], AdminChainOfCommandinitEdit);
    return AdminChainOfCommandinitEdit;
}());
exports.AdminChainOfCommandinitEdit = AdminChainOfCommandinitEdit;
//# sourceMappingURL=AdminChainOfCommandinitEdit.component.js.map