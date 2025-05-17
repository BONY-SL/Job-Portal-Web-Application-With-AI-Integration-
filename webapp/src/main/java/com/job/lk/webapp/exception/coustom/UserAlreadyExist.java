package com.job.lk.webapp.exception.coustom;

public class UserAlreadyExist extends RuntimeException{

    public UserAlreadyExist(String message) {
        super(message);
    }
}
