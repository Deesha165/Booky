package com.example.Bookify.service;

import com.example.Bookify.dto.event.EventReservationDetails;

public interface NotificationService {

    void sendMail(String userEmail, EventReservationDetails eventReservationDetails);
}
