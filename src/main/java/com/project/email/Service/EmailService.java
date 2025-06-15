/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.Service;

import com.project.email.Model.EmailJob;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(EmailJob job) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(job.getRecipients().toArray(new String[0]));
            helper.setSubject(job.getSubject());
            helper.setText(job.getBody(), true); // true if HTML

            mailSender.send(message);
            log.info("Email successfully sent to {}", job.getRecipients());

        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", job.getRecipients(), e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
