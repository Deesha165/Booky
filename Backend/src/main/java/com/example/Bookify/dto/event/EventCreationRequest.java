package com.example.Bookify.dto.event;

import com.example.Bookify.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
public record EventCreationRequest(
        @NotNull(message = "Event name cannot be null")
        @Size(min = 5,message = "user name must be at least three characters")
        String name,
        String description,

        @NotNull(message = "event time must be stated")
LocalDateTime eventTime,
@NotNull(message = "event venue must be stated")
String venue,

@Min(value = 1)
double pricePerTicket,

        int totalTickets,

      int categoryId,

String image
) {
}

