package com.example.Bookify.controller;


import com.example.Bookify.dto.booking.BookingRequest;
import com.example.Bookify.repository.projection.EventReservationDetailsForVerification;
import com.example.Bookify.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/booking")
public class BookingController {
    private BookingService bookingService;

    @PostMapping("/book-event/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public int bookEvent(@PathVariable int eventId){

        return bookingService.bookEvent(eventId);
    }

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('VERIFIER')")
    public EventReservationDetailsForVerification verifyReservation(@RequestParam String ticketCode){

        return bookingService.verifyReservation(ticketCode);
    }

}
