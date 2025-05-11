package com.example.Bookify.dto.event;

import java.time.LocalDateTime;

public record EventReservationDetails(
        String name,

        LocalDateTime eventTime,

        String venue,

        double pricePerTicket,
        String image,
        String ticketCode
) {
}
