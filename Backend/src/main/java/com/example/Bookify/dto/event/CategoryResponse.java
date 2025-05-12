package com.example.Bookify.dto.event;

import lombok.Builder;

@Builder
public record CategoryResponse(
        int id,
        String name
) {
}
