package com.example.Bookify.service.impl;

import com.example.Bookify.entity.booking.Booking;
import com.example.Bookify.entity.booking.Ticket;
import com.example.Bookify.entity.event.Event;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.exception.IllegalActionException;
import com.example.Bookify.repository.BookingRepository;
import com.example.Bookify.repository.TicketRepository;
import com.example.Bookify.repository.projection.EventReservationDetailsForVerification;
import com.example.Bookify.service.BookingService;
import com.example.Bookify.service.EventService;
import com.example.Bookify.service.UserService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final EventService eventService;


    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE)
    public int bookEvent(int eventId, int userId) {

        Event event=eventService.getEventEntityById(eventId);
        Booking savedBooking;
        if(event.getAvailableTickets()>0){
            event.setAvailableTickets(event.getAvailableTickets()-1);
            User user=userService.getUser(userId);
            Booking booking=Booking.builder()
                    .event(event)
                    .user(user)
                    .build();
            savedBooking=bookingRepository.save(booking);

             createTicket(savedBooking);

        }
        else{
            throw new IllegalActionException("No available tickets for booking");
        }

        return savedBooking.getId();
    }

    @Override
    public EventReservationDetailsForVerification verifyReservation(String ticketCode) {

        return bookingRepository.findReservationDetailsByTicketCode(ticketCode);
    }


    private void createTicket(Booking booking){

        log.info("Creating ticket for booking -ID: {},User: {},Event: {}, BookedAt: {}",
                booking.getId(),
                booking.getUser().getName(),
                booking.getEvent().getName(),
                booking.getBookedAt());

          Ticket generatedTicket=Ticket.builder()
                  .isUsed(false)
                  .booking(booking)
                  .ticketCode(generateTicketCode())
                  .build();
        Ticket savedTicket=  ticketRepository.save(generatedTicket);

        log.info("Ticket:{} created succesfully",savedTicket);
    }

    private String generateTicketCode(){
        String generatedTicketCode;
        do{
            generatedTicketCode= String.format("%06d", new Random().nextInt(1000000));
        }
        while (ticketRepository.checkTicketExistenceByTicketCode(generatedTicketCode));

        return generatedTicketCode;
    }

}
