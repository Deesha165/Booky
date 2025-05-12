package com.example.Bookify.dto.event;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventReservationDetails(
        String name,

        LocalDateTime eventTime,

        String venue,

        double pricePerTicket,
        String ticketCode
) {
}
