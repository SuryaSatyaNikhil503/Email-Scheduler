/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailStatsDTO {
    private long totalSent;
    private long totalScheduled;
    private long sentToday;
    private long totalFailed;
}
