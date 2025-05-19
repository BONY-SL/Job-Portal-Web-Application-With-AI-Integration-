package com.job.lk.webapp.repository;

import com.job.lk.webapp.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // Find applications by applicant ID (user ID)
    List<Application> findByApplicantId(Long applicantId);

    // Find applications by job ID
    List<Application> findByJobId(Long jobId);
}
