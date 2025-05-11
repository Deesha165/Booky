package com.example.Bookify.entity.booking;

import com.example.Bookify.entity.event.Event;
import com.example.Bookify.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "bookings",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","event_id"})
})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int id;


    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "event_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;


    @CreationTimestamp
    @Column(name = "booked_at",nullable = false)
    private LocalDateTime bookedAt;

}
