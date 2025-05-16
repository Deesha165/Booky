package com.example.Bookify.controller;

import com.example.Bookify.dto.event.CategoryResponse;
import com.example.Bookify.dto.event.EventCreationRequest;
import com.example.Bookify.dto.event.EventDetailsResponse;
import com.example.Bookify.dto.event.EventUpdateRequest;
import com.example.Bookify.repository.projection.EventWithBookingStatus;
import com.example.Bookify.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("api/event")
public class EventController {
    private final EventService eventService;


    @GetMapping("/trending")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Set<EventDetailsResponse> getTrendingEvents() {
        return eventService.getTrendingEvents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public EventDetailsResponse createEvent(@Valid @RequestBody EventCreationRequest eventCreationRequest){

        return eventService.createEvent(eventCreationRequest);
    }
    @PostMapping("/category/{categoryName}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createCategory(@PathVariable String categoryName){

        return eventService.createCategory(categoryName);
    }

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<CategoryResponse> getAllCategories(){

        return eventService.getAllCategories();
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public EventDetailsResponse modifyEvent(@Valid @RequestBody EventUpdateRequest eventUpdateRequest){

        return eventService.updateEvent(eventUpdateRequest);
    }
    @DeleteMapping("{eventId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public int removeEvent(@PathVariable int eventId){
        return eventService.deleteEvent(eventId);
    }
    @GetMapping("{eventId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public EventDetailsResponse getEvent(@PathVariable int eventId){
        return eventService.getEvent(eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<EventDetailsResponse> getAllEventsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "eventTime") String sortBy,
            @RequestParam(required = false) String categoryName
    ){
        if (categoryName == null) {
            return eventService.getAllEventsPaged( page, size, sortBy);
        }

        else {

            return eventService.getEventsPagedFilteredByCategory(categoryName, page, size, sortBy);
        }
    }


}
