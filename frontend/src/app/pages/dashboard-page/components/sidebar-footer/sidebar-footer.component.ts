import { Component, OnInit } from '@angular/core';
import { userLogout } from 'src/app/core/store/user/user.action';
import { faPowerOff, IconDefinition, faCog, faEnvelope } from '@fortawesome/free-solid-svg-icons';
import { Store } from '@ngrx/store';

@Component({
  selector: 'rpg-sidebar-footer',
  templateUrl: './sidebar-footer.component.html'
})
export class SidebarFooterComponent implements OnInit {


  icons: { [key: string]: IconDefinition } = {
    'signout': faPowerOff,
    'settings': faCog,
    'message': faEnvelope
  };

  constructor(private store$: Store) { }

  ngOnInit(): void {
  }

  logout() {
    this.store$.dispatch(userLogout());
  }

}
