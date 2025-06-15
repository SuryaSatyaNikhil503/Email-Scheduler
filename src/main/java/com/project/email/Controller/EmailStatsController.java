/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.Controller;

import com.project.email.DTO.CountByDate;
import com.project.email.DTO.EmailStatsTimeSeriesResponseDTO;
import com.project.email.Service.EmailStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "*") // Allow all origins for frontend access
public class EmailStatsController {

    private final EmailStatsService emailStatsService;

    @Autowired
    public EmailStatsController(EmailStatsService emailStatsService) {
        this.emailStatsService = emailStatsService;
    }

    @GetMapping("/email/daily")
    public List<CountByDate> getDailyEmailStats() {
        return emailStatsService.getLast7DaysStats();
    }

    @GetMapping("/email/weekly")
    public List<CountByDate> getWeeklyEmailStats() {
        return emailStatsService.getLast30DaysStats();
    }
}
