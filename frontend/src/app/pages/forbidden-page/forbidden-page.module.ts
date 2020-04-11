import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ForbiddenPageComponent } from './forbidden-page.component';
import { ForbiddenRoutingModule } from './forbidden-page-routing.module';



@NgModule({
  declarations: [ForbiddenPageComponent],
  imports: [
    CommonModule, ForbiddenRoutingModule
  ]
})
export class ForbiddenPageModule { }
