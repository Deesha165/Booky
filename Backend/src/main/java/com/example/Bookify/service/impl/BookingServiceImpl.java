package com.example.Bookify.service.impl;

import com.example.Bookify.dto.booking.BookingRequest;
import com.example.Bookify.dto.event.EventReservationDetails;
import com.example.Bookify.dto.event.EventVerificationResponse;
import com.example.Bookify.entity.booking.Booking;
import com.example.Bookify.entity.booking.Ticket;
import com.example.Bookify.entity.event.Event;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.exception.IllegalActionException;
import com.example.Bookify.mapper.EventMapper;
import com.example.Bookify.repository.BookingRepository;
import com.example.Bookify.repository.TicketRepository;
import com.example.Bookify.repository.projection.EventReservationDetailsForVerification;
import com.example.Bookify.service.BookingService;
import com.example.Bookify.service.EventService;
import com.example.Bookify.service.NotificationService;
import com.example.Bookify.service.UserService;
import com.example.Bookify.util.AuthUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final EventService eventService;
    private final NotificationService notificationService;
    private final AuthUtil authUtil;
private final EventMapper eventMapper;
    public BookingServiceImpl(
            BookingRepository bookingRepository,
            TicketRepository ticketRepository,
            UserService userService,
            @Lazy EventService eventService,
            NotificationService notificationService,
            AuthUtil authUtil, EventMapper eventMapper) {
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.eventService = eventService;
        this.notificationService = notificationService;
        this.authUtil = authUtil;
        this.eventMapper = eventMapper;
    }
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
    public EventVerificationResponse verifyReservation(String ticketCode) throws BadRequestException {

        Optional<EventReservationDetailsForVerification> eventReservationDetailsForVerification=
                bookingRepository.findReservationDetailsByTicketCode(ticketCode);

        if(eventReservationDetailsForVerification.isEmpty()||
                eventReservationDetailsForVerification.get().getUserEmail()==null)
            throw new BadRequestException("Ticket Code is not valid");

        return eventMapper.toEventVerificationResponse(eventReservationDetailsForVerification.get());
    }

    @Override
    public void deleteBookingsByEventId(int eventId) {
        bookingRepository.deleteByEventId(eventId);
    }

    @Override
    public void deleteTicketsByBookingId(int bookingId) {
        ticketRepository.deleteByBookingId(bookingId);
    }

    @Override
    @Transactional
    public void consumeTicket(String ticketCode) {
        Ticket ticket=ticketRepository.findByTicketCode(ticketCode);
        ticket.setIsUsed(true);
        ticket.setUsedAt(LocalDateTime.now());
        User verifier =authUtil.getAuthenticatedUser();

        ticket.setVerifiedBy(verifier);
        ticketRepository.save(ticket);

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
