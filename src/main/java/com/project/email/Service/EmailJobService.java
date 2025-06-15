/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.Service;

import com.project.email.DTO.EmailJobDTO;
import com.project.email.DTO.EmailStatsDTO;
import com.project.email.Model.EmailJob;
import com.project.email.Model.EmailStatus;
import com.project.email.Repository.EmailJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailJobService {

    private final EmailJobRepository repository;

    public EmailStatsDTO getStats() {
        long totalSent = repository.countByStatus(EmailStatus.SENT);
        long totalScheduled = repository.countByStatus(EmailStatus.PENDING);
        long totalFailed = repository.countByStatus(EmailStatus.FAILED);

        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        long sentToday = repository.countByStatusAndCreatedTimeAfter(EmailStatus.SENT, todayStart);

        return new EmailStatsDTO(totalSent, totalScheduled, sentToday, totalFailed);
    }

    public List<EmailJobDTO> getAllJobs() {
        return repository.findAllWithRecipients().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EmailJobDTO convertToDto(EmailJob job) {
        return new EmailJobDTO(
                job.getId(),
                job.getSubject(),
                job.getBody(),
                job.getRecipients(),
                job.getStatus(),
                job.isSent(),
                job.getCreatedTime(),
                job.getScheduledTime()
        );
    }
}
