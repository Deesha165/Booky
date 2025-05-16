package com.example.Bookify.controller;


import com.example.Bookify.dto.booking.BookingRequest;
import com.example.Bookify.dto.event.EventVerificationResponse;
import com.example.Bookify.repository.projection.EventReservationDetailsForVerification;
import com.example.Bookify.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
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
    public EventVerificationResponse verifyReservation(@RequestParam String ticketCode) throws BadRequestException {

        return bookingService.verifyReservation(ticketCode);
    }

    @PutMapping("/consume")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('VERIFIER')")
    public void consumeTicket(@RequestParam String ticketCode){

        bookingService.consumeTicket(ticketCode);
    }
}
