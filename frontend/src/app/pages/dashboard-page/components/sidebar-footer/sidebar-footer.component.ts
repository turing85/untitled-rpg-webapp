import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { faPowerOff, IconDefinition,faCog, faEnvelope } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'rpg-sidebar-footer',
  templateUrl: './sidebar-footer.component.html'
})
export class SidebarFooterComponent implements OnInit {

  
  icons: {[key:string]:IconDefinition} = {'signout':faPowerOff,'settings':faCog,'message':faEnvelope};

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  logout() {
    this.userService.logout();
  }

}
