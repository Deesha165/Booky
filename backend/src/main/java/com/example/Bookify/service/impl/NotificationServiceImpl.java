package com.example.Bookify.service.impl;


import com.example.Bookify.dto.event.EventReservationDetails;
import com.example.Bookify.exception.IllegalActionException;
import com.example.Bookify.service.NotificationService;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public NotificationServiceImpl(JavaMailSender mailSender,SpringTemplateEngine springTemplateEngine){
        this.mailSender=mailSender;
        this.templateEngine=springTemplateEngine;
    }
    @Value("${source.sender.email}")
    private String SOURCE_SENDER_MAIL;

    @Override
    public void sendMail(String userEmail, EventReservationDetails event) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(userEmail);
            helper.setSubject("Booking Confirmation");
            helper.setFrom(SOURCE_SENDER_MAIL, "Bookify-team");

            String emailContent = buildEmailContent(userEmail,event);
            helper.setText(emailContent, true);

            mailSender.send(mimeMessage);
            log.info("Reset password email sent to {}", userEmail);
        } catch (Exception e) {
            log.error("Failed to send booking confirmation email to {}", userEmail, e);
            throw new IllegalActionException("Failed to send booking confirmation email to " + userEmail);
        }

    }
    private String buildEmailContent(String userEmail, EventReservationDetails eventReservationDetails) {
        Context context = new Context();
        context.setVariable("userEmail", userEmail);
        context.setVariable("eventName", eventReservationDetails.name());
        context.setVariable("eventTime", eventReservationDetails.eventTime());
        context.setVariable("venue", eventReservationDetails.venue());
        context.setVariable("price", eventReservationDetails.pricePerTicket());
        context.setVariable("ticketCode", eventReservationDetails.ticketCode());

        return templateEngine.process("event-reservation-mail", context);
    }
}
