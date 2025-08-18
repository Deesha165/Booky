import { Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
    providedIn:'root'
})
export class ImageService{

    
private apiUrl = `${environment.apiUrl}/api/media`;

    constructor(private httpClient: HttpClient) {

    }

 
 uploadImage(eventId: number, imageFile: File): Observable<void> {
    const formData = new FormData();
    formData.append('image', imageFile);


    return this.httpClient.put<void>(`${this.apiUrl}/event/${eventId}/image`, formData);
  }
}