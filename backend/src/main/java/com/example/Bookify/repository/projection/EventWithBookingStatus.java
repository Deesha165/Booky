package com.example.Bookify.repository.projection;

import java.time.LocalDateTime;

public interface EventWithBookingStatus {
    int getId();
    String getName();
    String getDescription();
    LocalDateTime getEventTime();
    String getVenue();
    double getPricePerTicket();
    int getAvailableTickets();
    String getImage();
    boolean isBooked();
}
