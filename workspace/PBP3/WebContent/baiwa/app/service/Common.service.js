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
require('rxjs/add/operator/map');
var CommonService = (function () {
    function CommonService(http) {
        this.http = http;
        this.loadingOpt = {
            message: '<div class="blockui-default-message" ><i class="fa fa-circle-o-notch fa-spin"></i><h6>Please Wait</h6></div>',
            overlayCSS: {
                background: 'rgba(24, 44, 68, 0.8)',
                opacity: 1,
                cursor: 'wait'
            },
            css: {
                width: '50%'
            },
            blockMsgClass: 'block-msg-default'
        };
        this.SESSION_TIMEOUT_MSG = "Session timeout.";
    }
    CommonService.prototype.alert = function (msg, title) {
        var session_msg = this.SESSION_TIMEOUT_MSG;
        swal({
            title: (title) ? title : "",
            text: msg,
            type: "warning",
            showCancelButton: false,
            cancelButtonClass: "btn-default",
            confirmButtonClass: "btn-default",
            confirmButtonText: "OK",
            imageSize: "60x60",
            animation: false,
            closeOnConfirm: true,
            allowEscapeKey: false
        }, function () {
            if (session_msg == msg) {
                window.location.href = contextPath + "login.htm";
            }
        });
    };
    CommonService.prototype.confirm = function (msg, fnCallBack) {
        swal({
            title: "",
            text: msg,
            type: "warning",
            showCancelButton: true,
            cancelButtonClass: "btn-default",
            confirmButtonClass: "btn-default",
            confirmButtonText: "No",
            cancelButtonText: "Yes",
            closeOnConfirm: true,
            allowEscapeKey: false
        }, function (isOk) {
            fnCallBack(!isOk);
        });
    };
    CommonService.prototype.callService = function (controllerUrl, data) {
        var headers = new http_1.Headers();
        var url = contextPath + controllerUrl;
        var session_msg = this.SESSION_TIMEOUT_MSG;
        var general_msg = this.getMessage("ERR_CONNECTION_REFUSED");
        console.log("callService", url);
        headers.append('Content-Type', "application/json; charset=utf-8");
        return this.http.post(url, data).map(function (response) {
            var json = { "code": "1", "errorDesrciption": general_msg };
            var textResponse = response.text();
            try {
                json = JSON.parse(textResponse);
            }
            catch (error) {
                // window.location.href = contextPath + "login.htm";
                console.error("callService Error : JSON.parse", error);
                if (-1 != textResponse.indexOf("login")) {
                    json["errorDesrciption"] = session_msg;
                }
            }
            return json;
        });
    };
    CommonService.prototype.getPDFViewer = function (controllerUrl, data) {
        this.loading();
        //popup
        var urlViewer = contextPath + 'resources/pdfjs/viewer.html?file=';
        // console.log(this.ww);
        if (this.ww != undefined && !this.ww.closed) {
            this.ww.close();
        }
        this.ww = window.open(urlViewer, "pdf viewer", "menubar=no,status=no,titlebar=no,toolbar=no,width=800,height=600");
        //Ajax
        var headers = new http_1.Headers();
        var url = contextPath + controllerUrl;
        console.log("getPDFData", url);
        headers.append('Content-Type', "application/json; charset=utf-8");
        //Wait for popup ready
        jQuery(this.ww).ready(jQuery.proxy(function () {
            var _this = this;
            console.log("PoPUP OK");
            this.http.post(url, data).map(function (response) { return JSON.parse(response.text()); })
                .subscribe(function (response) { return _this.searchSuccess(response); }, function (error) { return _this.searchError(); }, function () { return console.log("done !"); });
        }, this));
    };
    CommonService.prototype.searchSuccess = function (response) {
        // console.log(response);
        if (response.code == "0") {
            var raw = window.atob(response.responeData);
            var rawLength = raw.length;
            var array = new Uint8Array(new ArrayBuffer(rawLength));
            for (var i = 0; i < rawLength; i++) {
                array[i] = raw.charCodeAt(i);
            }
            try {
                this.ww.PDFView.open(array);
            }
            catch (error) {
                console.log("PDFView.open Error", error);
                this.ww.close();
            }
        }
        else {
            this.alert(response.errorDesrciption);
            this.ww.close();
        }
        this.unLoading();
    };
    CommonService.prototype.searchError = function () {
        this.unLoading();
        this.ww.close();
        this.alert("GET PDF Error");
    };
    // -------------------------------------END REPORT -------------------------------------
    CommonService.prototype.loading = function () {
        jQuery('body').block(this.loadingOpt);
    };
    CommonService.prototype.unLoading = function () {
        jQuery('body').unblock();
    };
    CommonService.prototype.isEmpty = function (obj) {
        return jQuery.trim(obj) == "";
    };
    CommonService.prototype.putSessionStorage = function (key, value) {
        // sessionStorage[key] = JSON.stringify(value);
        if (value) {
            sessionStorage.setItem(key, JSON.stringify(value));
        }
        else {
            sessionStorage.setItem(key, null);
        }
    };
    CommonService.prototype.getSessionStorage = function (key) {
        if (jQuery.trim(sessionStorage.getItem(key)) == "") {
            return "";
        }
        else {
            return JSON.parse(sessionStorage.getItem(key));
        }
    };
    CommonService.prototype.getMessage = function (key) {
        var str = messages[key];
        // console.log(str,messages);
        if (!str) {
            str = "Undefine : " + key;
        }
        return str;
    };
    // ---------------------------- LOV----------------------------
    CommonService.prototype.getLovByType = function (lovtype) {
        return LOV_MASTER[jQuery.trim(lovtype)];
    };
    //-------------------------ROLE--------------------------------
    CommonService.prototype.hasAuthority = function (role) {
        for (var _i = 0, PAGE_ROLE_LIST_1 = PAGE_ROLE_LIST; _i < PAGE_ROLE_LIST_1.length; _i++) {
            var item = PAGE_ROLE_LIST_1[_i];
            if (role == item["role"])
                return true;
        }
        return false;
    };
    //------------------------ GET ----------------------------------
    CommonService.prototype.callGET = function (requestUrlGet) {
        console.log("callGET requestUrlGet: ", requestUrlGet);
        return this.http.get(contextPath + requestUrlGet)
            .map(this.extractData);
    };
    CommonService.prototype.extractData = function (res) {
        var body = res.json();
        // console.log("extractData", res);
        // console.log("body", body);
        return body || null;
    };
    CommonService.prototype.imageLocalPath = function () {
        var localpath = "D:/";
    };
    CommonService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], CommonService);
    return CommonService;
}());
exports.CommonService = CommonService;
//# sourceMappingURL=Common.service.js.map