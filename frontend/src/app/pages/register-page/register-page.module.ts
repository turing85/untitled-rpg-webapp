import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RegisterRoutingModule } from './register-page-routing.module';
import { RegisterPageComponent } from './register-page.component';



@NgModule({
  declarations: [RegisterPageComponent],

  imports: [
    CommonModule,
    RegisterRoutingModule,
    ReactiveFormsModule
  ]
})
export class RegisterPageModule { }
