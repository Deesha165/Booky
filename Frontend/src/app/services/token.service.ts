import { Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { UserClaims } from "../models/user/user-claims.model";
import { Observable } from "rxjs";


@Injectable({
    providedIn:'root'
})
export class TokenService{

       private apiUrl = `${environment.apiUrl}/token`;
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
    }

}
