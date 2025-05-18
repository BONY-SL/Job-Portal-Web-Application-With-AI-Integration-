package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.JobDTO;
import com.job.lk.webapp.entity.Company;
import com.job.lk.webapp.entity.Job;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.repository.CompanyRepository;
import com.job.lk.webapp.repository.JobRepository;
import com.job.lk.webapp.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {


    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    @Override
    public JobDTO createJob(JobDTO jobDTO) {

        Optional<Company> optionalCompany = companyRepository.findById(jobDTO.getCompanyId());
         if(optionalCompany.isEmpty()){
             throw new ResourceNotFound("Company Not Found...");
         }

        Job job = jobDtoConvertToHob(jobDTO);
        return jobConvertToDto(jobRepository.save(job));
    }

    @Override
    public List<JobDTO> getAllCompanies() {
        List<Job> jobList = jobRepository.findAll();
        return jobList.stream()
                .map(this::jobConvertToDto).toList();
    }

    @Override
    public JobDTO updateJob(Long id, JobDTO jobDTO) {

        Company company = companyRepository.findById(jobDTO.getCompanyId())
                .orElseThrow(()-> new ResourceNotFound("Company Not Found"));

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Job Not Found"));

        job.setTitle(jobDTO.getTitle());
        job.setLocation(jobDTO.getLocation());
        job.setDescription(jobDTO.getDescription());
        job.setIndustry(jobDTO.getIndustry());
        job.setSalary(jobDTO.getSalary());
        job.setSkillsRequired(jobDTO.getSkillsRequired());
        job.setExperienceRequired(jobDTO.getExperienceRequired());
        job.setActive(jobDTO.isActive());
        job.setPublishDate(jobDTO.getPublishDate());
        job.setCompanyId(company.getId());

        return jobConvertToDto(jobRepository.save(job));
    }

    @Override
    public boolean deleteJob(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()){
            jobRepository.delete(optionalJob.get());
            return true;
        }
        throw new ResourceNotFound("Job Not Found");
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFound("Job Not Found...")
        );
        return jobConvertToDto(job);
    }

    @Override
    public List<JobDTO> getJobsByCompanyIds(List<Long> companyIdsList) {
        List<Job> jobList = jobRepository.findByCompanyIdIn(companyIdsList);
        return jobList.stream().map(this::jobConvertToDto).toList();
    }


    private JobDTO jobConvertToDto(Job job){

        return JobDTO.builder().id(job.getId()).title(job.getTitle())
                .companyId(job.getCompanyId()).location(job.getLocation()).description(job.getDescription())
                .industry(job.getIndustry()).salary(job.getSalary()).skillsRequired(job.getSkillsRequired())
                .experienceRequired(job.getExperienceRequired()).active(job.isActive()).publishDate(job.getPublishDate())
                .build();
    }

    private Job jobDtoConvertToHob(JobDTO jobDTO){

        return Job.builder()
                .id(jobDTO.getId()).title(jobDTO.getTitle()).companyId(jobDTO.getCompanyId())
                .location(jobDTO.getLocation()).description(jobDTO.getDescription()).industry(jobDTO.getIndustry())
                .salary(jobDTO.getSalary()).skillsRequired(jobDTO.getSkillsRequired()).experienceRequired(jobDTO.getExperienceRequired())
                .active(jobDTO.isActive()).publishDate(jobDTO.getPublishDate())
                .build();
    }
}
