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
var personTimeTable = (function () {
    function personTimeTable() {
        this.makeDataTable = {
            "columns": [
                { "data": "code" },
                { "data": "name" },
                { "data": "sub" },
                { "data": "hour" },
                { "data": "point" },
                { "data": "class" },
                { "data": "student" },
                { "data": "section" },
                { "data": "time" },
                { "data": "other" }
            ]
        };
        this.data = {
            "data": [
                { "code": "1102001", "name": "Computer Programming", "sub": "T", "hour": "3", "point": "4", "class": "AA", "student": "45", "section": "1", "time": "mon", "other": "-" },
                { "code": "1102002", "name": "Computer Programming2", "sub": "T", "hour": "3", "point": "4", "class": "AA", "student": "45", "section": "1", "time": "mon", "other": "-" },
                { "code": "1102003", "name": "Computer Programming3", "sub": "T", "hour": "3", "point": "4", "class": "AA", "student": "45", "section": "1", "time": "mon", "other": "-" }
            ]
        };
    }
    personTimeTable.prototype.ngOnInit = function () {
        //this.timetable = this.data.DataTable;
        //this.makeDataTable.data = this.data;     
        this.datatable();
        this.dataTB();
    };
    personTimeTable.prototype.datatable = function () {
        this.a = jQuery("#DTable").DataTable({
            "searching": false,
            "bPaginate": false,
            "paging": false,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "ajax": "app/baiwa/jsonTimeTable.txt",
            "columns": [
                { "data": "code" },
                { "data": "name" },
                { "data": "sub" },
                { "data": "hour" },
                { "data": "point" },
                { "data": "class" },
                { "data": "student" },
                { "data": "section" },
                { "data": "time" },
                { "data": "other" }
            ]
        });
    };
    personTimeTable.prototype.dataTB = function () {
        this.a = jQuery("#D2Table").DataTable({
            "searching": false,
            "bPaginate": false,
            "paging": false,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "ajax": "app/baiwa/jsonTimeTable.txt",
            "columns": [
                { "data": "code" },
                { "data": "name" },
                { "data": "sub" },
                { "data": "hour" },
                { "data": "point" },
                { "data": "class" },
                { "data": "student" },
                { "data": "section" },
                { "data": "time" },
                { "data": "other" }
            ]
        });
    };
    __decorate([
        core_1.ViewChild('personTimeTable'), 
        __metadata('design:type', Object)
    ], personTimeTable.prototype, "namelistcstable", void 0);
    personTimeTable = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/personTimeTable.component.html'
        }), 
        __metadata('design:paramtypes', [])
    ], personTimeTable);
    return personTimeTable;
}());
exports.personTimeTable = personTimeTable;
//# sourceMappingURL=personTimeTable.component.js.map