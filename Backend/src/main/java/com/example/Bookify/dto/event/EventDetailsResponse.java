package com.example.Bookify.dto.event;

import com.example.Bookify.entity.booking.Booking;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventDetailsResponse(
                            int id,
                            String name,
                            String description,


                            LocalDateTime eventTime,

                            String venue,

                            double pricePerTicket,

                            int availableTickets,
                            String image,
                            Boolean isBooked
) {
}
