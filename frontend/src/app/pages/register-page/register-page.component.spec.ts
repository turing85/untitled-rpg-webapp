import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ngrxTesting } from 'src/app/dev/testing/ngrx-testing';
import { instance, mock, when } from 'ts-mockito';
import { RegisterPageComponent } from './register-page.component';


describe('RegisterPageComponent', () => {
  let component: RegisterPageComponent;
  let fixture: ComponentFixture<RegisterPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterPageComponent],
      imports: [
        ...ngrxTesting(),
        ReactiveFormsModule
      ],
      providers: [
        FormBuilder,
      ],
    })
      .compileComponents();

  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should validate password and return valid if same', () => {
    const controlMock = mock(AbstractControl);
    when(controlMock.get('password')).thenReturn({ value: '123' } as AbstractControl);
    when(controlMock.get('password2')).thenReturn({ value: '123' } as AbstractControl);
    expect(component.passwordConfirming(instance(controlMock)).invalid).toBe(false);
  });

  it('should validate password and return invalid if not same', () => {
    const controlMock = mock(AbstractControl);
    when(controlMock.get('password')).thenReturn({ value: '123' } as AbstractControl);
    when(controlMock.get('password2')).thenReturn({ value: '1234' } as AbstractControl);
    expect(component.passwordConfirming(instance(controlMock)).invalid).toBe(true);
  });
  describe('empty', () => {
    it('should return true if field is empty and touched', () => {
      const controlMock = mock(AbstractControl);
      when(controlMock.touched).thenReturn(true);
      when(controlMock.errors).thenReturn({ required: true });
      const formMock = mock(FormGroup);
      when(formMock.controls).thenReturn({ a: instance(controlMock) });
      component.registerForm = instance(formMock);
      expect(component.empty('a')).toBe(true);
    });

    it('should return false if field is empty but untouched', () => {
      const controlMock = mock(AbstractControl);
      when(controlMock.touched).thenReturn(false);
      when(controlMock.errors).thenReturn({ required: true });
      const formMock = mock(FormGroup);
      when(formMock.controls).thenReturn({ a: instance(controlMock) });
      component.registerForm = instance(formMock);
      expect(component.empty('a')).toBe(false);
    });
  });

  describe('invalid', () => {
    it('should return false if field is not empty', () => {
      const controlMock = mock(AbstractControl);
      when(controlMock.touched).thenReturn(true);
      when(controlMock.errors).thenReturn(null);
      const formMock = mock(FormGroup);
      when(formMock.controls).thenReturn({ a: instance(controlMock) });
      component.registerForm = instance(formMock);
      expect(component.invalid('a')).toBe(false);
    });

    it('should return true if field has errors', () => {
      const controlMock = mock(AbstractControl);
      when(controlMock.touched).thenReturn(true);
      when(controlMock.errors).thenReturn({ required: false });
      const formMock = mock(FormGroup);
      when(formMock.controls).thenReturn({ a: instance(controlMock) });
      component.registerForm = instance(formMock);
      expect(component.invalid('a')).toBe(true);
    });
  });

});
