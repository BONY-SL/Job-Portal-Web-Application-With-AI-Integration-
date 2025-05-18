package com.job.lk.webapp.repository;

import com.job.lk.webapp.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {

    List<Job> findByCompanyIdIn(List<Long> companyIds);
}
