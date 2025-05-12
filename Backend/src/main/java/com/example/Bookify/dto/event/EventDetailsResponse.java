package com.example.Bookify.dto.event;

import com.example.Bookify.entity.booking.Booking;
import com.example.Bookify.entity.event.Category;
import com.example.Bookify.entity.user.User;
import lombok.Builder;
import lombok.Setter;

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
                            User creator,
                            Category category,
                            Boolean isBooked
) {
}
