package com.job.lk.webapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.lk.webapp.dto.JobDTO;
import com.job.lk.webapp.entity.Job;
import com.job.lk.webapp.repository.JobRepository;
import com.job.lk.webapp.service.JobMatchingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JobMatchingServiceImpl implements JobMatchingService {

    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<JobDTO> findMatchingJobs(String extractedSkills) throws JsonProcessingException {

        if(extractedSkills == null || extractedSkills.trim().isEmpty()){
            System.out.println("Skills...."+ extractedSkills);
            // Return an empty list if no skills provided
            return List.of();
        }

        String jsonResponse = extractedSkills;
        String content =  null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            content = rootNode.path("choices").get(0).path("message").path("content").asText();
            System.out.println(content);
        }catch (Exception e){
            throw new JsonMappingException("Service Method Error");
        }

        List<String> skillList = Arrays.stream(content.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        System.out.println("hi....."+skillList);

        List<Job> allJobs = jobRepository.findAll();
        List<JobDTO> allJobDto = allJobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .toList();


        return allJobDto.stream()
                .filter(job -> {
                    String jobSkills = job.getSkillsRequired().toLowerCase();
                    return skillList.stream().anyMatch(jobSkills::contains);
                })
                .toList();
    }
}
