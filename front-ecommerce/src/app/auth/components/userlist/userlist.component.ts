import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Product } from '../../../models/product';
import { Users } from '../../../models/users';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RangePipe } from '../../../pipes/range.pipe';

@Component({
  selector: 'app-userlist',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RangePipe],
  templateUrl: './userlist.component.html',
  styleUrl: './userlist.component.css'
})
export class UserlistComponent implements OnInit {

  filteredUsers: Users[] = [];

  itemsPerPage = 10;
  searchQuery = '';
  loading = false;
  selectedCategory = '';
  error = false;
  currentPage = 1;
  users2: Users[] = [];
  users: any[] = [];
  errorMessage: string = ''
  constructor(
    private userService: UsersService,
    private readonly router: Router
  ) {}

  
  ngOnInit(): void {
    this.loadUsers();

  }

  async loadUsers() {
    try {
      const token: any = localStorage.getItem('token');
      const response = await this.userService.getAllUsers(token);
      if (response && response.statusCode === 200 && response.ourUsersList) {
        this.users2 = response.ourUsersList;
        this.loading = false;
        this.filteredUsers = response.ourUsersList;
      } else {
        this.showError('No users found.');
      }
    } catch (error: any) {
      this.showError(error.message);
      this.error = true;
        this.errorMessage = error.message;
        this.loading = false;
    }
  }


  navigateToUpdate(userId: number) {
    this.router.navigate(['/update', userId]);
  }

  showError(message: string) {
    this.errorMessage = message;
    setTimeout(() => {
      this.errorMessage = ''; // Clear the error message after the specified duration
    }, 3000);
  }


  get paginatedUsers(): Users[] {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredUsers.slice(start, start + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredUsers.length / this.itemsPerPage);
  }

  applyFilters(): void {
    this.filteredUsers = this.users2.filter(users => {
      const matchesSearch = users.name.toLowerCase().includes(this.searchQuery.toLowerCase());
      
      return matchesSearch;
    });
    this.currentPage = 1;
  }
}
