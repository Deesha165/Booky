package com.example.Bookify.dto.booking;

public record BookingCreationRequest(

        int eventId,
        int userId
) {
}
