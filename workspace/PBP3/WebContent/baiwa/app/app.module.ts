import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { Routes, RouterModule }   from '@angular/router';
import { AppComponent }   from './app.component';
import { HttpModule } from '@angular/http';
import {CommonService} from'./service/Common.service';


import {importwork} from './baiwa/importwork.component';
import {home} from './baiwa/home.component';
import {listworktype} from './baiwa/listworktype.component';

const routes: Routes = [

    { path: 'home', component: home },
    { path: 'importwork', component: importwork },
    { path: 'ListByWorkType', component: listworktype },
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
        listworktype
    ],
    providers: [
        CommonService
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
