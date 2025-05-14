import { Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { catchError, Observable } from "rxjs";
import { RegistrationRequest } from "../dtos/auth/RegisterationRequest.dto";
import { UserDetails } from "../models/user/user-details.model";
import { AuthenticationRequest } from "../dtos/auth/AuthenticationRequest.dto";
import { AuthDetails } from "../models/auth-details.model";
import { UserClaims } from "../models/user/user-claims.model";

@Injectable({
    providedIn:'root'
})
export class AuthService{

       private apiUrl = `${environment.apiUrl}/auth`;
    private headers: HttpHeaders = new HttpHeaders();

    constructor(private httpClient: HttpClient) {
     
    }

    private updateHeaders(): void {
        this.headers = new HttpHeaders({
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
            'Content-Type': 'application/json'
        
        
        
        });
    }

    register(registerRequest: RegistrationRequest): Observable<UserDetails> {
        return this.httpClient
          .post<UserDetails>(`${this.apiUrl}/sign-up`, registerRequest)
          .pipe(
            catchError((error) => {
              console.error('Sign-up failed', error);
              throw error; 
            })
          );
      }
        
        authenticate(authRequest: AuthenticationRequest): Observable<AuthDetails> {
            return this.httpClient
              .post<AuthDetails>(`${this.apiUrl}/login`,authRequest )
              .pipe(
                catchError((error) => {
                  console.error('Login failed', error);
                  throw error;
                })
              );
          }

    logout(): void {
        localStorage.clear();
    }

    

}
