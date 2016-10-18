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
import { FileSelectDirective } from 'ng2-file-upload';
import { FileDropDirective } from 'ng2-file-upload';


const routes: Routes = [

    { path: 'home', component: home },
    { path: 'importwork', component: importwork },
    { path: 'ListByWorkType', component: listworktype },
    { path: 'AcademicWork', component: AcademicWork },
    { path: 'anonymous',component:anonymous},
    { path: 'listPIByWorktype',component:listKPIByWorktype},
    { path: 'personTimeTable',component:personTimeTable},
    { path: 'userManual',component:userManual},
    
    { path: '', component: home }

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
        FileSelectDirective,
        FileDropDirective
    ],
    providers: [
        CommonService
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
