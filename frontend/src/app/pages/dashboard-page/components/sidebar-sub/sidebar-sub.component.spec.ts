import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { SidebarSubComponent } from './sidebar-sub.component';


describe('SidebarSubComponent', () => {
  let component: SidebarSubComponent;
  let fixture: ComponentFixture<SidebarSubComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarSubComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarSubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
