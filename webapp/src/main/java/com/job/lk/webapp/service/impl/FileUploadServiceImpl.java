package com.job.lk.webapp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.job.lk.webapp.exception.coustom.FileUploadError;
import com.job.lk.webapp.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class FileUploadServiceImpl implements FileUploadService {


    private final Cloudinary cloudinary;

    public FileUploadServiceImpl(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    @Override
    public String uploadFile(MultipartFile file) {
        Map<?, ?> uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "raw",
                            "public_id", "pdf_files/" + file.getOriginalFilename(),
                            "type", "upload",
                            "access_mode", "public",
                            "fl", "attachment:false"
                    )

            );
        } catch (IOException e) {
            throw new FileUploadError("Error Upload File");
        }

        return uploadResult.get("secure_url").toString(); // Return public URL
    }

}
