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
var AdminWorkUser = (function () {
    function AdminWorkUser(router, commonService, http) {
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
                { "data": "facultyDesc" },
                { "data": "departmentDesc" },
                { "data": "employeeType" },
                { "data": "academicYear" },
                {
                    data: "username",
                    className: "center",
                    "render": function (data, type, full, meta) {
                        return '<a href="javascript:void(0);"  Action="Edit" value="' + data + '" class="editor_remove">Edit</a> / <a href="javascript:void(0);" Action="DELETE" value="' + data + '" class="editor_remove">Delete</a>';
                    }
                }
            ]
        };
    }
    AdminWorkUser.prototype.ngOnInit = function () {
        this.getDatatabel1();
    };
    AdminWorkUser.prototype.getDatatabel1 = function () {
        var _this = this;
        var url = "../admin/json/GetUserlist";
        this.http.get(url).subscribe(function (response) { return _this.getTimeTableSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("callsevice done !"); });
    };
    AdminWorkUser.prototype.getTimeTableSucess = function (response) {
        this.datalist = response.json(JSON.stringify(response._body));
        this.makeDataTable.data = this.datalist[0].currentPageItem;
        this.page = this.datalist[0];
        this.timetabletable.show();
    };
    AdminWorkUser.prototype.GetPersonError = function (error) {
        console.log("call service error" + error);
    };
    AdminWorkUser.prototype.handleKeyboardEvent = function (target) {
        //
        var ele = jQuery(target);
        console.log(ele.attr("value"), ele.attr("Action"));
        if (ele.attr("Action") == "Edit") {
            if (ele.attr("value")) {
                this.router.navigate(['/AdminUserEdit', ele.attr("value")]);
            }
        }
        else {
            if (ele.attr("Action") == "DELETE") {
                if (ele.attr("value")) {
                    this.DeleteDataUser(ele.attr("value"));
                }
            }
        }
    };
    AdminWorkUser.prototype.DeleteDataUser = function (Username) {
        this.commonService.confirm("Are you sure you want to delete?", jQuery.proxy(function (isOk) {
            var _this = this;
            if (isOk) {
                //action
                var url = "../admin/json/deleteUser/" + Username + "/work";
                this.http.post(url, this.page).subscribe(function (response) { return _this.getTimeTableSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("callsevice done !"); });
                //this.router.navigate(['/AdminWorkUser']);
                location.reload();
            }
        }, this));
    };
    __decorate([
        core_1.ViewChild('personTimeTable'), 
        __metadata('design:type', Object)
    ], AdminWorkUser.prototype, "timetabletable", void 0);
    __decorate([
        core_1.ViewChild('name'), 
        __metadata('design:type', Object)
    ], AdminWorkUser.prototype, "name", void 0);
    __decorate([
        core_1.HostListener('click', ['$event.target']), 
        __metadata('design:type', Function), 
        __metadata('design:paramtypes', [Object]), 
        __metadata('design:returntype', void 0)
    ], AdminWorkUser.prototype, "handleKeyboardEvent", null);
    AdminWorkUser = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminWorkUser.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.Router, Common_service_1.CommonService, http_1.Http])
    ], AdminWorkUser);
    return AdminWorkUser;
}());
exports.AdminWorkUser = AdminWorkUser;
//# sourceMappingURL=AdminWorkUser.component.js.map