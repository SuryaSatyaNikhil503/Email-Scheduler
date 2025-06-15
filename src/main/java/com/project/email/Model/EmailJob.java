/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.Model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> recipients;

    private LocalDateTime scheduledTime;

    private LocalDateTime createdTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EmailStatus status = EmailStatus.PENDING;

    @Column(name = "sent", nullable = false)
    @Builder.Default
    private boolean sent = false;

    @PrePersist
    public void prePersist() {
        this.createdTime = LocalDateTime.now();

        if (this.scheduledTime == null && !this.sent) {
            this.sent = true;
            this.status = EmailStatus.SENT;
        }
    }
}
