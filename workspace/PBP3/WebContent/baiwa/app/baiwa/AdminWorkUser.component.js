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
var AdminWorkUser = (function () {
    function AdminWorkUser(commonService, http) {
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
                { "data": "username" },
                { "data": "firstLastName" },
                { "data": "facultyDesc" },
                { "data": "departmentDesc" },
                { "data": "employeeType" },
                { "data": "academicYear" },
                {
                    data: null,
                    className: "center",
                    defaultContent: '<a href="" class="editor_edit">Edit</a> / <a href="" class="editor_remove">Delete</a>'
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
        this.timetabletable.show();
    };
    AdminWorkUser.prototype.GetPersonError = function (error) {
        console.log("call service error" + error);
    };
    __decorate([
        core_1.ViewChild('personTimeTable'), 
        __metadata('design:type', Object)
    ], AdminWorkUser.prototype, "timetabletable", void 0);
    AdminWorkUser = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminWorkUser.component.html'
        }), 
        __metadata('design:paramtypes', [Common_service_1.CommonService, http_1.Http])
    ], AdminWorkUser);
    return AdminWorkUser;
}());
exports.AdminWorkUser = AdminWorkUser;
//# sourceMappingURL=AdminWorkUser.component.js.map