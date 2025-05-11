package com.example.Bookify.controller;

import com.example.Bookify.dto.event.EventDetailsResponse;
import com.example.Bookify.repository.projection.EventWithBookingStatus;
import com.example.Bookify.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/event")
public class EventController {
    private final EventService eventService;


    @GetMapping("/trending")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDetailsResponse> getTrendingEvents() {
        return eventService.getTrendingEvents();
    }

}
