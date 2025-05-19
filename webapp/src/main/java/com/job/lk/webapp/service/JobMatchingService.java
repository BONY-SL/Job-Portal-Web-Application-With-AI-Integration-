package com.job.lk.webapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.job.lk.webapp.dto.JobDTO;

import java.util.List;

public interface JobMatchingService {
    List<JobDTO> findMatchingJobs(String parsedText) throws JsonProcessingException;
}
