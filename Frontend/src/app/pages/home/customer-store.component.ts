import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CustomerNavebareComponent } from '../../components/customer-navebare/customer-navebare.component';
import { EventService } from '../../services/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EventDetails } from '../../models/event/event-details.model';
import { Page } from '../../models/page.model';
import { CardeventComponent } from "../../components/card-event/card-store.component";
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

@Component({
  selector: 'app-customer-event',
  standalone: true,
  imports: [CommonModule, CustomerNavebareComponent, CardeventComponent,InfiniteScrollModule],
  templateUrl: './customer-store.component.html',
  styleUrls: ['./customer-store.component.css']
})
export class CustomereventComponent implements OnInit {
  events: EventDetails[] = [];
  filteredEvents: EventDetails[] = [];
  error: string | null = null;

  currentPage = 0;
  totalPages = 0;
  pageSize = 5;
  searchTerm:string='';

  constructor(
    private eventService: EventService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadevents();
  }

  onScroll = () => {
  
    this.currentPage++;
    this.loadevents(this.searchTerm);
  };

loadevents(searchTerm: string = '', reset: boolean = false): void {
  this.eventService.getAllEventsPaged(this.currentPage, this.pageSize, '', searchTerm).subscribe({
    next: (response: Page<EventDetails>) => {
      if (reset) {

        this.events = [...response.content];
      } else {
  

        this.events = [...this.events, ...response.content];
      }

      this.filteredEvents = [...this.events];
      this.totalPages = response.totalPages;

      console.log('Events loaded:', this.events);
    },
    error: (error: any) => {
      this.error = 'Failed to load events';
      console.error('Error loading events:', error);
    }
  });
}

  handleSearch(searchText: string): void {
    this.currentPage = 0;
    this.searchTerm=searchText;
    this.loadevents(this.searchTerm,true);
  }


}
