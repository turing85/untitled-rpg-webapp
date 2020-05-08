import { Component, OnInit, OnChanges } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { userRegister } from 'src/app/core/store/user/user.actions';
import { FormBuilder, FormControl, Validators, AbstractControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'rpg-register-page',
  templateUrl: './register-page.component.html'
})
export class RegisterPageComponent implements OnInit {

  step = 0;
  registerForm: FormGroup;

  constructor(private store$: Store,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.registerForm = this.formBuilder.group({
      name: new FormControl('', [
        Validators.required,
        Validators.minLength(3)
      ]),
      email: new FormControl('', [
        Validators.required,
        Validators.email
      ]),
      password: new FormControl('', [
        Validators.required
      ]),
      password2: new FormControl('', [
        Validators.required
      ]),
    }, { validator: this.passwordConfirming });

  }

  passwordConfirming(c: AbstractControl): { invalid: boolean } {
    return { invalid: c.get('password').value !== c.get('password2').value };
  }

  register() {
    Object.values(this.registerForm.controls).forEach(e => e.markAsTouched());
    if (this.registerForm.status === 'VALID') {
      const { name, email, password } = (this.registerForm.value);
      this.store$.dispatch(userRegister({ name, email, password, preferredLanguageTag: 'de-DE' }));
    }
  }

  get nameInvalid(): boolean {
    return this.empty('name') || this.invalid('name');
  }

  get emailInvalid(): boolean {
    return this.empty('email') || this.invalid('email');
  }

  pwInvalid(): boolean {
    const c = this.registerForm.controls.password;
    const c2 = this.registerForm.controls.password2;
    return (c.touched || c2.touched) && c.value !== c2.value;
  }

  empty(key): boolean {
    const control = this.registerForm.controls[key];
    return control.touched && (control.errors && control.errors.required);
  }

  invalid(key): boolean {
    const control = this.registerForm.controls[key];
    return !this.empty(key) && control.touched && control.errors !== null;
  }
}
