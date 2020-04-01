import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'rpg-sidebar-footer',
  templateUrl: './sidebar-footer.component.html'
})
export class SidebarFooterComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  logout(){
    this.userService.logout();
  }
}
