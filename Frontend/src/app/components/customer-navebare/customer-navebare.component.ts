import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-customer-navebare',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './customer-navebare.component.html',
  styleUrls: ['./customer-navebare.component.css']
})
export class CustomerNavebareComponent implements OnInit {
  @Output() searchTextChanged = new EventEmitter<string>();
  userName: string | null = null;
  isLoggedIn: boolean = false;
  userId: number = 0;
  @Input() hideSearch: boolean = false;
  
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.checkLoginStatus();
  }

  checkLoginStatus(): void {
   /* this.isLoggedIn = this.authService.isLoggedIn();
    if (this.isLoggedIn) {
      this.loadUserData();
    }*/
  }

  loadUserData(): void {
    /*this.authService.getLoggedInUser().subscribe({
      next: (user) => {
        this.userFirstName = user?.fir?.toString() ?? 'User';
        this.userId = Number(user?.id) ?? 0;
      },
      error: (err) => {
        console.error("Failed to load user:", err);
        // Handle error appropriately
      }
    });*/
  }

  onStoreClick(): void {
    this.router.navigate(['/customer/stores']);
  }

  onSignOut(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  onSearch(value: string): void {
    this.searchTextChanged.emit(value.trim());
  }


}
