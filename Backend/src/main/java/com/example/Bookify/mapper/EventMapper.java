package com.example.Bookify.mapper;

import com.example.Bookify.dto.event.EventCreationRequest;
import com.example.Bookify.dto.event.EventDetailsResponse;
import com.example.Bookify.dto.event.EventUpdateRequest;
import com.example.Bookify.entity.event.Event;
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
               .image(eventCreationRequest.image()==null ? null: eventCreationRequest.image())
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


}
