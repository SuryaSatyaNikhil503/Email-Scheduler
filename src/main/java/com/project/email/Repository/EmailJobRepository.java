/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

package com.project.email.Repository;

import com.project.email.DTO.CountByDate;
import com.project.email.DTO.EmailStatsTimeSeriesResponseDTO;
import com.project.email.Model.EmailJob;
import com.project.email.Model.EmailStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailJobRepository extends CrudRepository<EmailJob, Long> {

    List<EmailJob> findAll();

    List<EmailJob> findByStatus(EmailStatus status);

    long countByStatus(EmailStatus status);

    // Fixed: changed to createdTime (since sentTime doesn't exist)
    long countByStatusAndCreatedTimeAfter(EmailStatus status, LocalDateTime time);

    @Query("SELECT j FROM EmailJob j JOIN FETCH j.recipients")
    List<EmailJob> findAllWithRecipients();

    @Query("SELECT j FROM EmailJob j JOIN FETCH j.recipients WHERE j.scheduledTime <= :now AND j.status = :status")
    List<EmailJob> findReadyToSend(LocalDateTime now, EmailStatus status);

    // Optional enhancements
    List<EmailJob> findBySubjectContainingIgnoreCase(String keyword);

    @Query("SELECT j FROM EmailJob j WHERE j.createdTime BETWEEN :start AND :end")
    List<EmailJob> findByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT FUNCTION('DATE_FORMAT', e.createdTime, '%Y-%m-%d') AS date, COUNT(e) AS count " +
            "FROM EmailJob e " +
            "WHERE e.status = 'SENT' AND e.createdTime >= :startDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', e.createdTime, '%Y-%m-%d') " +
            "ORDER BY FUNCTION('DATE_FORMAT', e.createdTime, '%Y-%m-%d')")
    List<CountByDate> getDailyStats(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT FUNCTION('DATE_FORMAT', e.createdTime, '%x-W%v') AS date, COUNT(e) AS count " +
            "FROM EmailJob e " +
            "WHERE e.status = 'SENT' AND e.createdTime >= :startDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', e.createdTime, '%x-W%v') " +
            "ORDER BY FUNCTION('DATE_FORMAT', e.createdTime, '%x-W%v')")
    List<CountByDate> getWeeklyStats(@Param("startDate") LocalDateTime startDate);


}
