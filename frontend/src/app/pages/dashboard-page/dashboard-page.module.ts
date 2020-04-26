import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-page-routing.module';
import { DashboardPageComponent } from './dashboard-page.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { SidebarMainComponent } from './components/sidebar-main/sidebar-main.component';
import { SidebarSubComponent } from './components/sidebar-sub/sidebar-sub.component';
import { SidebarFooterComponent } from './components/sidebar-footer/sidebar-footer.component';
import { SidebarHeaderComponent } from './components/sidebar-header/sidebar-header.component';
import { SharedModule } from 'src/app/core/shared.module';

@NgModule({
  declarations: [
    DashboardPageComponent,
    SidebarComponent,
    SidebarMainComponent,
    SidebarSubComponent,
    SidebarFooterComponent,
    SidebarHeaderComponent
  ],
  imports: [CommonModule, DashboardRoutingModule, SharedModule],
  exports: [DashboardPageComponent]
})
export class DashboardPageModule {}
