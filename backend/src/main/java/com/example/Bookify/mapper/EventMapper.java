package com.example.Bookify.mapper;

import com.example.Bookify.dto.event.*;
import com.example.Bookify.entity.event.Category;
import com.example.Bookify.entity.event.Event;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.repository.projection.EventReservationDetailsForVerification;
import com.example.Bookify.repository.projection.EventWithBookingStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventMapper {

   public Event toEventEntity(EventCreationRequest eventCreationRequest){
       return Event.builder()
               .name(eventCreationRequest.name())
               .description(eventCreationRequest.description()==null?null: eventCreationRequest.description())
               .eventTime(eventCreationRequest.eventTime())
               .venue(eventCreationRequest.venue())
               .totalTickets(eventCreationRequest.totalTickets())
               .availableTickets(eventCreationRequest.totalTickets())
               .pricePerTicket(eventCreationRequest.pricePerTicket())
               .build();
   }

   public EventDetailsResponse toEventResponse(Event event){
          return EventDetailsResponse.builder()
                  .id(event.getId())
                  .name(event.getName())
                  .description(event.getDescription())
                  .eventTime(event.getEventTime())
                  .venue(event.getVenue())
                  .pricePerTicket(event.getPricePerTicket())
                  .availableTickets(event.getAvailableTickets())
                  .image(event.getImage())
                  .build();
   }
    public EventDetailsResponse fromProjectedEventToEventResponse(EventWithBookingStatus event){
        return EventDetailsResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .eventTime(event.getEventTime())
                .venue(event.getVenue())
                .pricePerTicket(event.getPricePerTicket())
                .availableTickets(event.getAvailableTickets())
                .image(event.getImage())
                .isBooked(event.isBooked())
                .build();
    }

    public EventVerificationResponse toEventVerificationResponse(EventReservationDetailsForVerification event){

     return   EventVerificationResponse.builder()
               .userEmail(event.getUserEmail()).eventTime(event.getEventTime())
               .name(event.getName()).venue(event.getVenue()).build();
    }
    public CategoryResponse toCategoryResponse(Category category){

        return CategoryResponse.builder().id(category.getId()).name(category.getName()).build();

    }




}
