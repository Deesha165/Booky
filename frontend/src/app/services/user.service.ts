import { Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { catchError, Observable } from "rxjs";
import { UserDetails } from "../models/user/user-details.model";
import { RegistrationRequest } from "../dtos/auth/RegisterationRequest.dto";
import { Page } from "../models/page.model";
import { AuthErrorHandler } from "../utils/AuthErrorHandler";
import { TokenService } from "./token.service";
import { PasswordChangeRequest } from "../dtos/user/password-change-request.dto";

@Injectable({
    providedIn:'root'
})
export class UserService{

        private apiUrl = `${environment.apiUrl}/api/user`;
    private headers: HttpHeaders = new HttpHeaders();



       private authErrorHandler: AuthErrorHandler;
          constructor(private httpClient: HttpClient, private tokenService: TokenService) {
               this.authErrorHandler = new AuthErrorHandler(this.tokenService);
                 this.updateHeaders();
          }
 private getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      'Content-Type': 'application/json'
    });
  }
    private updateHeaders(): void {
        this.headers = new HttpHeaders({
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
            'Content-Type': 'application/json'
        
        });
        
    }



     activateUser(userId: Number): Observable<void> {
        this.updateHeaders();
        return this.httpClient.put<void>(`${this.apiUrl}/activate/${userId}`, [], { headers: this.headers }).pipe(
        this.authErrorHandler.handleAuthError(()=>this.activateUser(userId))  
        );
    }

    deactivateUser(userId: Number): Observable<void> {
        this.updateHeaders();
        return this.httpClient.put<void>(`${this.apiUrl}/deactivate/${userId}`, [], { headers: this.headers }).pipe(
        this.authErrorHandler.handleAuthError(()=>this.deactivateUser(userId))   
        );
    }

     createVerifierPerson(verifierPerson:RegistrationRequest):Observable<UserDetails>{

        this.updateHeaders();
        console.log('before direct call'+ verifierPerson );
        return this.httpClient.
        post<UserDetails>(`${this.apiUrl}/create-verifier`,verifierPerson,{headers:this.headers})
        .pipe(
        this.authErrorHandler.handleAuthError(()=>this.createVerifierPerson(verifierPerson))   
        );
    }

 getAllUsers(page: number, size: number): Observable<Page<UserDetails>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size)
     
    return this.httpClient.get<Page<UserDetails>>(this.apiUrl, {
      headers: this.getAuthHeaders(),
      params: params
    }).pipe(
        this.authErrorHandler.handleAuthError(()=>this.getAllUsers(page,size))   
        );
  }

   changePassword(passwordChangeRequest: PasswordChangeRequest): Observable<any> {
    this.updateHeaders();
    return this.httpClient.put(
      `${this.apiUrl}/change-password`,
      passwordChangeRequest,
      { 
        headers: this.headers
      }
    ).pipe(
        this.authErrorHandler.handleAuthError(()=>this.changePassword(passwordChangeRequest))   
        );
    
  }
 

}