package com.example.Bookify.repository;

import com.example.Bookify.entity.booking.Booking;
import com.example.Bookify.repository.projection.EventReservationDetailsForVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query("""
            select 
            
            e.name as name,
            e.eventTime as eventTime,
            e.venue as venue,
            u.email as userEmail
            from Ticket t
            join t.booking b 
            join b.event e 
            join b.user u 
            where t.ticketCode=:ticketCode  and e.eventTime >= CURRENT_TIMESTAMP
            """)
    Optional<EventReservationDetailsForVerification> findReservationDetailsByTicketCode(String ticketCode);

    void deleteByEventId(int eventId);
}
