package com.example.Bookify.service.impl;


import com.example.Bookify.dto.event.EventReservationDetails;
import com.example.Bookify.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendMail(String userEmail, EventReservationDetails eventReservationDetails) {

    }
}
