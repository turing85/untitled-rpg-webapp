import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';
import { RouterModule } from '@angular/router';
import { SearchfieldComponent } from './components/searchfield/searchfield.component';

const components = [BreadcrumbComponent, SearchfieldComponent];
@NgModule({
  declarations: [...components],
  imports: [RouterModule, CommonModule],
  exports: [...components]
})
export class SharedModule {}
