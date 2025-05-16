package com.example.Bookify.repository;

import com.example.Bookify.entity.booking.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket
        ,Integer> {

    @Query("""
            select exists(select 1 from Ticket t where t.ticketCode=:ticketCode) 
            """)
    Boolean checkTicketExistenceByTicketCode(String ticketCode);

    void deleteByBookingId(int bookingId);
}
