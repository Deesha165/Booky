import { Component } from '@angular/core';
import { AuthenticationRequest } from '../../dtos/auth/AuthenticationRequest.dto';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { AuthDetails } from '../../models/auth-details.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [CommonModule,FormsModule],
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

    constructor(private authService: AuthService, private route: Router) {localStorage.clear();}

    onSubmit() {
       
        this.fieldErrors = {};
        
        this.authService.authenticate(this.authRequest).subscribe({
            next: (response: AuthDetails) => {
                console.log('Login successful:', response);
       
             
                localStorage.setItem('accessToken', response.accessToken.toLowerCase());
                localStorage.setItem('refreshToken', response.refreshToken.toLowerCase());

              
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
