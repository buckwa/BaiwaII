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
var router_1 = require('@angular/router');
var notificationsDetail = (function () {
    function notificationsDetail(http, route) {
        var _this = this;
        this.http = http;
        this.route = route;
        this.libPath = "/PBP3/baiwa/libs/";
        this.sub = this.route.params.subscribe(function (params) {
            _this.code = +params['code'];
            _this.messageList = _this.defaultMessage();
            _this.nMessage = _this.newMessage();
        });
    }
    notificationsDetail.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    notificationsDetail.prototype.ngAfterViewInit = function () {
        console.log("Code kpi mapping :" + this.code);
    };
    notificationsDetail.prototype.defaultMessage = function () {
        return [{
                "createBy": "",
                "messageDetail": "",
                "messageId": "",
                "topicId": ""
            }];
    };
    notificationsDetail.prototype.newMessage = function () {
        return {
            "messageId": "",
            "topicId": "",
            "messageDetail": "",
            "imagePath": "",
            "status": "",
            "userName": "",
        };
    };
    notificationsDetail.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetuserSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    notificationsDetail.prototype.GetuserSucess = function (response) {
        this.user = response.json(JSON.stringify(response._body));
        this.GetPersonByAcadamy(this.user.userName, this.user.currentAcademicYear);
        this.getMessage();
    };
    notificationsDetail.prototype.getMessage = function () {
        var _this = this;
        var url = "../person/getMassageByKPI/" + this.code;
        this.http.get(url).subscribe(function (response) { return _this.GetMessageSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    notificationsDetail.prototype.GetMessageSucess = function (response) {
        this.messageList = response.json(JSON.stringify(response._body));
        this.UpdateFlagMessage(this.messageList[0].messageId);
    };
    notificationsDetail.prototype.GetPersonError = function (error) {
        console.log("error");
    };
    notificationsDetail.prototype.replyMessage = function () {
        var _this = this;
        var url = "../person/replyMessage";
        this.nMessage.topicId = this.code;
        this.nMessage.createBy = this.profile.thaiName + " " + this.profile.thaiSurname;
        this.http.post(url, this.nMessage).subscribe(function (response) { return _this.replyMessageSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    notificationsDetail.prototype.replyMessageSucess = function (response) {
        this.nMessage.messageDetail = "";
        console.log("addmessageSucess!");
        this.getMessage();
    };
    notificationsDetail.prototype.UpdateFlagMessage = function (messageId) {
        var _this = this;
        var url = "../person/updateFlagMessage/" + messageId;
        this.http.get(url).subscribe(function (response) { return _this.updateSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    notificationsDetail.prototype.updateSucess = function (response) {
        console.log("UpdateStatus Message Sucess..");
    };
    notificationsDetail.prototype.backToHome = function () {
        window.location.href = "#/home";
    };
    notificationsDetail.prototype.GetPersonByAcadamy = function (user, year) {
        var _this = this;
        var url = "../person/getPersonByAcademicYear/" + user + "/" + year;
        this.http.get(url).subscribe(function (response) { return _this.GetPersonSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    notificationsDetail.prototype.GetPersonSucess = function (response) {
        this.profile = response.json(JSON.stringify(response._body));
    };
    notificationsDetail = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/notificationsDetail.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.ActivatedRoute])
    ], notificationsDetail);
    return notificationsDetail;
}());
exports.notificationsDetail = notificationsDetail;
//# sourceMappingURL=notificationsDetail.component.js.map