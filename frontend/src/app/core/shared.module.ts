import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';
import { SearchfieldComponent } from './components/searchfield/searchfield.component';

const components = [BreadcrumbComponent, SearchfieldComponent];
@NgModule({
  declarations: [...components],
  imports: [RouterModule, CommonModule],
  exports: [...components]
})
export class SharedModule { }
