import { Component, OnInit } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user/user.keycloak.model';
import { selectMe } from '../../shared/store/user/user.selector';
import { userRegister } from 'src/app/shared/store/user/user.action';

@Component({
  selector: 'rpg-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  me$: Observable<User>;
  constructor(private store: Store) { }

  ngOnInit(): void {
    this.me$ = this.store.pipe(select(selectMe))
  }
  register() {
    this.store.dispatch(userRegister({ name: "a", email: "a@b.cd", password: "password", preferredLanguageTag: "de-DE" }));
  }
}
