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
var platform_browser_1 = require('@angular/platform-browser');
var forms_1 = require('@angular/forms');
var router_1 = require('@angular/router');
var app_component_1 = require('./app.component');
var http_1 = require('@angular/http');
var Common_service_1 = require('./service/Common.service');
var ng2_file_upload_1 = require('ng2-file-upload/ng2-file-upload');
var importwork_component_1 = require('./baiwa/importwork.component');
var home_component_1 = require('./baiwa/home.component');
var listworktype_component_1 = require('./baiwa/listworktype.component');
var AcademicWork_component_1 = require('./baiwa/AcademicWork.component');
var anonymous_component_1 = require('./baiwa/anonymous.component');
var listPIByWorktype_component_1 = require('./baiwa/listPIByWorktype.component');
var personTimeTable_component_1 = require('./baiwa/personTimeTable.component');
var userManual_component_1 = require('./baiwa/userManual.component');
var personYearReport_component_1 = require('./baiwa/personYearReport.component'); //ยุง
var personReportInit_component_1 = require('./baiwa/personReportInit.component'); //ยุง
var personReport_component_1 = require('./baiwa/personReport.component'); //ยุง
var workTypeBarChart_component_1 = require('./baiwa/workTypeBarChart.component'); //ยุง
var work_component_1 = require('./baiwa/work.component'); //ยุง
var userCreate_component_1 = require('./baiwa/userCreate.component'); //ยุง
var notificationsList_component_1 = require('./baiwa/notificationsList.component'); //ยุง
var notificationsDetail_component_1 = require('./baiwa/notificationsDetail.component'); //ยุง
var barChart_component_1 = require('./baiwa/barChart.component');
var headWorkTypeBarChart_component_1 = require('./baiwa/headWorkTypeBarChart.component');
var initApprove_component_1 = require('./baiwa/initApprove.component');
var approvework_component_1 = require('./baiwa/approvework.component');
var image_preview_directive_1 = require('./service/image_preview.directive');
var datatable_directive_1 = require('./datatable.directive');
var routes = [
    { path: 'home', component: home_component_1.home },
    { path: 'importwork', component: importwork_component_1.importwork },
    { path: 'ListByWorkType', component: listworktype_component_1.listworktype },
    { path: 'AcademicWork', component: AcademicWork_component_1.AcademicWork },
    { path: 'anonymous', component: anonymous_component_1.anonymous },
    { path: 'listPIByWorktype/:code/:year', component: listPIByWorktype_component_1.listKPIByWorktype },
    { path: 'personTimeTable', component: personTimeTable_component_1.personTimeTable },
    { path: 'userManual', component: userManual_component_1.userManual },
    { path: 'personYearReport', component: personYearReport_component_1.personYearReport },
    { path: 'personReportInit', component: personReportInit_component_1.personReportInit },
    { path: 'workTypeBarChart', component: workTypeBarChart_component_1.workTypeBarChart },
    { path: 'personReport', component: personReport_component_1.personReport },
    { path: 'work', component: work_component_1.work },
    { path: 'userCreate', component: userCreate_component_1.userCreate },
    { path: 'notificationsList', component: notificationsList_component_1.notificationsList },
    { path: 'notificationsDetail', component: notificationsDetail_component_1.notificationsDetail },
    { path: '', component: home_component_1.home },
    { path: 'barchart', component: barChart_component_1.barChart },
    { path: 'headWorkTypeBarChart', component: headWorkTypeBarChart_component_1.headWorkTypeBarChart },
    { path: 'initApprove', component: initApprove_component_1.InitApporve },
    { path: 'approvework/:email/:rond', component: approvework_component_1.approvework }
];
var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        core_1.NgModule({
            imports: [
                http_1.HttpModule,
                platform_browser_1.BrowserModule,
                forms_1.FormsModule,
                router_1.RouterModule.forRoot(routes, { useHash: true })
            ],
            declarations: [
                app_component_1.AppComponent,
                home_component_1.home,
                importwork_component_1.importwork,
                listworktype_component_1.listworktype,
                AcademicWork_component_1.AcademicWork,
                anonymous_component_1.anonymous,
                listPIByWorktype_component_1.listKPIByWorktype,
                personTimeTable_component_1.personTimeTable,
                userManual_component_1.userManual,
                personYearReport_component_1.personYearReport,
                personReportInit_component_1.personReportInit,
                workTypeBarChart_component_1.workTypeBarChart,
                personReport_component_1.personReport,
                work_component_1.work,
                notificationsList_component_1.notificationsList,
                notificationsDetail_component_1.notificationsDetail,
                userCreate_component_1.userCreate,
                ng2_file_upload_1.FileSelectDirective,
                ng2_file_upload_1.FileDropDirective,
                image_preview_directive_1.ImagePreview,
                datatable_directive_1.jQueryDataTableDirective,
                barChart_component_1.barChart,
                headWorkTypeBarChart_component_1.headWorkTypeBarChart,
                initApprove_component_1.InitApporve,
                approvework_component_1.approvework,
            ],
            providers: [
                Common_service_1.CommonService,
                anonymous_component_1.anonymous
            ],
            bootstrap: [app_component_1.AppComponent]
        }), 
        __metadata('design:paramtypes', [])
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map