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
import { ImageService } from '../../services/image.servicec';
import { EventUpdateRequest } from '../../dtos/event/event-update-request.dto';
import { UserClaims } from '../../models/user/user-claims.model';
import { UserRole } from '../../enums/user-role.model';
import { TokenService } from '../../services/token.service';

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
  selectedImageFile!: File;

  filteredEvents: EventDetails[] = [];
  selectedEvent: EventDetails ={
    id: 0,
    name: '',
    description: '',
    eventTime: new Date(),
    venue: '',
    pricePerTicket: 0,
    availableTickets: 0,
    image: '',
    isBooked: false
  };
  newVerifier:RegistrationRequest={
    name: '',
    email: '',
    password: ''
  };
  ticketCode:string='';
newCategory:string='';
  newEvent: EventCreationRequest = {
    name: '',
    description: '',
    eventTime: new Date(),
    venue: '',
    pricePerTicket: 0,
    availableTickets: 0,
    categoryId: 0
  };
 updatedEvent: EventUpdateRequest={
   eventId: 0,
   description: '',
   eventTime: new Date(),
   name: '',
   venue:''
 }
  imagePreviewUrl: string | ArrayBuffer | null = null;
    currentPage = 0;
  totalPages = 0;
  pageSize = 15;

  eventCounts=0;
  userClaims:UserClaims={
    userRole: UserRole.ADMIN,
    name: ''
  }
  constructor(
    private eventService: EventService,
   private imageService:ImageService,  
    private userService:UserService,
    private tokenService:TokenService,
    private router:Router
  ) {}

  ngOnInit(): void {
    this.fetchEvents();
   this.fetchUserClaims();
  }

  onScroll = () => {
  
    this.currentPage++;
    this.fetchEvents();
  };

onFileSelected(event: Event): void {
  const target = event.target as HTMLInputElement;
  if (target.files && target.files.length > 0) {
    this.selectedImageFile = target.files[0];

    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreviewUrl = reader.result;
    };
    reader.readAsDataURL(this.selectedImageFile);
  }
}
  fetchEvents(): void {
    this.eventService.getAllEventsPaged(this.currentPage,this.pageSize, '').subscribe({
      next: (page) => {
        this.Events = [...this.Events,...page.content];

        console.log(this.Events);
        this.filteredEvents = this.Events;
      },
      error: (err: any) => {
        console.error('Error fetching Events:', err);
      }
    });
  }
  fetchUserClaims(){
    this.tokenService.getUserClaims().subscribe({
      next:(dataa)=>{
            this.userClaims=dataa;
      },
      error:(error)=>{
        console.log('error fetching user claims');
      }
    })
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
           
          this.imageService.uploadImage(createdEvent.id,this.selectedImageFile).subscribe(()=>{
                 console.log("uploaded image",this.selectedImageFile);
           });

          this.Events.push(createdEvent);
          this.filterEvents();
        },
        error: (err: any) => {
          console.error('Error creating Event:', err);
        }
      });
    }
  }
 updateEvent(event: any): void {
  const updatedEvent = {
    eventId: event.id,
    description: event.description,
    eventTime: event.eventTime,
    venue:event.venue,
    name:event.name
  };

  this.eventService.updateEvent(updatedEvent).subscribe({
    next: () => {
      console.log('Event updated successfully');
          this.filteredEvents = this.filteredEvents.map(e => e.id === event.id ? { ...e, ...event } : e);
    
    },
    error: (err) => {
      console.error('Error updating event:', err);
    }
  });
}

  deleteEvent(event:EventDetails){

    this.eventService.deleteEvent(event.id).subscribe((data)=>{
  
      this.Events=this.Events.filter(e=>e.id!==event.id)
         this.filteredEvents = this.filteredEvents.filter(e => e.id !== event.id);
     console.log('event deletd successfully',data); 
    });
  }
  verifyTicket() {
 this.router.navigate(['/verify',this.ticketCode])
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



  openEditModal(event:EventDetails): void {
    this.selectedEvent = { ...event };
    const modalElement = document.getElementById('editEventModal');
    if (modalElement) {
      const modalInstance = new bootstrap.Modal(modalElement);
      modalInstance.show();
    }
  }
  isAdmin():boolean{
     return this.userClaims.userRole===UserRole.ADMIN;
  }
}
