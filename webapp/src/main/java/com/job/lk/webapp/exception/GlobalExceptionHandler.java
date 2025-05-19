package com.job.lk.webapp.exception;

import com.job.lk.webapp.exception.coustom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<Map<String,Object>> handleUserAlreadyExist(UserAlreadyExist userAlreadyExist, HttpServletRequest httpServletRequest){

        return buildErrorResponse(userAlreadyExist.getMessage(), HttpStatus.CONFLICT, httpServletRequest.getRequestURI());

    }

    @ExceptionHandler(FileUploadError.class)
    public ResponseEntity<Map<String,Object>> handleFileUpload(FileUploadError fileUploadError, HttpServletRequest httpServletRequest){

        return buildErrorResponse(fileUploadError.getMessage(), HttpStatus.BAD_REQUEST, httpServletRequest.getRequestURI());

    }

    @ExceptionHandler(UserNotFoundError.class)
    public ResponseEntity<Map<String,Object>> handleUserNotFound(UserNotFoundError userNotFoundError, HttpServletRequest httpServletRequest){

        return buildErrorResponse(userNotFoundError.getMessage(), HttpStatus.NOT_FOUND, httpServletRequest.getRequestURI());

    }

    @ExceptionHandler(PasswordUnMatchedError.class)
    public ResponseEntity<Map<String,Object>> handlePasswordUnMatched(PasswordUnMatchedError passwordUnMatchedError, HttpServletRequest httpServletRequest){

        return buildErrorResponse(passwordUnMatchedError.getMessage(), HttpStatus.BAD_REQUEST, httpServletRequest.getRequestURI());

    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Map<String,Object>> handleResourceNotFound(ResourceNotFound resourceNotFound, HttpServletRequest httpServletRequest){

        return buildErrorResponse(resourceNotFound.getMessage(), HttpStatus.NOT_FOUND, httpServletRequest.getRequestURI());

    }

    @ExceptionHandler(JobMatchingException.class)
    public ResponseEntity<Map<String,Object>> handleJobMatchingException(JobMatchingException jobMatchingException, HttpServletRequest httpServletRequest){

        return buildErrorResponse(jobMatchingException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, httpServletRequest.getRequestURI());

    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status, String path) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("code", status.value());
        errorResponse.put("status", status);
        errorResponse.put("message", message);
        errorResponse.put("path", path);

        return new ResponseEntity<>(errorResponse, status);
    }
}
