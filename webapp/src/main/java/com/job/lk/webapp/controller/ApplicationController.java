package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.ApplicationDTO;
import com.job.lk.webapp.service.ApplicationService;
import com.job.lk.webapp.util.JsonResponse;
import com.job.lk.webapp.util.Message;
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
    public ResponseEntity<JsonResponse> uploadApplication(@RequestBody ApplicationDTO applicationDTO) throws IOException {
        ApplicationDTO savedApplication = applicationService.createApplication(applicationDTO);
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Job Application Saved Success",
                savedApplication
        ), HttpStatus.CREATED);
    }

    // Update application
    @PutMapping("/{applicationId}")
    public ResponseEntity<JsonResponse> updateApplication(@PathVariable Long applicationId, @RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO updatedApplication = applicationService.updateApplication(applicationId, applicationDTO);
        return updatedApplication != null
                ? new ResponseEntity<>(new JsonResponse(
                        HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                updatedApplication
        ), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete application
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<JsonResponse> deleteApplication(@PathVariable Long applicationId) {
        boolean isDeleted = applicationService.deleteApplication(applicationId);
        return isDeleted ? new ResponseEntity<>(new JsonResponse(
                HttpStatus.NO_CONTENT.value(),
                HttpStatus.NO_CONTENT.name(),
                Message.SUCCESS.name(),
                true
                ),HttpStatus.NO_CONTENT) : new ResponseEntity<>(new JsonResponse(null),HttpStatus.NOT_FOUND);
    }

    // Get all applications
    @GetMapping
    public ResponseEntity<JsonResponse> getAllApplications() {
        List<ApplicationDTO> applications = applicationService.getAllApplications();
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                applications
        ), HttpStatus.OK);
    }

    // Get application by ID
    @GetMapping("/{applicationId}")
    public ResponseEntity<JsonResponse> getApplicationById(@PathVariable Long applicationId) {
        ApplicationDTO applicationDTO = applicationService.getApplicationById(applicationId);
        return applicationDTO != null
                ? new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                applicationDTO), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get all applications for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<JsonResponse> getApplicationsByUserId(@PathVariable Long userId) {
        List<ApplicationDTO> applications = applicationService.getApplicationsByUserId(userId);
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                applications
        ), HttpStatus.OK);
    }

    // Get all applications for a specific job
    @GetMapping("/job/{jobId}")
    public ResponseEntity<JsonResponse> getApplicationsByJobId(@PathVariable Long jobId) {
        List<ApplicationDTO> applications = applicationService.getApplicationsByJobId(jobId);
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                applications
        ), HttpStatus.OK);
    }


}
