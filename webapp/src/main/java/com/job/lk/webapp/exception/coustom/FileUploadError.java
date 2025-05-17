package com.job.lk.webapp.exception.coustom;

public class FileUploadError extends RuntimeException{

    public FileUploadError(String message) {
        super(message);
    }
}
