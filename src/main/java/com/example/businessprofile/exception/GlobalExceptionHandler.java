package com.example.businessprofile.exception;

import com.example.businessprofile.exception.model.*;
import com.example.businessprofile.exception.model.failure.FailureDetails;
import com.example.businessprofile.exception.model.failure.FailureErrorCodes;
import com.example.businessprofile.model.ResponseMaster;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;

import static com.example.businessprofile.utils.BusinessProfileConstants.ExceptionConstants.BAD_REQUEST_4;
import static java.lang.Boolean.FALSE;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMaster.mapResponseMaster(HttpStatus.BAD_REQUEST.toString(), false,
                FailureDetails.builder().description(BAD_REQUEST_4).details(errors).build()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseMaster<Void>> handleBadRequestException(BadRequestException e) {
        log.error(e.getFailureDetails().getDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMaster.mapResponseMaster(e.getFailureErrorCode().toString(), e.isSuccess(),
                FailureDetails.builder().description(e.getFailureDetails().getDescription()).details(e.getFailureDetails().getDetails()).build()));
    }

    @ExceptionHandler(UpdateNotAllowedException.class)
    public ResponseEntity<ResponseMaster<Void>> handleUpdateNotAllowedException(UpdateNotAllowedException e) {
        log.error(e.getFailureDetails().getDescription());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseMaster.mapResponseMaster(e.getFailureErrorCode().toString(), e.isSuccess(),
                FailureDetails.builder().description(e.getFailureDetails().getDescription()).details(e.getFailureDetails().getDetails()).build()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseMaster<Void>> handleConflictException(ConflictException e) {
        log.error(e.getFailureDetails().getDescription());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMaster.mapResponseMaster(e.getFailureErrorCode().toString(), e.isSuccess(),
                FailureDetails.builder().description(e.getFailureDetails().getDescription()).details(e.getFailureDetails().getDetails()).build()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseMaster<Void>> handleNotFoundException(NotFoundException e) {
        log.error(e.getFailureDetails().getDescription());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMaster.mapResponseMaster(e.getFailureErrorCode().toString(), e.isSuccess(),
                FailureDetails.builder().description(e.getFailureDetails().getDescription()).details(e.getFailureDetails().getDetails()).build()));
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ResponseMaster<Void>> handleTechnicalException(TechnicalException e) {
        log.error(e.getFailureDetails().getDescription());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMaster.mapResponseMaster(e.getFailureErrorCode().toString(), e.isSuccess(),
                FailureDetails.builder().description(e.getFailureDetails().getDescription()).details(e.getFailureDetails().getDetails()).build()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseMaster<Void>> handleConstraintViolation(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMaster.mapResponseMaster(String.valueOf(FailureErrorCodes.ERR01), Boolean.FALSE,
                FailureDetails.builder().description(e.getMessage()).build()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseMaster<Void>> handleAllUncaughtException(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMaster.mapResponseMaster(String.valueOf(FailureErrorCodes.ERR01), FALSE,
                FailureDetails.builder().description(e.getMessage()).build()));
    }

}
