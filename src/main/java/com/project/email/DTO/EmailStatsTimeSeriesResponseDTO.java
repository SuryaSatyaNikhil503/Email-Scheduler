/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EmailStatsTimeSeriesResponseDTO {

    private List<CountByDate> daily;
    private List<CountByDate> weekly;

}
