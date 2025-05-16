import { Component } from '@angular/core';
import { EventDetails } from '../../../models/event/event-details.model';
import { Router } from '@angular/router';
import { EventService } from '../../../services/event.service';
import { AdminSidebarComponent } from '../../../components/admin-sidebar/admin-sidebar.component';
import { CardeventComponent } from "../../../components/card-event/card-store.component";
import { EventsTableComponent } from "../../../components/events-table/events-table.component";
import { SidebarComponent } from "../../../components/sidebar/sidebar.component";

@Component({
  selector: 'app-admin-events',
  imports: [ EventsTableComponent, SidebarComponent],
  templateUrl: './admin-events.component.html',
  styleUrl: './admin-events.component.css'
})
export class AdminEventsComponent {


  constructor(
 
  ) {}

  

}
