import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ForbiddenRoutingModule } from './forbidden-page-routing.module';
import { ForbiddenPageComponent } from './forbidden-page.component';



@NgModule({
  declarations: [ForbiddenPageComponent],
  imports: [
    CommonModule, ForbiddenRoutingModule
  ]
})
export class ForbiddenPageModule { }
