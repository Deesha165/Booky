import { Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { EventReservationDetailsVerification } from "../models/booking/event-reservation-details-verification.mode";
import { AuthErrorHandler } from "../utils/AuthErrorHandler";
import { TokenService } from "./token.service";

@Injectable({
    providedIn:'root'
})
export class BookingService{

private apiUrl = `${environment.apiUrl}/api/booking`;
    private headers: HttpHeaders = new HttpHeaders();

        private authErrorHandler: AuthErrorHandler;
      constructor(private httpClient: HttpClient, private tokenService: TokenService) {
           this.authErrorHandler = new AuthErrorHandler(this.tokenService);
             this.updateHeaders();
      }


    private updateHeaders(): void {
        this.headers = new HttpHeaders({
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
            'Content-Type': 'application/json'
        
        });
    }
    bookEvent(eventId:Number):Observable<Number>{
    
            this.updateHeaders();
            console.log('before direct call'+ eventId);
            return this.httpClient.post<Number>(`${this.apiUrl}/book-event/${eventId}`,[],{headers:this.headers})
            .pipe(this.authErrorHandler.handleAuthError(()=>this.bookEvent(eventId)));
        }



        verifyReservation(ticketCode:String):Observable<EventReservationDetailsVerification>{
          this.updateHeaders();
          return this.httpClient.get<EventReservationDetailsVerification>(`${this.apiUrl}/verify`,{headers:this.headers})
             .pipe(this.authErrorHandler.handleAuthError(()=>this.verifyReservation(ticketCode)));;   
        }
}
