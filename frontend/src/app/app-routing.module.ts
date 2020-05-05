import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppAuthGuard } from './core/guards/app-auth.guard';
import { DashboardPageModule } from './pages/dashboard-page/dashboard-page.module';
import { LandingPageModule } from './pages/landing-page/landing-page.module';
import { ForbiddenPageModule } from './pages/forbidden-page/forbidden-page.module';
import { RegisterPageModule } from './pages/register-page/register-page.module';

const routes: Routes = [
  {
    path: 'public',
    loadChildren: () => LandingPageModule
  },
  {
    path: 'dashboard',
    loadChildren: () => DashboardPageModule,
    canActivate: [AppAuthGuard],
    data: { roles: ['user'] }
  },
  {
    path: 'forbidden',
    loadChildren: () => ForbiddenPageModule
  },
  {
    path: 'register',
    loadChildren: () => RegisterPageModule
  },
  {
    path: '',
    loadChildren: () => LandingPageModule
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AppAuthGuard]
})
export class AppRoutingModule { }
