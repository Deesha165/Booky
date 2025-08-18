package com.example.Bookify.repository;

import com.example.Bookify.entity.event.Event;
import com.example.Bookify.repository.projection.EventWithBookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Integer> {


    @Query("""
         
            select exists(select 1 from Event e where e.name=:eventName)
            """)
    Boolean checkEventExistence(String eventName);


    @Query("""
    SELECT 
        e.id as id,
        e.name as name,
        e.description as description,
        e.eventTime as eventTime,
        e.venue as venue,
        e.pricePerTicket as pricePerTicket,
        e.availableTickets as availableTickets,
        e.image as image,
        CASE WHEN b.id IS NOT NULL THEN true ELSE false END as booked
    FROM Event e
    LEFT JOIN Booking b ON b.event.id = e.id AND b.user.id = :userId
    ORDER BY e.eventTime DESC
""")
    Page<EventWithBookingStatus> findPagedEvents( int userId, Pageable pageable);


    @Query("""
    SELECT 
        e.id as id,
        e.name as name,
        e.description as description,
        e.eventTime as eventTime,
        e.venue as venue,
        e.pricePerTicket as pricePerTicket,
        e.availableTickets as availableTickets,
        e.image as image,
        CASE WHEN b.id IS NOT NULL THEN true ELSE false END as booked
    FROM Event e
    LEFT JOIN Booking b ON b.event.id = e.id AND b.user.id = :userId
    where e.category.name=:categoryName 
    ORDER BY e.eventTime DESC
""")
    Page<EventWithBookingStatus> findPagedEventsFilteredByCategory(String categoryName,int userId,Pageable pageable);


    @Query("""
              SELECT 
        e.id as id,
        e.name as name,
        e.description as description,
        e.eventTime as eventTime,
        e.venue as venue,
        e.pricePerTicket as pricePerTicket,
        e.availableTickets as availableTickets,
        e.image as image,
        CASE WHEN b.id IS NOT NULL THEN true ELSE false END as booked
    FROM EventTag et
    join et.event e
    join et.tag t
    LEFT JOIN Booking b ON b.event.id = e.id 
    where t.id in :tagIds
    
            """)
    List<EventWithBookingStatus> findTrendingEventsFilteredByTagOccurrence(   @Param("tagIds") List<Integer> tagIds
                                                                              );



}
