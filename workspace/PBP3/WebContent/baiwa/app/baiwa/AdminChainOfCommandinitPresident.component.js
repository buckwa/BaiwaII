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
var router_1 = require('@angular/router');
var AdminChainOfCommandinitPresident = (function () {
    function AdminChainOfCommandinitPresident(router, commonService, http) {
        this.router = router;
        this.commonService = commonService;
        this.http = http;
        this.makeDataTable = {
            "searching": true,
            "bPaginate": false,
            "paging": true,
            "bLengthChange": false,
            "bInfo": false,
            "bAutoWidth": false,
            "columns": [
                { "data": "username" },
                { "data": "firstLastName" },
                { "data": "academicYear" },
                {
                    data: "username",
                    className: "center",
                    "render": function (data, type, full, meta) {
                        return '<a href="javascript:void(0);"  Action="Edit" value="' + data + '" class="editor_remove">กำหนดอธิการบดี</a>';
                    }
                }
            ]
        };
    }
    AdminChainOfCommandinitPresident.prototype.ngOnInit = function () {
        this.getDatatabel1();
    };
    AdminChainOfCommandinitPresident.prototype.ngAfterViewInit = function () {
    };
    AdminChainOfCommandinitPresident.prototype.getDatatabel1 = function () {
        var _this = this;
        var url = "../admin/json/managePresident";
        this.http.get(url).subscribe(function (response) { return _this.getTimeTableSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("callsevice done !"); });
    };
    AdminChainOfCommandinitPresident.prototype.getTimeTableSucess = function (response) {
        //this.makeDataTable.data = response.json(JSON.stringify(response._body));
        this.presidents = response.json(JSON.stringify(response._body));
        this.president = this.presidents.resObj;
        this.academicYear = this.president.academicYear;
        //this.president = this.president.president;
        this.thainame = this.president.thaiName;
        this.thaiSurname = this.president.thaiSurname;
        this.resPagingBean = this.presidents.resPagingBean;
        this.currentPageItem = this.presidents.resPagingBean.currentPageItem;
        this.makeDataTable.data = this.currentPageItem;
        this.timetabletable.show();
    };
    AdminChainOfCommandinitPresident.prototype.GetPersonError = function (error) {
        console.log("call service error" + error);
    };
    AdminChainOfCommandinitPresident.prototype.handleKeyboardEvent = function (target) {
        //
        var ele = jQuery(target);
        console.log(ele.attr("value"), ele.attr("Action"));
        if (ele.attr("Action") == "Edit") {
            if (ele.attr("value")) {
                this.getSaveDatatabel1(ele.attr("value"));
            }
        }
    };
    AdminChainOfCommandinitPresident.prototype.getSaveDatatabel1 = function (response) {
        var _this = this;
        var url = "../admin/json/assignToPresident/" + response + "/WORK";
        this.http.post(url, this.president).subscribe(function (response) { return _this.SENT_ss(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log(" done !"); });
    };
    AdminChainOfCommandinitPresident.prototype.SENT_ss = function (response) {
        this.router.navigate(['/AdminChainOfCommandinit']);
    };
    __decorate([
        core_1.ViewChild('personTimeTable'), 
        __metadata('design:type', Object)
    ], AdminChainOfCommandinitPresident.prototype, "timetabletable", void 0);
    __decorate([
        core_1.HostListener('click', ['$event.target']), 
        __metadata('design:type', Function), 
        __metadata('design:paramtypes', [Object]), 
        __metadata('design:returntype', void 0)
    ], AdminChainOfCommandinitPresident.prototype, "handleKeyboardEvent", null);
    AdminChainOfCommandinitPresident = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminChainOfCommandinitPresident.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, Common_service_1.CommonService, http_1.Http])
    ], AdminChainOfCommandinitPresident);
    return AdminChainOfCommandinitPresident;
}());
exports.AdminChainOfCommandinitPresident = AdminChainOfCommandinitPresident;
// 
//# sourceMappingURL=AdminChainOfCommandinitPresident.component.js.map