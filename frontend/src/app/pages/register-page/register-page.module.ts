import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterPageComponent } from './register-page.component';
import { RegisterRoutingModule } from './register-page-routing.module';
import {  ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [RegisterPageComponent],
  
  imports: [
    CommonModule,RegisterRoutingModule,ReactiveFormsModule
  ]
})
export class RegisterPageModule { }
