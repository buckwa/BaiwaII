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
var personTimeTable = (function () {
    function personTimeTable(commonService, http) {
        this.commonService = commonService;
        this.http = http;
        this.makeDataTable = {
            "searching": false,
            "bPaginate": false,
            "paging": false,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "columns": [
                { "data": "subjectCode" },
                { "data": "subjectName" },
                { "data": "lecOrPrac" },
                { "data": "teachHr" },
                { "data": "credit" },
                { "data": "degreeStr" },
                { "data": "totalStudent" },
                { "data": "secNo" },
                { "data": "teachDayStr" },
                { "data": "remark" }
            ]
        };
        this.makeDataTable2 = {
            "searching": false,
            "bPaginate": false,
            "paging": false,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "columns": [
                { "data": "subjectCode" },
                { "data": "subjectName" },
                { "data": "lecOrPrac" },
                { "data": "teachHr" },
                { "data": "credit" },
                { "data": "degreeStr" },
                { "data": "totalStudent" },
                { "data": "secNo" },
                { "data": "teachDayStr" },
                { "data": "remark" }
            ]
        };
    }
    personTimeTable.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    personTimeTable.prototype.getDatatabel1 = function () {
        var _this = this;
        var url = "../personTimeTable/getTimeTable/" + this.acdemicyear + "/" + this.user.userName + "/1";
        this.http.get(url).subscribe(function (response) { return _this.getTimeTableSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("callsevice done !"); });
    };
    personTimeTable.prototype.getDatatabel2 = function () {
        var _this = this;
        var url = "../personTimeTable/getTimeTable/" + this.acdemicyear + "/" + this.user.userName + "/2";
        this.http.get(url).subscribe(function (response) { return _this.getTimeTableSucess2(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("callsevice done !"); });
    };
    personTimeTable.prototype.getTimeTableSucess = function (response) {
        this.makeDataTable.data = response.json(JSON.stringify(response._body));
        this.timetabletable.show();
    };
    personTimeTable.prototype.getTimeTableSucess2 = function (response) {
        this.makeDataTable2.data = response.json(JSON.stringify(response._body));
        this.timetabletable2.show();
    };
    personTimeTable.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        this.http.get(url).subscribe(function (response) { return _this.GetuserSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("callsevice done !"); });
    };
    personTimeTable.prototype.GetuserSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.year = this.user.academicYearList;
        //this.year = ["2557","2558","2559"];
        this.acdemicyear = this.user.currentAcademicYear;
        this.getDatatabel1();
        this.getDatatabel2();
        return true;
    };
    personTimeTable.prototype.GetPersonError = function (error) {
        console.log("call service error" + error);
    };
    personTimeTable.prototype.changYear = function (value) {
        this.acdemicyear = value;
        console.log("change year" + value);
        this.getDatatabel1();
        this.getDatatabel2();
        //this.year = this.repositories.find(repository => repository.name === this.selectedRepositoryName);
    };
    __decorate([
        core_1.ViewChild('personTimeTable'), 
        __metadata('design:type', Object)
    ], personTimeTable.prototype, "timetabletable", void 0);
    __decorate([
        core_1.ViewChild('personTimeTable2'), 
        __metadata('design:type', Object)
    ], personTimeTable.prototype, "timetabletable2", void 0);
    personTimeTable = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/personTimeTable.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], personTimeTable);
    return personTimeTable;
}());
exports.personTimeTable = personTimeTable;
//# sourceMappingURL=personTimeTable.component.js.map