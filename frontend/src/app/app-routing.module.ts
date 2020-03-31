import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { AppAuthGuard } from './core/guards/app-auth-guard.guard';
import { DashboardPageModule } from './pages/dashboard-page/dashboard-page.module';
import { LandingPageModule } from './pages/landing-page/landing-page.module';

const routes: Routes = [
  {
    path: 'public',
    loadChildren: () => LandingPageModule,
  },
  {
    path: 'dashboard',
    loadChildren: () => DashboardPageModule,
    canActivate: [AppAuthGuard],
    data: { roles: [ 'user'] }
  },
  {
    path: '**',
    loadChildren: () => LandingPageModule,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AppAuthGuard]
})
export class AppRoutingModule {}
