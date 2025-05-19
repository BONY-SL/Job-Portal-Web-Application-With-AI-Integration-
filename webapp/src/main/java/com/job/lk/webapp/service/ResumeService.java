package com.job.lk.webapp.service;

import com.job.lk.webapp.dto.ResumeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    ResumeDTO saveResume(ResumeDTO resumeDto);

    ResumeDTO getResumeById(Long id);

    boolean deleteResumeById(Long id);

    ResumeDTO updateResume(Long id, MultipartFile file, String resumeUrl);

    List<ResumeDTO> getResumesByUserId(Long userId);
}
