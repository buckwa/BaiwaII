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
var common_1 = require('@angular/common');
var AdminAcademicYearEditComponent = (function () {
    function AdminAcademicYearEditComponent(route, router, _location) {
        this.route = route;
        this.router = router;
        this._location = _location;
    }
    AdminAcademicYearEditComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) { return _this.year = params["year"]; });
        this.route.params.subscribe(function (params) { return _this.startDate = params["startDate"]; });
        this.route.params.subscribe(function (params) { return _this.endDate = params["endDate"]; });
        if (this.year && this.startDate && this.endDate) {
            console.log("Input Year :", this.year);
            console.log("Input StartDate :", this.startDate);
            console.log("Input EndDate :", this.endDate);
        }
    };
    AdminAcademicYearEditComponent.prototype.backClicked = function () {
        this._location.back();
    };
    AdminAcademicYearEditComponent = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/AdminAcademicYearEdit.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.ActivatedRoute, router_1.Router, common_1.Location])
    ], AdminAcademicYearEditComponent);
    return AdminAcademicYearEditComponent;
}());
exports.AdminAcademicYearEditComponent = AdminAcademicYearEditComponent;
//# sourceMappingURL=AdminAcademicYearEdit.component.js.map