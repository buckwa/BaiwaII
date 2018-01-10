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
var http_1 = require('@angular/http');
var personYearReport = (function () {
    function personYearReport(http) {
        this.http = http;
        this.libPath = "/PBP3/baiwa/libs/";
    }
    personYearReport.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    personYearReport.prototype.ngAfterViewInit = function () {
    };
    personYearReport.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        this.http.get(url).subscribe(function (response) { return _this.GetuserSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("callsevice done !"); });
    };
    personYearReport.prototype.GetuserSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        console.log('User');
        console.log(this.user);
        this.year = this.user.academicYearList;
        //this.year = ["2557","2558","2559"];
        this.acdemicyear = this.user.currentAcademicYear;
        this.GetPersonByAcadamy(this.user.userName, this.acdemicyear);
        return true;
    };
    personYearReport.prototype.GetPersonByAcadamy = function (user, year) {
        var _this = this;
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year;
        this.http.get(url).subscribe(function (response) { return _this.GetPersonSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    personYearReport.prototype.GetPersonSucess = function (response) {
        this.profile = response.json(JSON.stringify(response._body));
        console.log(this.profile);
        //this.imageProfilePath = this.sanitize(this.profile.picture)
        this.evaluateRoundValue = this.profile.evaluateRound;
        console.log(this.evaluateRoundValue);
        if (this.profile.employeeType == 'ข้าราชการ') {
            this.evaluateRoundList = this.profile.evaluateRoundList;
            console.log(this.evaluateRoundList);
        }
    };
    personYearReport.prototype.GetPersonError = function (error) {
    };
    personYearReport.prototype.changYear = function (value) {
        this.acdemicyear = value;
        console.log("change year" + value);
        //this.year = this.repositories.find(repository => repository.name === this.selectedRepositoryName);
    };
    personYearReport.prototype.clickDownload = function () {
        window.location.href = '../report/printReportYear.htm?year=' + this.acdemicyear + '&round=' + this.evaluateRoundValue;
    };
    personYearReport = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/personYearReport.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], personYearReport);
    return personYearReport;
}());
exports.personYearReport = personYearReport;
//# sourceMappingURL=personYearReport.component.js.map