import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { TokenService } from '../../services/token.service';
import { UserClaims } from '../../models/user/user-claims.model';
import { UserRole } from '../../enums/user-role.model';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './navebar.component.html',
  styleUrls: ['./navebar.component.css']
})
export class CustomerNavebareComponent implements OnInit {

  @Output() searchTextChanged = new EventEmitter<string>();

  @Output() trendingState=new EventEmitter<boolean>();
  userClaims:UserClaims={
    userRole: UserRole.ADMIN,
    name: ''
  }
  isLoggedIn: boolean = false;
  @Input() hideSearch: boolean = false;
  isDropdownOpen: boolean = false;
  
  constructor(private tokenService:TokenService,private authService:AuthService, private router: Router) {}

  ngOnInit(): void {
    this.checkLoginStatus();
  }



  checkLoginStatus(): void {
   
    this.tokenService.getUserClaims().subscribe((claims)=>{
      this.userClaims=claims;
      this.isLoggedIn=true;
    })
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
getSearchPlaceholder(){
  if(this.userClaims.userRole===UserRole.USER){
   return 'Search for Events...'
  }
  else if(this.userClaims.userRole===UserRole.VERIFIER){
          return 'Search for Tickets...'
  }
  return 'Search...'
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


   isVerifier():boolean{
     return this.userClaims.userRole===UserRole.VERIFIER;
  }


}
