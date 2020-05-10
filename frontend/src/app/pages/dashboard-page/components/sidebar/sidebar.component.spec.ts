import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MockComponent } from 'ng-mocks';
import { SidebarFooterComponent } from '../sidebar-footer/sidebar-footer.component';
import { SidebarHeaderComponent } from '../sidebar-header/sidebar-header.component';
import { SidebarMainComponent } from '../sidebar-main/sidebar-main.component';
import { SidebarSubComponent } from '../sidebar-sub/sidebar-sub.component';
import { SidebarComponent } from './sidebar.component';


describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarComponent,
        MockComponent(SidebarHeaderComponent),
        MockComponent(SidebarFooterComponent),
        MockComponent(SidebarMainComponent),
        MockComponent(SidebarSubComponent)
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
