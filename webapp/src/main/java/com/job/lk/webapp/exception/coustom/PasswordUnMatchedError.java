package com.job.lk.webapp.exception.coustom;

public class PasswordUnMatchedError extends RuntimeException {

    public PasswordUnMatchedError(String message) {
        super(message);
    }
}
