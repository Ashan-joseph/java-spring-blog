package org.ashan.controller;

import lombok.extern.slf4j.Slf4j;
import org.ashan.domain.dto.ApiJsonResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionController {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiJsonResponse<Void>> handleUsernameNotFoundException(UsernameNotFoundException ex){
        log.error("An error on oauth deatils", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiJsonResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiJsonResponse<Void>> handleExceptionError(Exception ex){
        log.error("Caught an exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiJsonResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiJsonResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex){
        log.error("Caught an illegal argument exception", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiJsonResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiJsonResponse<Void>> handleIllegalStateException(IllegalStateException ex){
        log.error("Caught an illegal state exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiJsonResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiJsonResponse<Void>> handleDataIntegrityException(DataIntegrityViolationException ex){
        log.error("Caught an illegal state exception", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiJsonResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiJsonResponse<Void>> handleBadCredentialsException(BadCredentialsException ex){
        log.error("Bad credentials exception", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiJsonResponse<>(false, ex.getMessage(), null));
    }
}
