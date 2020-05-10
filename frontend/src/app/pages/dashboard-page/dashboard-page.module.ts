import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SharedModule } from 'src/app/core/shared.module';
import { SidebarFooterComponent } from './components/sidebar-footer/sidebar-footer.component';
import { SidebarHeaderComponent } from './components/sidebar-header/sidebar-header.component';
import { SidebarMainComponent } from './components/sidebar-main/sidebar-main.component';
import { SidebarSubComponent } from './components/sidebar-sub/sidebar-sub.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { DashboardRoutingModule } from './dashboard-page-routing.module';
import { DashboardPageComponent } from './dashboard-page.component';

@NgModule({
  declarations: [
    DashboardPageComponent,
    SidebarComponent,
    SidebarMainComponent,
    SidebarSubComponent,
    SidebarFooterComponent,
    SidebarHeaderComponent
  ],
  imports: [CommonModule, DashboardRoutingModule, SharedModule,
    FontAwesomeModule],
  exports: [DashboardPageComponent]
})
export class DashboardPageModule { }
