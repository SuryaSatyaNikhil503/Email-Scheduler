/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.Controller;

import com.project.email.DTO.EmailJobDTO;
import com.project.email.DTO.EmailStatsDTO;
import com.project.email.Model.EmailJob;
import com.project.email.Model.EmailStatus;
import com.project.email.Repository.EmailJobRepository;
import com.project.email.Service.EmailJobService;
import com.project.email.Service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/emails")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailJobRepository repository;
    private final EmailService emailService;
    private final EmailJobService emailJobService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    // Create email job (send now or schedule)
    @PostMapping
    public ResponseEntity<?> createEmail(@RequestBody EmailJob job) {
        String message;

        boolean isImmediate = (job.getScheduledTime() == null || job.getScheduledTime().isBefore(LocalDateTime.now()));

        if (isImmediate) {
            emailService.sendEmail(job); // Send immediately
            job.setStatus(EmailStatus.SENT);
            job.setSent(true);
            message = "Email sent to: " + String.join(", ", job.getRecipients());
        } else {
            job.setStatus(EmailStatus.PENDING); // Scheduled for later
            job.setSent(false);
            message = "Email scheduled at " + FORMATTER.format(job.getScheduledTime())
                    + " to " + String.join(", ", job.getRecipients());
        }

        EmailJob savedJob = repository.save(job);

        log.info("Email Job Created - Subject: {}, Recipients: {}, Status: {}, ScheduledTime: {}",
                savedJob.getSubject(),
                savedJob.getRecipients(),
                savedJob.getStatus(),
                savedJob.getScheduledTime()
        );

        return ResponseEntity.ok(new EmailResponse(message, savedJob));
    }

    // Get all email jobs or filter by status
    @GetMapping
    public ResponseEntity<List<EmailJob>> getAll(@RequestParam(value = "status", required = false) EmailStatus status) {
        List<EmailJob> jobs = (status != null)
                ? repository.findByStatus(status)
                : repository.findAll();
        return ResponseEntity.ok(jobs);
    }

    // Get a specific email job by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmailJob> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a scheduled email job (only if not already sent)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return repository.findById(id).map(job -> {
            if (job.isSent()) {
                return ResponseEntity.badRequest().body("Cannot delete an already sent email.");
            }
            repository.delete(job);
            return ResponseEntity.ok("Email job deleted.");
        }).orElse(ResponseEntity.notFound().build());
    }

    // Update a scheduled email job (only if not already sent)
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody EmailJob updatedJob) {
        return repository.findById(id).map(existing -> {
            if (existing.isSent()) {
                return ResponseEntity.badRequest().body("Cannot update an already sent email.");
            }

            existing.setSubject(updatedJob.getSubject());
            existing.setBody(updatedJob.getBody());
            existing.setRecipients(updatedJob.getRecipients());
            existing.setScheduledTime(updatedJob.getScheduledTime());

            repository.save(existing);
            return ResponseEntity.ok("Email job updated.");
        }).orElse(ResponseEntity.notFound().build());
    }

    // Get email delivery statistics
    @GetMapping("/stats")
    public ResponseEntity<EmailStatsDTO> getStats() {
        return ResponseEntity.ok(emailJobService.getStats());
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<EmailJobDTO>> getJobs() {
        return ResponseEntity.ok(emailJobService.getAllJobs());
    }

    // Inner record for response
    private record EmailResponse(String message, EmailJob job) {
    }
}
