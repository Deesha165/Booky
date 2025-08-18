package com.example.Bookify.entity.event;


import com.example.Bookify.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "events",indexes = {
        @Index(name = "idx_events_name",columnList="event_name")
})
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;

    @NotNull(message = "Event name cannot be null")
    @Column(name = "event_name",nullable = false,unique = true)
    @Size(min = 3,message = "user name must be at least three characters")
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull(message = "event time must be stated")
    @Column(name = "event_time",nullable = false)
    private LocalDateTime eventTime;

    @NotNull(message = "event venue must be stated")
    @Column(name = "venue",nullable = false)
    private String venue;

    @Column(name = "ticket_price")
    private double pricePerTicket;


    @Column(name = "total_tickets")
    private int totalTickets;

    @Column(name = "available_tickets")
    private int availableTickets;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

     @Column(name = "event_image")
    private String image;

    @PrePersist
    void checksBeforePersistence(){

        if(pricePerTicket<=0) {
            throw new IllegalArgumentException("price of the ticket must be positive");
        }
        if(availableTickets>totalTickets){
            throw new IllegalArgumentException("available tickets quantity must be smaller than total number of tickets");
        }
    }

}
