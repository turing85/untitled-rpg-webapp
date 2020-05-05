import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarComponent } from './sidebar.component';
import { MockComponent } from 'ng-mocks';
import { SidebarHeaderComponent } from '../sidebar-header/sidebar-header.component';
import { SidebarFooterComponent } from '../sidebar-footer/sidebar-footer.component';
import { SidebarMainComponent } from '../sidebar-main/sidebar-main.component';
import { SidebarSubComponent } from '../sidebar-sub/sidebar-sub.component';

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
