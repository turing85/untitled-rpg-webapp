import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterPageComponent } from './register-page.component';
import { RegisterRoutingModule } from './register-page-routing.module';



@NgModule({
  declarations: [RegisterPageComponent],
  
  imports: [
    CommonModule,RegisterRoutingModule
  ]
})
export class RegisterPageModule { }
