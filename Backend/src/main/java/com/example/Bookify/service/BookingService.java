package com.example.Bookify.service;

import com.example.Bookify.dto.booking.BookingRequest;
import com.example.Bookify.dto.event.EventVerificationResponse;
import com.example.Bookify.repository.projection.EventReservationDetailsForVerification;
import org.apache.coyote.BadRequestException;

public interface BookingService {

    int bookEvent(int eventId);

    EventVerificationResponse verifyReservation(String ticketCode) throws BadRequestException;

    void deleteBookingsByEventId(int eventId);

    void deleteTicketsByBookingId(int bookingId);

    void consumeTicket(String ticketCode);
}
