package com.allane.leasing.exception;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;

import com.allane.leasing.dto.LeasingContractErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class LeasingContractExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<LeasingContractErrorResponseDto> handleNotFound(NotFoundException e) {
        log.error("Not found");
        return new ResponseEntity<>(new LeasingContractErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage(), ZonedDateTime.now()),
                                    HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<LeasingContractErrorResponseDto> handleDateTimeParseException(DateTimeParseException e) {
        log.error("invalid date format");
        return new ResponseEntity<>(new LeasingContractErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), ZonedDateTime.now()),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<LeasingContractErrorResponseDto> handleValidationExceptions(BadRequestException ex) {
        log.error("Bad request", ex);
        return new ResponseEntity<>(
                new LeasingContractErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ZonedDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehicleAssignedException.class)
    public ResponseEntity<LeasingContractErrorResponseDto> handleVehicleAssignedException(VehicleAssignedException ex) {
        return new ResponseEntity<>(
                new LeasingContractErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ZonedDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<LeasingContractErrorResponseDto> handle(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        StringBuilder message = new StringBuilder();
        if (fieldError != null) {
            String rejectReason = fieldError.getDefaultMessage();

            message.append(fieldError.getField()).append(" ");

            if (StringUtils.hasText(rejectReason)) {
                message.append(rejectReason);
            } else {
                message.append("must not be empty");
            }
        } else {
            if (!bindingResult.getAllErrors().isEmpty()) {
                Collection<ObjectError> errors = bindingResult.getAllErrors();
                for (ObjectError e : errors) {
                    message.append("Validation in the request object \"")
                            .append(e.getObjectName())
                            .append("\"")
                            .append(" is failed. Value was rejected by [")
                            .append(e.getCode())
                            .append("] constraint validator")
                            .append(" with message \"")
                            .append(e.getDefaultMessage())
                            .append("\"");
                }
            } else {
                message.append("Field validation failed");
            }
        }
        return new ResponseEntity<>(
                new LeasingContractErrorResponseDto(HttpStatus.BAD_REQUEST.value(), message.toString(), ZonedDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

}
