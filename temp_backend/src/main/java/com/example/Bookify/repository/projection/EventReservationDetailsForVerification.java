package com.example.Bookify.repository.projection;

import java.time.LocalDateTime;

public interface EventReservationDetailsForVerification {

    String getName();
    LocalDateTime getEventTime();
    String getVenue();
    String getUserEmail();
}
