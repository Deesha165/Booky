package com.example.Bookify.entity.booking;


import com.example.Bookify.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tickets",indexes = {
        @Index(name = "idx_tickets_ticket_code",columnList = "ticket_code",unique = true)
})
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int id;


    @Column(name = "ticket_code",nullable = false)
    private String ticketCode;

    @Column(name = "is_used",nullable = false)
    private Boolean isUsed;

    @Column(name = "used_at")
    private LocalDateTime usedAt;


    @JoinColumn(name = "user_id")
    @OneToOne
    private User verifiedBy;

    @JoinColumn(name = "booking_id",nullable = false)
    @OneToOne
    private Booking booking;

}
