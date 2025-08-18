package com.example.Bookify.dto.event;

import com.example.Bookify.util.annotation.FutureTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDateTime;

public record EventUpdateRequest(

                                 int eventId,
                                 String description,

                                 @NotNull(message = "event time must be stated")
                                 @FutureTime
                                 LocalDateTime eventTime,

                                 String name,
                                 String venue
) {
}
