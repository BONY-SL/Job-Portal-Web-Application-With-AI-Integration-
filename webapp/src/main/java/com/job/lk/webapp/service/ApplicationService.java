package com.job.lk.webapp.service;

import com.job.lk.webapp.dto.ApplicationDTO;

import java.util.List;

public interface ApplicationService {
    ApplicationDTO createApplication(ApplicationDTO applicationDTO);

    ApplicationDTO updateApplication(Long applicationId, ApplicationDTO applicationDTO);

    boolean deleteApplication(Long applicationId);

    List<ApplicationDTO> getAllApplications();

    ApplicationDTO getApplicationById(Long applicationId);

    List<ApplicationDTO> getApplicationsByUserId(Long userId);

    List<ApplicationDTO> getApplicationsByJobId(Long jobId);
}
