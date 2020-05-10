import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { userLoadMe } from 'src/app/core/store/user/user.actions';
import { selectCurrentUser } from 'src/app/core/store/user/user.selectors';
import { User } from 'src/app/model/user/user.keycloak.model';

@Component({
  selector: 'rpg-sidebar-header',
  templateUrl: './sidebar-header.component.html'
})
export class SidebarHeaderComponent implements OnInit {

  me$: Observable<User>;
  constructor(private store$: Store) { }

  ngOnInit(): void {

    this.store$.dispatch(userLoadMe());
    this.me$ = this.store$.pipe(select(selectCurrentUser));
  }

}
