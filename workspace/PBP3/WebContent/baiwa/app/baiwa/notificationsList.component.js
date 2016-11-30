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
var notificationsList = (function () {
    function notificationsList(http, commonService) {
        this.http = http;
        this.commonService = commonService;
        this.libPath = "/PBP3/baiwa/libs/";
        this.messageList = this.defaultMessage();
        this.pageCount = this.pagecountD();
        this.pagingMessage = this.defaultPaging();
    }
    notificationsList.prototype.ngOnInit = function () {
    };
    notificationsList.prototype.ngAfterViewInit = function () {
        this.CountMessageAll();
    };
    notificationsList.prototype.defaultMessage = function () {
        return [{
                "createBy": "",
                "messageDetail": "",
                "messageId": "",
                "topicId": ""
            }];
    };
    notificationsList.prototype.defaultPaging = function () {
        return {
            "pageStart": "",
            "pageEnd": "",
            "totalMessage": "",
            "data": ""
        };
    };
    notificationsList.prototype.pagecountD = function () {
        return [{
                "pageStart": "",
                "pageEnd": "",
            }];
    };
    notificationsList.prototype.getMessage = function () {
        var _this = this;
        var url = "../person/getMassageAll/" + this.pagingMessage.pageStart + "/" + this.pagingMessage.pageEnd;
        this.http.get(url).subscribe(function (response) { return _this.GetMessageSucess(response); }, function (error) { return _this.GetMessageError(error); }, function () { return console.log("notificationsList"); });
    };
    notificationsList.prototype.GetMessageSucess = function (response) {
        this.messageList = response.json(JSON.stringify(response._body));
        this.commonService.unLoading();
    };
    notificationsList.prototype.GetMessageError = function (error) {
        this.commonService.unLoading();
        console.log("error");
    };
    notificationsList.prototype.CountMessageAll = function () {
        var _this = this;
        this.commonService.loading();
        var url = "../person/countMessage";
        this.http.get(url).subscribe(function (response) { return _this.countMessageSucess(response); }, function (error) { return _this.GetMessageError(error); }, function () { return console.log("editdone !"); });
    };
    notificationsList.prototype.countMessageSucess = function (response) {
        var totalMessage = response.json(JSON.stringify(response._body));
        this.totalMessage = totalMessage;
        this.pagingMessage.totalMessage = totalMessage;
        this.countPaging();
    };
    notificationsList.prototype.countPaging = function () {
        var npage;
        var a = [];
        var totalpage = this.totalMessage / 20;
        var modPage = this.totalMessage % 20;
        if (modPage > 0) {
            totalpage = totalpage + 1;
        }
        if (totalpage > 1) {
            for (var i = 1; i < totalpage; i++) {
                a.push(i);
            }
            this.start = 0;
            this.end = 20;
        }
        else {
            this.start = 0;
            this.end = totalpage;
            a.push(1);
        }
        this.page = a;
        this.pagingMessage.pageStart = this.start;
        this.pagingMessage.pageEnd = this.end;
        //console.log("page is :"+ this.page);
        this.getMessage();
    };
    notificationsList.prototype.clickPage = function (val) {
        this.commonService.loading();
        var modPage = this.totalMessage % 20;
        console.log("click value:" + val);
        if (val > 1 && val != this.page.length) {
            this.start = (val - 1) * 20;
        }
        else if (val == this.page.length) {
            this.start = (val - 1) * 20;
            this.end = modPage;
        }
        this.pagingMessage.pageStart = this.start;
        // this.pagingMessage.pageEnd = this.end
        this.getMessage();
        console.log("Strat Page :" + this.start + " EndPage:" + this.end);
    };
    notificationsList = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/notificationsList.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, Common_service_1.CommonService])
    ], notificationsList);
    return notificationsList;
}());
exports.notificationsList = notificationsList;
//# sourceMappingURL=notificationsList.component.js.map