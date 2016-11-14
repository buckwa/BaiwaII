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
var router_1 = require('@angular/router');
var http_1 = require('@angular/http');
var listKPIByWorktype = (function () {
    function listKPIByWorktype(route, http) {
        this.route = route;
        this.http = http;
    }
    listKPIByWorktype.prototype.ngOnInit = function () {
        var _this = this;
        this.sub = this.route.params.subscribe(function (params) {
            _this.code = +params['code']; // (+) converts string 'id' to a number
            _this.year = +params['year'];
            // In a real app: dispatch action to load the details here.
        });
        this.getAcademicList();
    };
    listKPIByWorktype.prototype.blackpage = function () {
        window.location.href = "#/anonymous";
    };
    listKPIByWorktype.prototype.getAcademicList = function () {
        var _this = this;
        var url = "../person/anonymous/listKPIByWorktype/" + this.code + "/" + this.year;
        this.http.get(url).subscribe(function (response) { return _this.getlistSucess(response); }, function (error) { return _this.dataError(error); }, function () { return console.log("editdone !"); });
    };
    listKPIByWorktype.prototype.getlistSucess = function (response) {
        this.listacadamicwork = response.json(JSON.stringify(response._body));
        this.academicKPIList = this.listacadamicwork.academicKPIList;
        console.log("getlistSucess");
    };
    listKPIByWorktype.prototype.dataError = function (error) {
        console.log("geterror" + error);
    };
    listKPIByWorktype = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/listKPIByWorktype.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.ActivatedRoute, http_1.Http])
    ], listKPIByWorktype);
    return listKPIByWorktype;
}());
exports.listKPIByWorktype = listKPIByWorktype;
//# sourceMappingURL=listPIByWorktype.component.js.map