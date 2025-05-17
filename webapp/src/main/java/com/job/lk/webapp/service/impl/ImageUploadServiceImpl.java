package com.job.lk.webapp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.job.lk.webapp.exception.coustom.FileUploadError;
import com.job.lk.webapp.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class ImageUploadServiceImpl implements ImageUploadService {



    private final Cloudinary cloudinary;

    public ImageUploadServiceImpl(
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
    public String uploadFile(MultipartFile profilePicture) {
        // Uploading image to Cloudinary
        Map<?,?> uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(
                    profilePicture.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "image",  // Ensuring it's treated as an image
                            "public_id", "profile_pictures/" + profilePicture.getOriginalFilename(), // Folder for organization
                            "overwrite", true // Allow overwriting of the image if same name is used
                    )
            );
        } catch (IOException e) {
            throw new FileUploadError("Error Upload File");
        }

        // Return the URL of the uploaded image
        return uploadResult.get("secure_url").toString(); // Return secure URL
    }
}
