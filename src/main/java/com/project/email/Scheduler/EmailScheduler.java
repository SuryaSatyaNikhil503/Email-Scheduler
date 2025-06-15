/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.Scheduler;

import com.project.email.Model.EmailJob;
import com.project.email.Model.EmailStatus;
import com.project.email.Repository.EmailJobRepository;
import com.project.email.Service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailScheduler {

    private final EmailJobRepository repository;
    private final EmailService emailService;

    @Scheduled(fixedRate = 60000) // every 1 min
    public void sendScheduledEmails() {
        LocalDateTime now = LocalDateTime.now();

        // Get all emails scheduled in past or now, still pending, and not sent
        List<EmailJob> jobs = repository.findReadyToSend(now, EmailStatus.PENDING);

        for (EmailJob job : jobs) {
            try {
                emailService.sendEmail(job);
                job.setStatus(EmailStatus.SENT);
                job.setSent(true);
                repository.save(job);

                log.info("✅ Scheduled email sent to: {} | Subject: {}", String.join(", ", job.getRecipients()), job.getSubject());
            } catch (Exception e) {
                log.error("❌ Failed to send scheduled email (ID={}): {}", job.getId(), e.getMessage(), e);
            }
        }
    }
}
