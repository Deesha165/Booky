import { Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { EventReservationDetailsVerification } from "../models/booking/event-reservation-details-verification.mode";

@Injectable({
    providedIn:'root'
})
export class BookingService{

private apiUrl = `${environment.apiUrl}/api/booking`;
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
    bookEvent(eventId:Number):Observable<Number>{
    
            this.updateHeaders();
            console.log('before direct call'+ eventId);
            return this.httpClient.post<Number>(`${this.apiUrl}/book-event/${eventId}`,[],{headers:this.headers});
        }



        verifyReservation(ticketCode:String):Observable<EventReservationDetailsVerification>{
          this.updateHeaders();
          return this.httpClient.get<EventReservationDetailsVerification>(`${this.apiUrl}/verify`,{headers:this.headers});   
        }
}
