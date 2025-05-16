import { Component, Input } from '@angular/core';
import { EventDetails } from '../../models/event/event-details.model';
import { EventService } from '../../services/event.service';
import { Router } from '@angular/router';
import { EventCreationRequest } from '../../dtos/event/event-creation-request.dto';

import * as bootstrap from 'bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CategoryDetails } from '../../models/event/category-details.model';
import { AuthService } from '../../services/auth.service';
import { RegistrationRequest } from '../../dtos/auth/RegisterationRequest.dto';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-events-table',
  imports:[CommonModule,FormsModule,InfiniteScrollModule],
  templateUrl: './events-table.component.html',
  styleUrls: ['./events-table.component.css']
})
export class EventsTableComponent {
  searchQuery: string = '';

   Events: EventDetails[] = [];
categories:CategoryDetails[]=[];

  filteredEvents: EventDetails[] = [];
  selectedEvent: EventDetails | null = null;
  newVerifier:RegistrationRequest={
    name: '',
    email: '',
    password: ''
  };
newCategory:string='';
  newEvent: EventCreationRequest = {
    name: '',
    description: '',
    eventTime: new Date(),
    venue: '',
    pricePerTicket: 0,
    availableTickets: 0,
    image: '',
    categoryId: 0
  };
    currentPage = 0;
  totalPages = 0;
  pageSize = 5;

  constructor(
    private eventService: EventService,
  
    private userService:UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchEvents();

  }

  onScroll = () => {
  
    this.currentPage++;
    this.fetchEvents();
  };
  fetchEvents(): void {
    this.eventService.getAllEventsPaged(this.currentPage,this.pageSize, '').subscribe({
      next: (page) => {
        this.Events = [...this.Events,...page.content];

        this.filteredEvents = this.Events;
      },
      error: (err: any) => {
        console.error('Error fetching Events:', err);
      }
    });
  }

  fetchCategories():void{
    this.eventService.getAllCategories().subscribe({
      next:(data)=>{
this.categories=data;


      }
      , error:(error)=>{
                console.error('Error fetching Categories:', error);
      }
    })
  }

  createVerifier():void{

    this.userService.createVerifierPerson(this.newVerifier).subscribe({
      next:(data)=>{

        console.log(data);
      },
      error:(err)=>{

        console.log('an error occured',err);
      }
    })
  }

  createCategory():void{

    this.eventService.createCategory(this.newCategory).subscribe({
      next:(data)=>{

        console.log(data);
      },
      error:(error)=>{

        console.log('category adding failed',this.newCategory);
      }
    })
  }

  filterEvents(): void {
    const lowerQuery = this.searchQuery.toLowerCase();
    this.filteredEvents = this.Events.filter(event =>
      event.name.toLowerCase().includes(lowerQuery)
    );
  }



  viewEventDetails(event: EventDetails): void {
    this.selectedEvent = event;
    const modalElement = document.getElementById('eventDetailsModal');
    if (modalElement) {
      const modalInstance = new bootstrap.Modal(modalElement);
      modalInstance.show();
    }
  }



  addEvent(): void {
    if (this.newEvent.name.trim()) {
      this.eventService.createEvent(this.newEvent).subscribe({
        next: (createdEvent: EventDetails) => {
          this.Events.push(createdEvent);
          this.filterEvents();
        },
        error: (err: any) => {
          console.error('Error creating Event:', err);
        }
      });
    }
  }
     openCreateEventModal(): void {
      this.fetchCategories();
    const modalElement = document.getElementById('createEventModal');
    if (modalElement) {
      new bootstrap.Modal(modalElement).show();
    }
  }

  openCreateVerifierModal(): void {
    const modalElement = document.getElementById('createVerifierModal');
    if (modalElement) {
      new bootstrap.Modal(modalElement).show();
    }
  }
  openCreateCategoryModal() {
  const modal = new bootstrap.Modal(document.getElementById('createCategoryModal')!);
  modal.show();
}
}
