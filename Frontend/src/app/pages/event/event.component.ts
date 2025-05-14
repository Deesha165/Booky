import { Component } from '@angular/core';
import { EventDetails } from '../../models/event/event-details.model';
import { ActivatedRoute } from '@angular/router';
import { EventService } from '../../services/event.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-event',
  imports: [CommonModule,FormsModule],
  templateUrl: './event.component.html',
  styleUrl: './event.component.css'
})
export class EventComponent {


event: EventDetails | null = null;
  error: string | null = null;

  constructor(private route: ActivatedRoute, private eventService: EventService) {}

  ngOnInit(): void {
    const eventId = Number(this.route.snapshot.paramMap.get('id'));
    if (!isNaN(eventId)) {
      this.eventService.getEventById(eventId).subscribe({
        next: (data) => this.event = data,
        error: () => this.error = 'Failed to load event details.'
      });
    } else {
      this.error = 'Invalid event ID.';
    }
  }

}
