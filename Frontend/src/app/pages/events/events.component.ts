import { Component } from '@angular/core';
import { EventDetails } from '../../models/event/event-details.model';
import { Router } from '@angular/router';
import { EventService } from '../../services/event.service';
import { EventsTableComponent } from "../../components/events-table/events-table.component";
import { SidebarComponent } from "../../components/sidebar/sidebar.component";
import { TranslateModule } from '@ngx-translate/core';


@Component({
  selector: 'app-events',
  imports: [EventsTableComponent, SidebarComponent, TranslateModule],
  templateUrl: './events.component.html',
  styleUrl: './events.component.css'
})
export class EventsComponent {


  constructor(
 
  ) {}

  

}
