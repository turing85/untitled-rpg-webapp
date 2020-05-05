import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MockComponent } from 'ng-mocks';
import { DashboardPageComponent } from './dashboard-page.component';
import { BreadcrumbComponent } from 'src/app/core/components/breadcrumb/breadcrumb.component';
import { SearchfieldComponent } from 'src/app/core/components/searchfield/searchfield.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';

describe('DashboardPageComponent', () => {
  let component: DashboardPageComponent;
  let fixture: ComponentFixture<DashboardPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        DashboardPageComponent,
        MockComponent(BreadcrumbComponent),
        MockComponent(SearchfieldComponent),
        MockComponent(SidebarComponent)
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
