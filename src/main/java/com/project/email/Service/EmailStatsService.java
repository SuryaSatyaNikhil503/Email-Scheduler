/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.Service;

import com.project.email.DTO.CountByDate;
import com.project.email.DTO.EmailStatsTimeSeriesResponseDTO;
import com.project.email.Repository.EmailJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailStatsService {

    private final EmailJobRepository emailJobRepository;

    @Autowired
    public EmailStatsService(EmailJobRepository emailJobRepository) {
        this.emailJobRepository = emailJobRepository;
    }

    public List<CountByDate> getLast7DaysStats() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        return emailJobRepository.getDailyStats(startDate);
    }

    public List<CountByDate> getLast30DaysStats() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        return emailJobRepository.getWeeklyStats(startDate);
    }

}
