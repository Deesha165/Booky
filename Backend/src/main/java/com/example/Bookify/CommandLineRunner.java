package com.example.Bookify;

import com.example.Bookify.dto.event.EventReservationDetails;
import com.example.Bookify.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
    private NotificationService notificationService;

    @Override
    public void run(String... args) throws Exception {

/*        String email = "mustafatarek412@gmail.com";
        EventReservationDetails event = new EventReservationDetails("Event1", LocalDateTime.now(), "Cairo", 100.0, "TCK123");

        System.out.println("hello from line runner before send");
        notificationService.sendMail(email,event);
        System.out.println("hello from line runner after send");*/
    }
}
