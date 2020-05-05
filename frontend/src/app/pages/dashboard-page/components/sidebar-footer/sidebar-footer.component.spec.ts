import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarFooterComponent } from './sidebar-footer.component';
import { ngrxTesting } from 'src/app/dev/testing/ngrx-testing';
import { IconsModule } from 'src/app/dev/testing/icons.module';

describe('SidebarFooterComponent', () => {
  let component: SidebarFooterComponent;
  let fixture: ComponentFixture<SidebarFooterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:[
        ...ngrxTesting(),
        IconsModule
      ],
      declarations: [ SidebarFooterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarFooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
