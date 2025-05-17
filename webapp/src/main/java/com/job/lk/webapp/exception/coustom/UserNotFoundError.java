package com.job.lk.webapp.exception.coustom;

public class UserNotFoundError extends RuntimeException{

    public UserNotFoundError(String message) {
        super(message);
    }
}
