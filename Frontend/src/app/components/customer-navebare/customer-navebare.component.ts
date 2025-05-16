import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { TokenService } from '../../services/token.service';

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
  @Input() hideSearch: boolean = false;
  
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


  onSignOut(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  onSearch(value: string): void {
    this.searchTextChanged.emit(value.trim());
  }


}
