import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-customer-navebare',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './customer-navebare.component.html',
  styleUrls: ['./customer-navebare.component.css']
})
export class CustomerNavebareComponent implements OnInit {

  @Output() searchTextChanged = new EventEmitter<string>();

  @Output() trendingState=new EventEmitter<boolean>();
  userName: string | null = null;
  isLoggedIn: boolean = false;
  @Input() hideSearch: boolean = false;
  isDropdownOpen: boolean = false;
  
  constructor(private tokenService:TokenService,private authService:AuthService, private router: Router) {}

  ngOnInit(): void {
    this.checkLoginStatus();
  }



  checkLoginStatus(): void {
   
    this.tokenService.getUserClaims().subscribe((claims)=>{
      this.userName=claims.name;
      this.isLoggedIn=true;
    })
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  loadTrending(){
    this.trendingState.emit(true);
    this.isDropdownOpen = false;
  }

  onSignOut(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
    this.isDropdownOpen = false;
  }

  onSearch(value: string): void {
    this.searchTextChanged.emit(value.trim());
  }

  onSelectOption(event: Event) {
    const target = event.target as HTMLSelectElement | null;
    const value = target?.value;



    switch (value) {
      case 'trending':
        this.loadTrending();
        break;
      case 'settings':
        this.goToSettings();
        break;
      case 'logout':
        this.onSignOut();
        break;
    }
  }

  goToSettings() {
    this.router.navigate(['/settings']);
    this.isDropdownOpen = false;
  }




}
