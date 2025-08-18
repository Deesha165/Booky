package com.example.Bookify.dto.event;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventVerificationResponse(
        int id,
        String name,
        String venue,
        LocalDateTime eventTime,
        String userEmail

) {
}
