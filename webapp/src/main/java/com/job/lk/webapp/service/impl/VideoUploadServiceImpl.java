package com.job.lk.webapp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.job.lk.webapp.service.VideoUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class VideoUploadServiceImpl implements VideoUploadService {

    private final Cloudinary cloudinary;

    public VideoUploadServiceImpl(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    // Video upload functionality
    public String uploadFile(MultipartFile file) throws IOException {
        Map<?,?> uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "video",  // Specify a resource type as video
                        "public_id", "videos/" + file.getOriginalFilename(), // Organize videos in a folder
                        "format", "mp4", // Specify desired video format
                        "chunk_size", 6000000, // Optional: chunking for large videos
                        "fl", "attachment:false" // Prevent forced download
                )
        );

        return uploadResult.get("secure_url").toString(); // Return reviewable video link
    }
}
