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
var initApproveByKPI_component_1 = require('./baiwa/initApproveByKPI.component');
var approvework_component_1 = require('./baiwa/approvework.component');
var approveworkRead_component_1 = require('./baiwa/approveworkRead.component');
var FacultyReport_component_1 = require('./baiwa/FacultyReport.component');
var deanBarChart_component_1 = require('./baiwa/deanBarChart.component');
var deanWorkTypeBarChart_component_1 = require('./baiwa/deanWorkTypeBarChart.component');
var departmentBarChart_component_1 = require('./baiwa/departmentBarChart.component');
var workTypeCompareBarChart_component_1 = require('./baiwa/workTypeCompareBarChart.component');
var ng2_file_upload_1 = require('ng2-file-upload');
var ng2_file_upload_2 = require('ng2-file-upload');
var image_preview_directive_1 = require('./service/image_preview.directive');
var maxLength_pipe_1 = require('./maxLength.pipe');
var datatable_directive_1 = require('./datatable.directive');
var AdminChainOfCommandinit_component_1 = require('./baiwa/AdminChainOfCommandinit.component'); //ยุง
var AdminChainOfCommandinitPresident_component_1 = require('./baiwa/AdminChainOfCommandinitPresident.component'); //ยุง
var AdminChainOfCommandinitHead_component_1 = require('./baiwa/AdminChainOfCommandinitHead.component'); //ยุง
var AdminChainOfCommandinitDean_component_1 = require('./baiwa/AdminChainOfCommandinitDean.component'); //ยุง
var AdminWorkUser_component_1 = require('./baiwa/AdminWorkUser.component'); //ยุง
var AdminUserCreate_component_1 = require('./baiwa/AdminUserCreate.component'); //ยุง
var AdminUserEdit_component_1 = require('./baiwa/AdminUserEdit.component'); //ยุง
var AdminFaculty_component_1 = require('./baiwa/AdminFaculty.component'); //มอส
var AdminFacultyCreate_component_1 = require('./baiwa/AdminFacultyCreate.component'); //มอส
var AdminFacultycreateDepartment_component_1 = require('./baiwa/AdminFacultycreateDepartment.component'); //มอส
var AdminFacultyeditFaculty_component_1 = require('./baiwa/AdminFacultyeditFaculty.component'); //มอส
var AdminFacultyEditDepartment_component_1 = require('./baiwa/AdminFacultyEditDepartment.component'); //มอส
var AdminAcademicKPI_component_1 = require('./baiwa/AdminAcademicKPI.component');
var AdminAcademicKPIcreate_component_1 = require('./baiwa/AdminAcademicKPIcreate.component');
var AdminAcademicKPIedit_component_1 = require('./baiwa/AdminAcademicKPIedit.component');
var AdminAcademicYear_component_1 = require('./baiwa/AdminAcademicYear.component');
var AdminEvaluateRoundinit_component_1 = require('./baiwa/AdminEvaluateRoundinit.component');
var AdminAcademicYeareditDateEvaluateRound_component_1 = require('./baiwa/AdminAcademicYeareditDateEvaluateRound.component');
var AdminmarkRankinit_component_1 = require('./baiwa/AdminmarkRankinit.component');
var AdminmarkRankinitEditRound_component_1 = require('./baiwa/AdminmarkRankinitEditRound.component');
var AdminpBPWorkTypeinit_component_1 = require('./baiwa/AdminpBPWorkTypeinit.component');
var approveworkByKPI_component_1 = require('./baiwa/approveworkByKPI.component');
var markDepartmentRecalInit_component_1 = require('./baiwa/markDepartmentRecalInit.component');
var AssignHead_component_1 = require('./baiwa/AssignHead.component');
var PreviousAcademicWork_component_1 = require('./baiwa/PreviousAcademicWork.component');
var AdminAcademicYearEdit_component_1 = require('./baiwa/AdminAcademicYearEdit.component');
var ReportKpi1_component_1 = require('./baiwa/ReportKpi1.component');
var ReportKpi2_component_1 = require('./baiwa/ReportKpi2.component');
var routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
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
    { path: 'notificationsDetail/:code', component: notificationsDetail_component_1.notificationsDetail },
    { path: '', component: home_component_1.home },
    // { path: '**', component: home },
    { path: 'barchart', component: barChart_component_1.barChart },
    { path: 'headWorkTypeBarChart', component: headWorkTypeBarChart_component_1.headWorkTypeBarChart },
    { path: 'initApprove', component: initApprove_component_1.InitApporve },
    { path: 'initApproveByKPI/:workTypeCode', component: initApproveByKPI_component_1.initApproveByKPI },
    { path: 'approvework/:email/:rond', component: approvework_component_1.approvework },
    { path: 'facultyReport', component: FacultyReport_component_1.FacultyReport },
    { path: 'deanBarChart', component: deanBarChart_component_1.deanBarChart },
    { path: 'deanWorkTypeBarChart', component: deanWorkTypeBarChart_component_1.deanWorkTypeBarChart },
    { path: 'departmentBarChart', component: departmentBarChart_component_1.departmentBarChart },
    { path: 'workTypeCompareBarChart', component: workTypeCompareBarChart_component_1.workTypeCompareBarChart },
    { path: 'AdminChainOfCommandinit', component: AdminChainOfCommandinit_component_1.AdminChainOfCommandinit },
    { path: 'AdminChainOfCommandinitPresident', component: AdminChainOfCommandinitPresident_component_1.AdminChainOfCommandinitPresident },
    { path: 'AdminChainOfCommandinitHead/:result', component: AdminChainOfCommandinitHead_component_1.AdminChainOfCommandinitHead },
    { path: 'AdminChainOfCommandinitDean/:result', component: AdminChainOfCommandinitDean_component_1.AdminChainOfCommandinitDean },
    { path: 'AdminWorkUser', component: AdminWorkUser_component_1.AdminWorkUser },
    { path: 'AdminUserCreate', component: AdminUserCreate_component_1.AdminUserCreate },
    { path: 'AdminUserEdit/:user', component: AdminUserEdit_component_1.AdminUserEdit },
    { path: 'AdminFaculty', component: AdminFaculty_component_1.AdminFaculty },
    { path: 'AdminFacultycreateDepartment/:Faculty_id', component: AdminFacultycreateDepartment_component_1.AdminFacultycreateDepartment },
    { path: 'AdminFacultyCreate/:year', component: AdminFacultyCreate_component_1.AdminFacultyCreate },
    { path: 'AdminFacultyeditFaculty/:FacultyID', component: AdminFacultyeditFaculty_component_1.AdminFacultyeditFaculty },
    { path: 'AdminFacultyEditDepartment/:Depart_Id', component: AdminFacultyEditDepartment_component_1.AdminFacultyEditDepartment },
    { path: 'AdminAcademicKPI/:workTypeCode/:academicYear/:facultyCode', component: AdminAcademicKPI_component_1.AdminAcademicKPI },
    { path: 'AdminAcademicKPIcreate/:workTypeCode/:academicYear/:facultyCode', component: AdminAcademicKPIcreate_component_1.AdminAcademicKPIcreate },
    { path: 'AdminAcademicKPIedit/:academicKPIId/:workTypeCode/:academicYear/:facultyCode', component: AdminAcademicKPIedit_component_1.AdminAcademicKPIedit },
    { path: 'AdminAcademicYear', component: AdminAcademicYear_component_1.AdminAcademicYear },
    { path: 'AdminEvaluateRoundinit', component: AdminEvaluateRoundinit_component_1.AdminEvaluateRoundinit },
    { path: 'AdminAcademicYeareditDateEvaluateRound', component: AdminAcademicYeareditDateEvaluateRound_component_1.AdminAcademicYeareditDateEvaluateRound },
    { path: 'AdminmarkRankinit', component: AdminmarkRankinit_component_1.AdminmarkRankinit },
    { path: 'AdminmarkRankinitEditRound', component: AdminmarkRankinitEditRound_component_1.AdminmarkRankinitEditRound },
    { path: 'AdminpBPWorkTypeinit', component: AdminpBPWorkTypeinit_component_1.AdminpBPWorkTypeinit },
    { path: 'approveworkByKPI/:code/:status/:workcode', component: approveworkByKPI_component_1.approveworkByKPI },
    { path: 'markDepartmentRecalInit', component: markDepartmentRecalInit_component_1.markDepartmentRecalInit },
    { path: 'AssignHead', component: AssignHead_component_1.AssignHead },
    { path: 'PreviousAcademicWork', component: PreviousAcademicWork_component_1.PreviousAcademicWorkComponent },
    { path: 'ReportKpi1', component: ReportKpi1_component_1.ReportKpi1 },
    { path: 'ReportKpi2', component: ReportKpi2_component_1.ReportKpi2 },
    { path: 'approveworkRead/:email/:rond/:year', component: approveworkRead_component_1.approveworkRead },
    { path: 'adminAcademicYearEdit/:year/:startDate/:endDate', component: AdminAcademicYearEdit_component_1.AdminAcademicYearEditComponent },
    { path: "**", redirectTo: '../logout.htm' }
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
                ng2_file_upload_2.FileDropDirective,
                image_preview_directive_1.ImagePreview,
                datatable_directive_1.jQueryDataTableDirective,
                barChart_component_1.barChart,
                headWorkTypeBarChart_component_1.headWorkTypeBarChart,
                initApprove_component_1.InitApporve,
                initApproveByKPI_component_1.initApproveByKPI,
                approvework_component_1.approvework,
                FacultyReport_component_1.FacultyReport,
                deanBarChart_component_1.deanBarChart,
                deanWorkTypeBarChart_component_1.deanWorkTypeBarChart,
                departmentBarChart_component_1.departmentBarChart,
                workTypeCompareBarChart_component_1.workTypeCompareBarChart,
                maxLength_pipe_1.MaxLengthPipe,
                AdminChainOfCommandinit_component_1.AdminChainOfCommandinit,
                AdminChainOfCommandinitPresident_component_1.AdminChainOfCommandinitPresident,
                AdminChainOfCommandinitHead_component_1.AdminChainOfCommandinitHead,
                AdminChainOfCommandinitDean_component_1.AdminChainOfCommandinitDean,
                AdminWorkUser_component_1.AdminWorkUser,
                AdminUserCreate_component_1.AdminUserCreate,
                AdminUserEdit_component_1.AdminUserEdit,
                AdminFaculty_component_1.AdminFaculty,
                AdminFacultyCreate_component_1.AdminFacultyCreate,
                AdminFacultycreateDepartment_component_1.AdminFacultycreateDepartment,
                AdminFacultyeditFaculty_component_1.AdminFacultyeditFaculty,
                AdminFacultyEditDepartment_component_1.AdminFacultyEditDepartment,
                AdminAcademicKPI_component_1.AdminAcademicKPI,
                AdminAcademicKPIcreate_component_1.AdminAcademicKPIcreate,
                AdminAcademicKPIedit_component_1.AdminAcademicKPIedit,
                AdminAcademicYear_component_1.AdminAcademicYear,
                AdminEvaluateRoundinit_component_1.AdminEvaluateRoundinit,
                AdminAcademicYeareditDateEvaluateRound_component_1.AdminAcademicYeareditDateEvaluateRound,
                AdminmarkRankinit_component_1.AdminmarkRankinit,
                AdminmarkRankinitEditRound_component_1.AdminmarkRankinitEditRound,
                AdminpBPWorkTypeinit_component_1.AdminpBPWorkTypeinit,
                approveworkByKPI_component_1.approveworkByKPI,
                markDepartmentRecalInit_component_1.markDepartmentRecalInit,
                AssignHead_component_1.AssignHead,
                PreviousAcademicWork_component_1.PreviousAcademicWorkComponent,
                approveworkRead_component_1.approveworkRead,
                ReportKpi1_component_1.ReportKpi1,
                ReportKpi2_component_1.ReportKpi2,
                AdminAcademicYearEdit_component_1.AdminAcademicYearEditComponent
            ],
            providers: [
                Common_service_1.CommonService,
                anonymous_component_1.anonymous,
            ],
            bootstrap: [app_component_1.AppComponent]
        }), 
        __metadata('design:paramtypes', [])
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map