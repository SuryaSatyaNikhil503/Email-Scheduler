/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.DTO;

import com.project.email.Model.EmailStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailJobDTO {
    private Long id;
    private String subject;
    private String body;
    private List<String> recipients;
    private EmailStatus status;
    private boolean sent;
    private LocalDateTime createdTime;
    private LocalDateTime scheduledTime;
}

