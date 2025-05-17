package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.CreateJobDTO;
import com.job.lk.webapp.service.JobMatchingService;
import com.job.lk.webapp.service.JobService;
import com.job.lk.webapp.util.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JobMatchingService jobMatchingService;

    //Create Job
    @PostMapping("/create")
    public ResponseEntity<JsonResponse> createJob(@RequestBody CreateJobDTO createJobDTO){
        return null;
    }
}
