package com.example.Bookify.controller;

import com.example.Bookify.dto.event.EventCreationRequest;
import com.example.Bookify.dto.event.EventDetailsResponse;
import com.example.Bookify.dto.event.EventUpdateRequest;
import com.example.Bookify.repository.projection.EventWithBookingStatus;
import com.example.Bookify.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/event")
public class EventController {
    private final EventService eventService;


    @GetMapping("/trending")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDetailsResponse> getTrendingEvents() {
        return eventService.getTrendingEvents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDetailsResponse createEvent(@Valid @RequestBody EventCreationRequest eventCreationRequest){

        return eventService.createEvent(eventCreationRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EventDetailsResponse modifyEvent(@Valid @RequestBody EventUpdateRequest eventUpdateRequest){

        return eventService.updateEvent(eventUpdateRequest);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public int removeEvent(int eventId){
        return eventService.deleteEvent(eventId);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EventDetailsResponse getEvent(int eventId){
        return eventService.getEvent(eventId);
    }

 /*   @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<EventDetailsResponse> getAllEventsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "eventTime") String sortBy
    ){
       return eventService.getAllEventsPaged(page,size,sortBy);
    }*/
   /* @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public Page<EventDetailsResponse> getAllEventsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "eventTime") String sortBy,
            @RequestParam String categoryName
    ){
        return eventService.getEventsPagedFilteredByCategory(categoryName,page,size,sortBy);
    }
*/

}
