import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingPageComponent } from './landing-page.component';
import { RouterModule } from '@angular/router';
import { LandingRoutingModule } from './landing-page-routing.module';

@NgModule({
  declarations: [LandingPageComponent],
  imports: [CommonModule, RouterModule, LandingRoutingModule]
})
export class LandingPageModule {}
