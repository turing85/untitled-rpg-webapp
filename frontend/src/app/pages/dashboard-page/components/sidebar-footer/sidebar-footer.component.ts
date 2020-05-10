import { Component, OnInit } from '@angular/core';
import { faCog, faEnvelope, faPowerOff, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { Store } from '@ngrx/store';
import { userLogout } from 'src/app/core/store/user/user.actions';

@Component({
  selector: 'rpg-sidebar-footer',
  templateUrl: './sidebar-footer.component.html'
})
export class SidebarFooterComponent implements OnInit {


  icons: { [key: string]: IconDefinition } = {
    signout: faPowerOff,
    settings: faCog,
    message: faEnvelope
  };

  constructor(private store$: Store) { }

  ngOnInit(): void {
  }

  logout() {
    this.store$.dispatch(userLogout());
  }

}
