import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookingService } from '../../services/booking.service';
import { EventReservationDetailsVerification } from '../../models/booking/event-reservation-details-verification.mode';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerNavebareComponent } from '../../components/navebar/navebar.component';

@Component({
  selector: 'app-verification',
  imports:[CommonModule,FormsModule,CustomerNavebareComponent],
  templateUrl: './verification.component.html',
  styleUrl: './verification.component.css'
})
export class VerificationComponent {
  eventVerificationDetails: EventReservationDetailsVerification | null = null;
  code: string  = '';
  ticketConsumed = false;

  constructor(private route: ActivatedRoute, private bookingService: BookingService) {}

  ngOnInit() {

  
  }

  handleSearch(searchTerm:string){
    this.code=searchTerm;
       this.fetchReservationDetails(searchTerm);
  }

  fetchReservationDetails(code: string) {
    this.bookingService.verifyReservation(code).subscribe({
      next: (data) => {
        this.eventVerificationDetails = data;
        this.ticketConsumed = false;
      },
      error: (err) => {
        console.error('Error verifying ticket', err);
      }
    });
  }

  consumeTicket() {
   console.log(this.code);
    this.bookingService.consumeTicket(this.code).subscribe({
      next: () => {
        this.ticketConsumed = true;
      },
      error: (err) => {
        console.error('Error consuming ticket', err);
      }
    });
  }
}
