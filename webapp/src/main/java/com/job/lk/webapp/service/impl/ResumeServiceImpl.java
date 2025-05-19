package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.ResumeDTO;
import com.job.lk.webapp.entity.Resume;
import com.job.lk.webapp.entity.User;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.exception.coustom.UserNotFoundError;
import com.job.lk.webapp.repository.ResumeRepository;
import com.job.lk.webapp.repository.UserRepository;
import com.job.lk.webapp.service.FileUploadService;
import com.job.lk.webapp.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ModelMapper modelMapper;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    @Override
    public ResumeDTO saveResume(ResumeDTO resumeDto) {

        Optional<User> optionalUser = userRepository.findById(resumeDto.getUserId());
        if(optionalUser.isEmpty()){
            throw new UserNotFoundError("User Not Found...");
        }
        var saveResume = Resume.builder()
                .userId(resumeDto.getUserId())
                .resumeUrl(resumeDto.getResumeUrl()).build();

        resumeRepository.save(saveResume);
        return modelMapper.map(saveResume, ResumeDTO.class);
    }

    @Override
    public ResumeDTO getResumeById(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Not Found Resume"));
        return modelMapper.map(resume, ResumeDTO.class);
    }

    @Override
    public boolean deleteResumeById(Long id) {
        Optional<Resume> resume = resumeRepository.findById(id);
        if(resume.isPresent()){
            resumeRepository.delete(resume.get());
            return true;
        }
        throw new ResourceNotFound("Resume Not Found....");
    }

    @Override
    public ResumeDTO updateResume(Long id, MultipartFile file, String resumeUrl) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resume not found"));

        // If a new file is uploaded, update the resume URL
        if (file != null) {
            String newFileUrl = fileUploadService.uploadFile(file);
            resume.setResumeUrl(newFileUrl);
        } else if (resumeUrl != null) {
            resume.setResumeUrl(resumeUrl);
        }
        // Update the existing Resume
        Resume updatedResume = resumeRepository.save(resume);
        return modelMapper.map(updatedResume, ResumeDTO.class);
    }

    @Override
    public List<ResumeDTO> getResumesByUserId(Long userId) {
        return resumeRepository.findByUserId(userId).stream()
                .map(resume -> new ResumeDTO(resume.getId(), resume.getUserId(), resume.getResumeUrl()))
                .collect(Collectors.toList());
    }

}
