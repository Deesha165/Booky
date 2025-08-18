import { Component } from '@angular/core';
import { AuthenticationRequest } from '../../../dtos/auth/AuthenticationRequest.dto';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { AuthDetails } from '../../../models/auth-details.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TokenService } from '../../../services/token.service';
import { UserClaims } from '../../../models/user/user-claims.model';
import { UserRole } from '../../../enums/user-role.model';
import { RouterTestingHarness } from '@angular/router/testing';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  imports: [CommonModule,FormsModule,TranslateModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {


     authRequest: AuthenticationRequest = {
        email: '',
        password: ''
    };
    userId: number = 0;
    fieldErrors: { [key: string]: string } = {};
    userClaims:UserClaims|null=null;

    constructor(private authService: AuthService, private route: Router, private tokenService :TokenService) {localStorage.clear();}

    onSubmit() {
       
        this.fieldErrors = {};
        
        this.authService.authenticate(this.authRequest).subscribe({
            next: (response: AuthDetails) => {
                console.log('Login successful:', response);
       
             
                localStorage.setItem('accessToken', response.accessToken);
                localStorage.setItem('refreshToken', response.refreshToken);

           this.tokenService.getUserClaims().subscribe((data)=>{
            this.userClaims=data;
            console.log(this.userClaims);
            if(this.userClaims.userRole===UserRole.USER){
              console.log('inside');  
              this.route.navigate(['/home']);
            }
            else if(this.userClaims.userRole===UserRole.ADMIN){

                this.route.navigate(['/events']);
            }
            else{
                  this.route.navigate(['/verification']);
            }
            

           })
          
              
            },
            error: (err) => {
                console.error('Login failed:', err);
                this.fieldErrors['password'] = "Invalid email or password. Please try again.";
            }
        });
    }

    goToSignUp() {
        this.route.navigate(['sign-up']);
    }

    goToForgotPassword() {
        this.route.navigate(['forgot-password']);
    }

}
