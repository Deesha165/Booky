package com.example.Bookify.service.impl;

import com.example.Bookify.dto.booking.BookingRequest;
import com.example.Bookify.dto.event.EventReservationDetails;
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
import com.example.Bookify.service.NotificationService;
import com.example.Bookify.service.UserService;
import com.example.Bookify.util.AuthUtil;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final NotificationService notificationService;
    private final AuthUtil authUtil;


    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE)
    public int bookEvent(int eventId) {



        Event event=eventService.getEventEntityById(eventId);
        Booking savedBooking;
        if(event.getAvailableTickets()>0){
            event.setAvailableTickets(event.getAvailableTickets()-1);

            User authenticatedUser = authUtil.getAuthenticatedUser();

            User user=userService.getUser(authenticatedUser.getId());
            Booking booking=Booking.builder()
                    .event(event)
                    .user(user)
                    .build();
            savedBooking=bookingRepository.save(booking);

           Ticket createdTicket=  createTicket(savedBooking);

             //send email with details for the userrr with reservation deetails
            EventReservationDetails eventReservationDetails=EventReservationDetails.builder()
                    .name(event.getName())
                    .eventTime(event.getEventTime())
                    .venue(event.getVenue())
                    .pricePerTicket(event.getPricePerTicket())
                    .ticketCode(createdTicket.getTicketCode())
                    .build();

            try{
                notificationService.sendMail(user.getEmail(),eventReservationDetails);
            }
            catch (Exception e){
                log.warn("Failed to send email for booking id {}: {}", savedBooking.getId(), e.getMessage());

            }


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


    private Ticket createTicket(Booking booking){

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
        return  savedTicket;
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
