package com.example.Bookify.service;

import com.example.Bookify.dto.booking.BookingRequest;
import com.example.Bookify.repository.projection.EventReservationDetailsForVerification;

public interface BookingService {

    int bookEvent(int eventId);

    EventReservationDetailsForVerification verifyReservation(String ticketCode);

    void deleteBookingsByEventId(int eventId);

    void deleteTicketsByBookingId(int bookingId);
}
