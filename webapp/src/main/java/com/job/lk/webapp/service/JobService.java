package com.job.lk.webapp.service;

import com.job.lk.webapp.dto.JobDTO;

import java.util.List;

public interface JobService {
    JobDTO createJob(JobDTO jobDTO);

    List<JobDTO> getAllJobs();

    JobDTO updateJob(Long id, JobDTO jobDTO);

    boolean deleteJob(Long id);

    JobDTO getJobById(Long id);

    List<JobDTO> getJobsByCompanyIds(List<Long> companyIdsList);
}
