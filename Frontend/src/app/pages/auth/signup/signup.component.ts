import { Component } from '@angular/core';
import { RegistrationRequest } from '../../../dtos/auth/RegisterationRequest.dto';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signup',
  imports: [CommonModule,FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

   registerRequest: RegistrationRequest = {
  name:'',
    email: '',
    password: ''
  };
  fieldErrors: { [key: string]: string } = {};  

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.register(this.registerRequest).subscribe({
      next: (response) => {
        console.log('Registration successful:', response);
      
        this.fieldErrors = {};
      
    
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 1000);
      },
      error: (err) => {
        console.error('Registration failed:', err);
        if (err.error?.fieldErrors) {
          this.fieldErrors = err.error.fieldErrors;
        } else {
          this.fieldErrors['general'] = 'Registration failed. Please try again.';
        }
      }
    });
  }

  goToLogIn() {
    this.router.navigate(['login']);
  }
}
