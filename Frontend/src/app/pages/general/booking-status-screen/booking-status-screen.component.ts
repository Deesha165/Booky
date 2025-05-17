import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-booking-status-screen',
  templateUrl: './booking-status-screen.component.html',
  imports:[FormsModule,CommonModule,RouterLink,TranslateModule],
  styleUrls: ['./booking-status-screen.component.css']
})
export class BookingStatusScreenComponent implements OnInit {

  bookingMessage: string | null = null;
  bookingSuccess: boolean = false;
 
  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {

    const status = this.route.snapshot.paramMap.get('status');


    if (status === 'success') {
      this.bookingMessage = '🎉 Congrats! You have successfully booked the event.';
      this.bookingSuccess = true;
    } else {
      this.bookingMessage = '❌ Error occurred while booking. Please try again.';
      this.bookingSuccess = false;
    }
  }
}
