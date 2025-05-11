package com.example.Bookify.service;

import com.example.Bookify.repository.projection.EventReservationDetailsForVerification;

public interface BookingService {

    int bookEvent(int eventId,int userId);

    EventReservationDetailsForVerification verifyReservation(String ticketCode);
}
