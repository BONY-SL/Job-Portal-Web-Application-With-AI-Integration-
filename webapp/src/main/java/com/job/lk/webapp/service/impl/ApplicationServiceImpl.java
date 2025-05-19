package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.ApplicationDTO;
import com.job.lk.webapp.entity.Application;
import com.job.lk.webapp.entity.Job;
import com.job.lk.webapp.entity.User;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.repository.ApplicationRepository;
import com.job.lk.webapp.repository.JobRepository;
import com.job.lk.webapp.repository.UserRepository;
import com.job.lk.webapp.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;


    @Override
    public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {

        var application = Application.builder().jobId(applicationDTO.getJobId()).applicantId(applicationDTO.getApplicantId())
                .resumeUrl(applicationDTO.getResumeUrl()).applicationStatus(applicationDTO.getApplicationStatus()).appliedDate(LocalDate.now()) // Set the current date
                .build();

        Application savedApplication = applicationRepository.save(application);

        return modelMapper.map(savedApplication, ApplicationDTO.class);
    }

    @Override
    public ApplicationDTO updateApplication(Long applicationId, ApplicationDTO applicationDTO) {
        // Find the application to update
        Application existingApplication = applicationRepository.findById(applicationId).orElseThrow(()->
                new ResourceNotFound("Application Not Found...."));
        Job job = jobRepository.findById(applicationDTO.getJobId()).orElseThrow(()->
                new ResourceNotFound("Job Not Found...."));

        existingApplication = existingApplication.toBuilder()
                .jobId(job.getId()).applicantId(applicationDTO.getApplicantId())
                .resumeUrl(applicationDTO.getResumeUrl()).applicationStatus(applicationDTO.getApplicationStatus())
                .appliedDate(applicationDTO.getAppliedDate())
                .build();
        Application updatedApplication = applicationRepository.save(existingApplication);
        return modelMapper.map(updatedApplication, ApplicationDTO.class);
    }

    @Override
    public boolean deleteApplication(Long applicationId) {
        // Check if the application exists and delete
        if (applicationRepository.existsById(applicationId)) {
            applicationRepository.deleteById(applicationId);
            return true;
        }
        return false;
    }

    @Override
    public List<ApplicationDTO> getAllApplications() {

        return applicationRepository.findAll()
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();
    }

    @Override
    public ApplicationDTO getApplicationById(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                ()-> new ResourceNotFound("Not Application With Id : "+applicationId)
        );
        return modelMapper.map(application, ApplicationDTO.class);
    }

    @Override
    public List<ApplicationDTO> getApplicationsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFound("Not User With Id : "+userId)
        );

        return applicationRepository.findByApplicantId(user.getId())
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();
    }

    @Override
    public List<ApplicationDTO> getApplicationsByJobId(Long jobId) {

        Job job = jobRepository.findById(jobId).orElseThrow(()->
                new ResourceNotFound("Job Not Found...."));

        return applicationRepository.findByJobId(job.getId())
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();

    }
}
