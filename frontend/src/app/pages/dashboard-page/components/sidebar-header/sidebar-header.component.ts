import { Component, OnInit } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { User } from 'src/app/model/user/user.keycloak.model';
import { selectMe, selectCurrentUser, selectCurrentUserId } from '../../../../core/store/user/user.selector';
import { Observable } from 'rxjs';
import { userLoadMe } from 'src/app/core/store/user/user.action';

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
