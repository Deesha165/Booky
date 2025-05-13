import { Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { catchError, Observable } from "rxjs";
import { UserDetails } from "../models/user/user-details.model";
import { RegistrationRequest } from "../dtos/auth/RegisterationRequest.dto";

@Injectable({
    providedIn:'root'
})
export class UserService{

        private apiUrl = `${environment.apiUrl}/user`;
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



     activateUser(userId: Number): Observable<void> {
        this.updateHeaders();
        return this.httpClient.put<void>(`${this.apiUrl}/activate/${userId}`, [], { headers: this.headers }).pipe(
            catchError((error) => {
                console.error('Activating user failed:', error);
                throw error;
            })
        );
    }

    deactivateUser(userId: Number): Observable<void> {
        this.updateHeaders();
        return this.httpClient.put<void>(`${this.apiUrl}/deactivate/${userId}`, [], { headers: this.headers }).pipe(
            catchError((error) => {
                console.error('Deactivating user failed:', error);
                throw error;
            })
        );
    }

     createVerifierPerson(verifierPerson:RegistrationRequest):Observable<UserDetails>{

        this.updateHeaders();
        console.log('before direct call'+ verifierPerson );
        return this.httpClient.post<UserDetails>(`${this.apiUrl}/create-verifier`,verifierPerson,{headers:this.headers});
    }
}