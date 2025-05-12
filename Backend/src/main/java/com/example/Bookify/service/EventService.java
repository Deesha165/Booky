package com.example.Bookify.service;

import com.example.Bookify.dto.event.CategoryResponse;
import com.example.Bookify.dto.event.EventCreationRequest;
import com.example.Bookify.dto.event.EventDetailsResponse;
import com.example.Bookify.dto.event.EventUpdateRequest;
import com.example.Bookify.entity.event.Event;
import com.example.Bookify.repository.projection.EventWithBookingStatus;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface EventService {


    EventDetailsResponse createEvent(EventCreationRequest eventCreationRequest);
    EventDetailsResponse updateEvent(EventUpdateRequest eventUpdateRequest);
    int deleteEvent(int eventId);
    EventDetailsResponse getEvent(int eventId);
    Event getEventEntityById(int eventId);
    Page<EventDetailsResponse> getAllEventsPaged(int userId,int page, int size, String sortBy);

    Page<EventDetailsResponse> getEventsPagedFilteredByCategory(int userId,String categoryName,int page,int size,String sortBy);


    Set<EventDetailsResponse> getTrendingEvents();


    CategoryResponse createCategory(String categoryName);

    List<CategoryResponse> getAllCategories();
}
