import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { Routes, RouterModule }   from '@angular/router';
import { AppComponent }   from './app.component';
import { HttpModule } from '@angular/http';
import { CommonService } from'./service/Common.service';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload/ng2-file-upload';
import {importwork} from './baiwa/importwork.component';
import {home} from './baiwa/home.component';
import {listworktype} from './baiwa/listworktype.component';
import {AcademicWork} from './baiwa/AcademicWork.component';
import {anonymous} from './baiwa/anonymous.component';
import {listKPIByWorktype} from './baiwa/listPIByWorktype.component';
import {personTimeTable} from './baiwa/personTimeTable.component';
import {userManual} from './baiwa/userManual.component';
import {personYearReport} from './baiwa/personYearReport.component';//ยุง
import {personReportInit} from './baiwa/personReportInit.component';//ยุง
import {personReport}     from './baiwa/personReport.component';//ยุง
import {workTypeBarChart} from './baiwa/workTypeBarChart.component';//ยุง
import {work} from './baiwa/work.component';//ยุง
import {userCreate} from './baiwa/userCreate.component';//ยุง
import { notificationsList} from './baiwa/notificationsList.component';//ยุง
import { notificationsDetail} from './baiwa/notificationsDetail.component';//ยุง
import { barChart} from './baiwa/barChart.component';
import { headWorkTypeBarChart} from './baiwa/headWorkTypeBarChart.component';
import { InitApporve} from './baiwa/initApprove.component';
import { approvework} from './baiwa/approvework.component';
import { FacultyReport} from './baiwa/FacultyReport.component';
import { deanBarChart } from './baiwa/deanBarChart.component';
import { deanWorkTypeBarChart } from './baiwa/deanWorkTypeBarChart.component';
import { departmentBarChart } from './baiwa/departmentBarChart.component';
import { workTypeCompareBarChart } from './baiwa/workTypeCompareBarChart.component';
import { FileSelectDirective } from 'ng2-file-upload';
import { FileDropDirective } from 'ng2-file-upload';
import { ImagePreview} from './service/image_preview.directive';
import { MaxLengthPipe} from './maxLength.pipe';
import {jQueryDataTableDirective} from './datatable.directive';
import { AdminChainOfCommandinit } from './baiwa/AdminChainOfCommandinit.component';//ยุง
import { AdminChainOfCommandinitPresident } from './baiwa/AdminChainOfCommandinitPresident.component';//ยุง
import { AdminChainOfCommandinitHead } from './baiwa/AdminChainOfCommandinitHead.component';//ยุง
import { AdminChainOfCommandinitDean } from './baiwa/AdminChainOfCommandinitDean.component';//ยุง
import { AdminWorkUser } from './baiwa/AdminWorkUser.component';//ยุง
import { AdminUserCreate} from './baiwa/AdminUserCreate.component';//ยุง
import { AdminUserEdit} from './baiwa/AdminUserEdit.component';//ยุง



import { AdminFaculty } from './baiwa/AdminFaculty.component';//มอส
import { AdminFacultyCreate } from './baiwa/AdminFacultyCreate.component';//มอส
import { AdminFacultycreateDepartment } from './baiwa/AdminFacultycreateDepartment.component';//มอส
import { AdminFacultyeditFaculty } from './baiwa/AdminFacultyeditFaculty.component';//มอส
import { AdminFacultyEditDepartment } from './baiwa/AdminFacultyEditDepartment.component';//มอส

import { AdminAcademicKPI } from './baiwa/AdminAcademicKPI.component';
import { AdminAcademicKPIcreate } from './baiwa/AdminAcademicKPIcreate.component';
import { AdminAcademicKPIedit } from './baiwa/AdminAcademicKPIedit.component';
import { AdminAcademicYear } from './baiwa/AdminAcademicYear.component';
import { AdminEvaluateRoundinit } from './baiwa/AdminEvaluateRoundinit.component';
import { AdminAcademicYeareditDateEvaluateRound } from './baiwa/AdminAcademicYeareditDateEvaluateRound.component';
import { AdminmarkRankinit } from './baiwa/AdminmarkRankinit.component';
import { AdminmarkRankinitEditRound } from './baiwa/AdminmarkRankinitEditRound.component';
import { AdminpBPWorkTypeinit } from './baiwa/AdminpBPWorkTypeinit.component';

const routes: Routes = [

    { path: 'home', component: home },
    { path: 'importwork', component: importwork },
    { path: 'ListByWorkType', component: listworktype },
    { path: 'AcademicWork', component: AcademicWork },
    { path: 'anonymous',component:anonymous},
    { path: 'listPIByWorktype/:code/:year',component:listKPIByWorktype},
    { path: 'personTimeTable',component:personTimeTable},
    { path: 'userManual',component:userManual},

    { path: 'personYearReport', component: personYearReport },//ยุง
    { path: 'personReportInit', component: personReportInit },//ยุง
    { path: 'workTypeBarChart', component: workTypeBarChart },//ยุง
    { path: 'personReport', component: personReport },//ยุง
    { path: 'work', component: work },//ยุง
    { path: 'userCreate', component: userCreate },//ยุง
    { path: 'notificationsList', component: notificationsList },//ยุง
    { path: 'notificationsDetail/:code', component: notificationsDetail },//ยุง
    { path: '', component: home },
    { path: 'barchart',component:barChart},
    { path: 'headWorkTypeBarChart' , component:headWorkTypeBarChart},
    { path: 'initApprove' , component:InitApporve},
    { path: 'approvework/:email/:rond', component:approvework},
    { path: 'facultyReport' , component:FacultyReport},
    { path: 'deanBarChart' , component:deanBarChart},
    { path: 'deanWorkTypeBarChart' , component:deanWorkTypeBarChart},
    { path: 'departmentBarChart' , component:departmentBarChart},
    { path: 'workTypeCompareBarChart' , component:workTypeCompareBarChart},
    { path: 'AdminChainOfCommandinit' , component:AdminChainOfCommandinit},
    { path: 'AdminChainOfCommandinitPresident' , component:AdminChainOfCommandinitPresident},
    { path: 'AdminChainOfCommandinitHead/:result' , component:AdminChainOfCommandinitHead},
    { path: 'AdminChainOfCommandinitDean/:result' , component:AdminChainOfCommandinitDean},
    { path: 'AdminWorkUser' , component:AdminWorkUser},
    { path: 'AdminUserCreate' , component:AdminUserCreate},
    { path: 'AdminUserEdit/:user' , component:AdminUserEdit},

    { path: 'AdminFaculty', component: AdminFaculty },//มอส
    { path: 'AdminFacultycreateDepartment/:Faculty_id', component: AdminFacultycreateDepartment },//มอส
    { path: 'AdminFacultyCreate/:year', component: AdminFacultyCreate },//มอส
    { path: 'AdminFacultyeditFaculty/:FacultyID', component: AdminFacultyeditFaculty },//มอส
    { path: 'AdminFacultyEditDepartment/:Depart_Id', component:AdminFacultyEditDepartment },//มอส

    { path: 'AdminAcademicKPI', component: AdminAcademicKPI },
    { path: 'AdminAcademicKPIcreate/:workTypeCode/:academicYear/:facultyCode', component: AdminAcademicKPIcreate },
    { path: 'AdminAcademicKPIedit/:academicKPIId', component: AdminAcademicKPIedit },
    { path: 'AdminAcademicYear', component: AdminAcademicYear },
    { path: 'AdminEvaluateRoundinit', component: AdminEvaluateRoundinit },
    { path: 'AdminAcademicYeareditDateEvaluateRound', component: AdminAcademicYeareditDateEvaluateRound },
    { path: 'AdminmarkRankinit', component: AdminmarkRankinit },
    { path: 'AdminmarkRankinitEditRound', component: AdminmarkRankinitEditRound },
    { path: 'AdminpBPWorkTypeinit', component: AdminpBPWorkTypeinit },

];

@NgModule({
    imports: [
        HttpModule,
        BrowserModule,
        FormsModule,
        RouterModule.forRoot(routes, { useHash: true })
    ],
    declarations: [
        AppComponent,
        home,
        importwork,
        listworktype,
        AcademicWork,
        anonymous,
        listKPIByWorktype,
        personTimeTable,
        userManual,
        personYearReport,//ยุง
        personReportInit,//ยุง
        workTypeBarChart,//ยุง
        personReport,//ยุง
        work,//ยุง
        notificationsList,//ยุง
        notificationsDetail,//ยุง
        userCreate,//ยุง
        FileSelectDirective,//ยุง
        FileDropDirective,
        ImagePreview,
        jQueryDataTableDirective,
        barChart,
        headWorkTypeBarChart,
        InitApporve,
        approvework,
        FacultyReport,
        deanBarChart,
        deanWorkTypeBarChart,
        departmentBarChart,
        workTypeCompareBarChart,
        MaxLengthPipe,
        AdminChainOfCommandinit,
        AdminChainOfCommandinitPresident,
        AdminChainOfCommandinitHead,
        AdminChainOfCommandinitDean,
        AdminWorkUser,
        AdminUserCreate,
        AdminUserEdit,
        AdminFaculty,//มอส
        AdminFacultyCreate,//มอส
        AdminFacultycreateDepartment,//มอส
        AdminFacultyeditFaculty,//มอส
        AdminFacultyEditDepartment,//มอส
        AdminAcademicKPI,//มอส
        AdminAcademicKPIcreate,//มอส
        AdminAcademicKPIedit,//มอส
        AdminAcademicYear,//มอส
        AdminEvaluateRoundinit,//มอส
        AdminAcademicYeareditDateEvaluateRound,//มอส
        AdminmarkRankinit,//มอส
        AdminmarkRankinitEditRound,//มอส
        AdminpBPWorkTypeinit,

    ],
    providers: [
        CommonService,
        anonymous,
        
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
