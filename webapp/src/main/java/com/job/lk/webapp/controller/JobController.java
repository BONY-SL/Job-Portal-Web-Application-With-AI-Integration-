package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.JobDTO;
import com.job.lk.webapp.dto.ResumeUrlDTO;
import com.job.lk.webapp.exception.coustom.JobMatchingException;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.service.JobMatchingService;
import com.job.lk.webapp.service.JobService;
import com.job.lk.webapp.service.impl.ResumeParserService;
import com.job.lk.webapp.util.JsonResponse;
import com.job.lk.webapp.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JobMatchingService jobMatchingService;
    private final ResumeParserService resumeParserService;
    //Create Job
    @PostMapping("/create")
    public ResponseEntity<JsonResponse> createJob(@RequestBody JobDTO jobDTO){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Job Created Successfully....",
                jobService.createJob(jobDTO)
        ),HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<JsonResponse> getAllJobs(){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                jobService.getAllJobs()
        ),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JsonResponse> updateJob(@PathVariable(value = "id") Long id,
                                                  @RequestBody JobDTO jobDTO){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Job Details Update Success",
                jobService.updateJob(id,jobDTO)
        ),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<JsonResponse> deleteJob(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Job Deleted Successfully...",
                jobService.deleteJob(id)
        ),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResponse> getJobById(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                jobService.getJobById(id)
        ),HttpStatus.OK);
    }

    @GetMapping("/companies")
    public ResponseEntity<JsonResponse> getJobsByCompanyIds(@RequestParam(value = "ids") String companyIds){
        try {
            List<Long> companyIdsList = Arrays.stream(companyIds.split(","))
                    .map(Long::parseLong)
                    .toList();
            return new ResponseEntity<>(new JsonResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    Message.SUCCESS.name(),
                    jobService.getJobsByCompanyIds(companyIdsList)
            ),HttpStatus.OK);
        }catch (Exception e){
            throw new ResourceNotFound(e.getMessage());
        }
    }

    @PostMapping("/match-jobs")
    public ResponseEntity<JsonResponse> matchJobs(@RequestBody ResumeUrlDTO resumeUrlDTO){
        System.out.println(resumeUrlDTO.getResumeUrl());

        try{
            String parsedText = resumeParserService.parseResume(resumeUrlDTO);
            System.out.println("parse text: "+parsedText);
            List<JobDTO> matchingJob = jobMatchingService.findMatchingJobs(parsedText);

            return new ResponseEntity<>(new JsonResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    Message.SUCCESS.name(),
                    matchingJob
            ),HttpStatus.OK);

        }catch (Exception e){
            throw new JobMatchingException(e.getMessage());
        }
    }


}
