import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  userId: number = 0;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {
    const storedUserId = localStorage.getItem('userId');
    if (storedUserId) {
      this.userId = parseInt(storedUserId, 10);
    }
  }

  goToAdminDashboard() {
    this.router.navigate(['admin-dashboard']);
  }

  goToProfile() {
    this.router.navigate(['profile', this.userId]);
  }

  goToActiveAccounts() {
    this.router.navigate(['active-account']);
  }

  goToBlockedAccounts() {
    this.router.navigate(['un-active-account']);
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
