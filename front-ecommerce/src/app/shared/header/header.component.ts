import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../auth/services/users.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  constructor(private readonly userService: UsersService) {}

  isAuthenticated:boolean =false;
  isAdmin:boolean =false;
  isUser:boolean =false;

  ngOnInit(): void {

    this.userService.isAuthenticated$.subscribe(isAuthenticated => {
      this.isAuthenticated = isAuthenticated;
    });
    this.isAdmin = this.userService.isAdmin();
    this.isUser = this.userService.isUser();
    console.log("adute",this.isAuthenticated);

  }

  logout(){
    this.userService.logOut();

  }


}
