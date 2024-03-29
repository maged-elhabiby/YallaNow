package org.ucalgary.events_service.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.ucalgary.events_service.DTO.ParticipantStatus;
import org.ucalgary.events_service.Entity.EventsEntity;

@Service
public class MailSenderService {

    @Value("classpath:mailAttendBody.txt")
    private Resource mailBodyAttendResource;

    @Value("classpath:mailMaybeBody.txt")
    private Resource mailBodyMaybeResource;

    @Value("classpath:mailNotAttendBody.txt")
    private Resource mailBodyNotAttendResource;

    private final JavaMailSender mailSender;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a new email to the specified recipient.
     * @param to
     * @param subject
     * @param body
     */
    public void sendNewMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    /**
     * Loads the message template for the specified event and user.
     * @param event
     * @param userName
     * @param messageType
     * @return The message template.
     */
    public String loadMessage(EventsEntity event, String userName, ParticipantStatus messageType) {
        try {
            String mailBodyTemplate;
            if(messageType == ParticipantStatus.Attending) {
                mailBodyTemplate = new String(FileCopyUtils.copyToByteArray(mailBodyAttendResource.getInputStream()), StandardCharsets.UTF_8);
            } else if(messageType == ParticipantStatus.Maybe) {
                mailBodyTemplate = new String(FileCopyUtils.copyToByteArray(mailBodyMaybeResource.getInputStream()), StandardCharsets.UTF_8);
            } else if(messageType == ParticipantStatus.NotAttending){
                mailBodyTemplate = new String(FileCopyUtils.copyToByteArray(mailBodyNotAttendResource.getInputStream()), StandardCharsets.UTF_8);
            } else{
                return null;
            }

            mailBodyTemplate = mailBodyTemplate.replace("[UserName]", userName);
            mailBodyTemplate = mailBodyTemplate.replace("[EventDate]",event.getEventStartTime().toLocalDate().toString());
            mailBodyTemplate = mailBodyTemplate.replace("[EventName]", event.getEventTitle());
            mailBodyTemplate = mailBodyTemplate.replace("[EventLocation]", event.getAddress().toString());
            mailBodyTemplate = mailBodyTemplate.replace("[EventStartDate]", event.getEventStartTime().toLocalDate().toString() + " at " + event.getEventStartTime().toLocalTime().toString());
            mailBodyTemplate = mailBodyTemplate.replace("[EventEndDate]", event.getEventEndTime().toLocalDate().toString() + " at " + event.getEventEndTime().toLocalTime().toString());

            return mailBodyTemplate;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a message to the specified user for the specified event.
     * @param event
     * @param userName
     * @param userEmail
     * @param messageType
     */
    public void sendMessage(EventsEntity event, String userName, String userEmail, ParticipantStatus messageType) {
        String message = loadMessage(event, userName, messageType);
        if(message != null) {
            sendNewMail(userEmail, "YallaNow - ConfirmationEmail" , message);
        }else{
            throw new RuntimeException("Error loading message template");
        }
    }
    
}