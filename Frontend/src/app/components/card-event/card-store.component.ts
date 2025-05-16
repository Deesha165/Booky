import { Component, Input } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { EventDetails } from '../../models/event/event-details.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BookingService } from '../../services/booking.service';
import { environment } from '../../../environment/environment';

@Component({
  selector: 'app-card-event',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './card-store.component.html',
  styleUrl: './card-store.component.css'
})
export class CardeventComponent {
  @Input() event!: EventDetails;

  serverUrl:string=`${environment.apiUrl}`;

  constructor(private bookingService: BookingService,private router:Router) {}

  selectedEvent: EventDetails | null = null;
  bookingMessage: string | null = null;
  bookingSuccess: boolean = false;
  isModalOpen = false;

  openBookingModal(event: EventDetails) {
    this.selectedEvent = event;
    this.isModalOpen = true;
    this.bookingMessage = null;
    this.bookingSuccess = false;
  }

  closeModal() {
    this.isModalOpen = false;
    this.selectedEvent = null;
    this.bookingMessage = null;
    this.bookingSuccess = false;
  }

  confirmBooking() {
    if (!this.selectedEvent) return;

    this.bookingMessage = null;
    this.bookingSuccess = false;

    this.bookingService.bookEvent(this.selectedEvent.id).subscribe({
      next: () => {
      
        this.bookingSuccess = true;
        this.event.isBooked = true;

         this.router.navigate(['/booking-screen', 'success']);
      },
      error: () => {
       
        this.bookingSuccess = false;
           this.router.navigate(['/booking-screen', 'error']);
      }
    });
  
    this.closeModal();
  }
}
