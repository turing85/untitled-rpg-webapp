import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user/user.keycloak.model';

@Component({
  selector: 'rpg-dashboard-page',
  templateUrl: './dashboard-page.component.html'
})
export class DashboardPageComponent implements OnInit {
  user$: Observable<User>;
  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.user$ = this.userService.getUserDetails();
  }

}
