import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarHeaderComponent } from './sidebar-header.component';
import { ngrxTesting } from 'src/app/dev/testing/ngrx-testing';

describe('SidebarHeaderComponent', () => {
  let component: SidebarHeaderComponent;
  let fixture: ComponentFixture<SidebarHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:[
        ...ngrxTesting()
      ],
      declarations: [ SidebarHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
