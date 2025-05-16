import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { environment } from "../../environment/environment";
import { Observable } from "rxjs";
import { EventDetails } from "../models/event/event-details.model";
import { EventCreationRequest } from "../dtos/event/event-creation-request.dto";
import { CategoryDetails } from "../models/event/category-details.model";
import { EventUpdateRequest } from "../dtos/event/event-update-request.dto";
import { Page } from "../models/page.model";

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = `${environment.apiUrl}/api/event`;

  constructor(private httpClient: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      'Content-Type': 'application/json'
    });
  }

  getTrendingEvents(): Observable<EventDetails[]> {
    return this.httpClient.get<EventDetails[]>(`${this.apiUrl}/trending`, {
      headers: this.getAuthHeaders()
    });
  }

  createEvent(event: EventCreationRequest): Observable<EventDetails> {
    return this.httpClient.post<EventDetails>(this.apiUrl, event, {
      headers: this.getAuthHeaders()
    });
  }

  
  createCategory(categoryName: string): Observable<CategoryDetails> {
    return this.httpClient.post<CategoryDetails>(`${this.apiUrl}/category/${categoryName}`, {}, {
      headers: this.getAuthHeaders()
    });
  }

  getAllCategories(): Observable<CategoryDetails[]> {
    return this.httpClient.get<CategoryDetails[]>(`${this.apiUrl}/category`, {
      headers: this.getAuthHeaders()
    });
  }

  updateEvent(event: EventUpdateRequest): Observable<EventDetails> {
    return this.httpClient.put<EventDetails>(this.apiUrl, event, {
      headers: this.getAuthHeaders()
    });
  }

  deleteEvent(eventId: number): Observable<number> {
    return this.httpClient.delete<number>(`${this.apiUrl}/${eventId}`, {
      headers: this.getAuthHeaders()
    });
  }


  getEventById(eventId: number): Observable<EventDetails> {
    return this.httpClient.get<EventDetails>(`${this.apiUrl}/${eventId}`, {
      headers: this.getAuthHeaders()
    });
  }

  getAllEventsPaged(page: number, size: number, sortBy: string, categoryName?: string): Observable<Page<EventDetails>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortBy', sortBy);

    if (categoryName) {
      params = params.set('categoryName', categoryName);
    }

    return this.httpClient.get<Page<EventDetails>>(this.apiUrl, {
      headers: this.getAuthHeaders(),
      params: params
    });
  }
}
