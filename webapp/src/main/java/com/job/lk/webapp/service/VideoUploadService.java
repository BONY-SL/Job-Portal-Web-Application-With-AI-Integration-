package com.job.lk.webapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoUploadService {
    String uploadFile(MultipartFile video) throws IOException;
}
