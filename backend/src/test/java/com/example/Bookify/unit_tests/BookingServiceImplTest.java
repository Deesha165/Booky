package com.example.Bookify.unit_tests;

import com.example.Bookify.dto.event.EventReservationDetails;
import com.example.Bookify.entity.booking.Booking;
import com.example.Bookify.entity.booking.Ticket;
import com.example.Bookify.entity.event.Event;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.exception.IllegalActionException;
import com.example.Bookify.mapper.EventMapper;
import com.example.Bookify.repository.BookingRepository;
import com.example.Bookify.repository.TicketRepository;
import com.example.Bookify.service.EventService;
import com.example.Bookify.service.NotificationService;
import com.example.Bookify.service.UserService;
import com.example.Bookify.service.impl.BookingServiceImpl;
import com.example.Bookify.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {
    private BookingRepository bookingRepository;
    private TicketRepository ticketRepository;
    private UserService userService;
    private EventService eventService;
    private NotificationService notificationService;

    private BookingServiceImpl bookingService;
    private AuthUtil authUtil;
    private EventMapper eventMapper;

    @BeforeEach
    void setUp() {
        /// /mock exterenal ddependencies
        bookingRepository = mock(BookingRepository.class);
        ticketRepository = mock(TicketRepository.class);
        userService = mock(UserService.class);
        eventService = mock(EventService.class);
        notificationService = mock(NotificationService.class);
        authUtil=mock(AuthUtil.class);
        eventMapper=mock(EventMapper.class);

        bookingService = new BookingServiceImpl(bookingRepository, ticketRepository, userService, eventService, notificationService,authUtil,eventMapper);
    }
    @Test
    void bookEvent_WhenNoTicketsAvailable_ShouldThrowException(){

        int eventId=3;int userId=1000;
        Event event= Event.builder().availableTickets(0).build();

        when(eventService.getEventEntityById(eventId)).thenReturn(event);


        assertThrows(IllegalActionException.class, () -> bookingService.bookEvent(eventId));
    }
    @Test
    void bookEvent_WhenEmailFails_ShouldStillBook() {
        int eventId = 1;
        int userId = 1;

        Event event = Event.builder().availableTickets(111).name("Event 45757").venue("any").eventTime(LocalDateTime.now())
                        .pricePerTicket(100).build();


        User user = new User();
        user.setEmail("testmustafe@example.com");

        Booking booking = Booking.builder().id(1).user(user).event(event).build();
        Ticket ticket = Ticket.builder().ticketCode("DE45t5F456").booking(booking).build();

        when(bookingRepository.save(any())).thenReturn(booking);
          when(ticketRepository.save(any())).thenReturn(ticket);
                      when(eventService.getEventEntityById(eventId)).thenReturn(event);
        when(userService.getUser(userId)).thenReturn(user);
        when(ticketRepository.checkTicketExistenceByTicketCode(anyString())).thenReturn(false);

        when(authUtil.getAuthenticatedUser()).thenReturn(user);



        doThrow(new RuntimeException("Email failed")).when(notificationService).sendMail(anyString(), any());
        int bookingId = bookingService.bookEvent(eventId);
        assertEquals(1, bookingId);
    }
    @Test
    void bookEvent_WhenBookingFails_ShouldNotSendEmail(){
          int userId=90;
          int eventId=56;
          Event event=Event.builder().availableTickets(44).build();
          User user=User.builder().email("mustafatarek412@gmail.com").build();

          when(eventService.getEventEntityById(eventId)).thenReturn(event);
          when(userService.getUser(userId)).thenReturn(user);
          when(bookingRepository.save(any())).thenThrow(new RuntimeException("a random database error "));
        when(authUtil.getAuthenticatedUser()).thenReturn(user);
          assertThrows(RuntimeException.class,()->bookingService.bookEvent(eventId));
          verify(notificationService,never()).sendMail(anyString(),any());
    }

    @Test
     void bookEvent_WhenTicketsAvailableAndEventExistAndUserExist_ShouldCompleteBooking(){
        int eventId = 1;
        int userId = 1;

        Event event = Event.builder().availableTickets(111).name("Event 45757").venue("any").eventTime(LocalDateTime.now())
                .pricePerTicket(100).build();


        User user = new User();
        user.setEmail("testmustafe@example.com");
       user.setId(99);

        Booking booking = Booking.builder().id(1).user(user).event(event).build();
        Ticket ticket = Ticket.builder().ticketCode("DE45t5F456").booking(booking).build();


        when(bookingRepository.save(any())).thenReturn(booking);
                         when(ticketRepository.save(any())).thenReturn(ticket);
        when(eventService.getEventEntityById(eventId)).thenReturn(event);
        when(userService.getUser(anyInt())).thenReturn(user);
        when(
                ticketRepository.checkTicketExistenceByTicketCode(anyString())).thenReturn(false);
        when(authUtil.getAuthenticatedUser()).thenReturn(user);

        int bookingId = bookingService.bookEvent(eventId);

        assertEquals(1,bookingId);
        verify(notificationService).sendMail(eq(user.getEmail()), any(EventReservationDetails.class));

    }

}
