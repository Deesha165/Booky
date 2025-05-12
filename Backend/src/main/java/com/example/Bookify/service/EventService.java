package com.example.Bookify.service;

import com.example.Bookify.dto.event.EventCreationRequest;
import com.example.Bookify.dto.event.EventDetailsResponse;
import com.example.Bookify.dto.event.EventUpdateRequest;
import com.example.Bookify.entity.event.Event;
import com.example.Bookify.repository.projection.EventWithBookingStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {


    EventDetailsResponse createEvent(EventCreationRequest eventCreationRequest);
    EventDetailsResponse updateEvent(EventUpdateRequest eventUpdateRequest);
    int deleteEvent(int eventId);
    EventDetailsResponse getEvent(int eventId);
    Event getEventEntityById(int eventId);
    Page<EventDetailsResponse> getAllEventsPaged(int page, int size, String sortBy);

    Page<EventDetailsResponse> getEventsPagedFilteredByCategory(String categoryName,int page,int size,String sortBy);


    List<EventDetailsResponse> getTrendingEvents();
}
