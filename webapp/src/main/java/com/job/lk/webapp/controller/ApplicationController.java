package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.ApplicationDTO;
import com.job.lk.webapp.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/upload")
    public ResponseEntity<ApplicationDTO> uploadApplication(@RequestBody ApplicationDTO applicationDTO) throws IOException {
        ApplicationDTO savedApplication = applicationService.createApplication(applicationDTO);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    // Update application
    @PutMapping("/{applicationId}")
    public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable Long applicationId, @RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO updatedApplication = applicationService.updateApplication(applicationId, applicationDTO);
        return updatedApplication != null
                ? new ResponseEntity<>(updatedApplication, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete application
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long applicationId) {
        boolean isDeleted = applicationService.deleteApplication(applicationId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get all applications
    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        List<ApplicationDTO> applications = applicationService.getAllApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    // Get application by ID
    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long applicationId) {
        ApplicationDTO applicationDTO = applicationService.getApplicationById(applicationId);
        return applicationDTO != null
                ? new ResponseEntity<>(applicationDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get all applications for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByUserId(@PathVariable Long userId) {
        List<ApplicationDTO> applications = applicationService.getApplicationsByUserId(userId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    // Get all applications for a specific job
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByJobId(@PathVariable Long jobId) {
        List<ApplicationDTO> applications = applicationService.getApplicationsByJobId(jobId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }


}
