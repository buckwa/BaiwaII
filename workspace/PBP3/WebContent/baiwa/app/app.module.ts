import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { Routes, RouterModule }   from '@angular/router';
import { AppComponent }   from './app.component';
import { HttpModule } from '@angular/http';



import {home} from './baiwa/home.component';

const routes: Routes = [

    { path: 'home', component: home },
    { path: '', component: home },

];

@NgModule({
  imports:      [ 
    	HttpModule,
        BrowserModule,
        FormsModule,
        RouterModule.forRoot(routes, { useHash: true })
   ],
  declarations: [ 
  AppComponent,

        home
       
  
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
