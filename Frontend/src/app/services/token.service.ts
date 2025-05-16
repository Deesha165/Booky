import { Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { UserClaims } from "../models/user/user-claims.model";
import { catchError, Observable, switchMap, throwError } from "rxjs";
import { AuthDetails } from "../models/auth-details.model";
import { AuthErrorHandler } from "../utils/AuthErrorHandler";


@Injectable({
    providedIn:'root'
})
export class TokenService{

       private apiUrl = `${environment.apiUrl}/api/token`;
    private headers: HttpHeaders = new HttpHeaders();


          constructor(private httpClient: HttpClient) {
             
                 this.updateHeaders();
          }
    private updateHeaders(): void {
        this.headers = new HttpHeaders({
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
            'Content-Type': 'application/json'
        
        
    
        });
    }
  


getUserClaims():Observable<UserClaims>{
    this.updateHeaders();
        return this.httpClient.get<UserClaims>(`${this.apiUrl}/user-claims`,{headers:this.headers})
        .pipe(this.handleAuthError(()=>this.getUserClaims()))
 }

 refreshToken():Observable<AuthDetails>{
      const refreshTokenRequest={
        refreshToken:localStorage.getItem('refreshToken')
      }

      return this.httpClient.post<AuthDetails>(`${this.apiUrl}/refresh`,refreshTokenRequest);
 }


 private handleAuthError<T>(retryFn: () => Observable<T>): (source: Observable<T>) => Observable<T> {
  return catchError((error: any) => {
    if (error.status === 401) {
      return this.refreshToken().pipe(
        switchMap(newToken => {
          localStorage.setItem('accessToken', newToken.accessToken);
          return retryFn();
        }),
        catchError(refreshError => {
          return throwError(() => refreshError);
        })
      );
    }
    return throwError(() => error);
  });
}
}
