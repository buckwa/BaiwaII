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
var platform_browser_1 = require('@angular/platform-browser');
var router_1 = require('@angular/router');
var AppComponent = (function () {
    function AppComponent(router, http, sanitizer) {
        this.router = router;
        this.http = http;
        this.sanitizer = sanitizer;
    }
    AppComponent.prototype.ngOnInit = function () {
        this.GetUserSession();
    };
    AppComponent.prototype.GetUserSession = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetuserSucess(response); }, function (error) { return _this.GetPersonError(error); }, function () { return console.log("editdone !"); });
    };
    AppComponent.prototype.GetuserSucess = function (response) {
        console.log("GetPersonSS.");
    };
    AppComponent.prototype.GetPersonError = function (error) {
        console.log("GetPersonError.");
        // this.router.navigate(['../../logout.htm']);
        document.location.href = '../logout.htm';
    };
    AppComponent = __decorate([
        core_1.Component({
            selector: 'my-app',
            template: "<router-outlet></router-outlet>"
        }), 
        __metadata('design:paramtypes', [router_1.Router, http_1.Http, platform_browser_1.DomSanitizer])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map