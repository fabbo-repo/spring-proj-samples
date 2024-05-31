package com.prueba.homeworkapp.common.handlers;

import com.prueba.homeworkapp.common.data.exceptions.ApiCodeException;
import com.prueba.homeworkapp.common.data.exceptions.ApiPageNotFoundException;
import com.prueba.homeworkapp.common.data.exceptions.ApiResourceNotFoundException;
import com.prueba.homeworkapp.common.data.models.ApiCodeError;
import com.prueba.homeworkapp.common.data.models.ApiParamError;
import com.prueba.homeworkapp.modules.auth.domain.exceptions.UnauthorizedException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiCodeError> handleGenericException(final Exception ex) {
        logger.error(ex.getClass() + ": " + ex.getMessage());
        logger.error(ExceptionUtils.getStackTrace(ex));

        final ApiCodeError errorResponse = ApiCodeError
                .builder()
                .message("Internal Server Error")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Will get invoke when invalid data is sent in rest request object.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatusCode status,
            @NonNull final WebRequest request
    ) {
        final List<ApiParamError> paramErrors = new ArrayList<>();
        for (final FieldError error : ex.getFieldErrors()) {
            paramErrors.add(
                    ApiParamError
                            .builder()
                            .field(error.getField())
                            .message(error.getDefaultMessage())
                            .build()
            );
        }
        final ApiCodeError response = ApiCodeError
                .builder()
                .params(paramErrors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when unauthorized exception is thrown.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiCodeError> handleUnauthorizedException(final UnauthorizedException ex) {
        final ApiCodeError errorResponse = ApiCodeError
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Will get invoke when any API exception is thrown.
     */
    @ExceptionHandler(ApiCodeException.class)
    public ResponseEntity<ApiCodeError> handleApiException(final ApiCodeException ex) {
        final ApiCodeError errorResponse = ApiCodeError
                .builder()
                .errorCode(ex.getCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when an NotFoundException is thrown.
     */
    @ExceptionHandler({
            ApiPageNotFoundException.class,
            ApiResourceNotFoundException.class
    })
    public ResponseEntity<ApiCodeError> handleNotFoundException(final RuntimeException ex) {
        final ApiCodeError errorResponse = ApiCodeError
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiCodeError> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException ex
    ) {
        final ApiCodeError errorResponse = ApiCodeError
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiCodeError> handleConstraintViolationException(
            final ConstraintViolationException ex
    ) {
        final List<ApiParamError> paramErrors = new ArrayList<>();
        for (final ConstraintViolation<?> error : ex.getConstraintViolations()) {
            paramErrors.add(
                    ApiParamError
                            .builder()
                            .field(error.getPropertyPath().toString().split("\\.")[1])
                            .message(error.getMessage())
                            .build()
            );
        }
        final ApiCodeError response = ApiCodeError
                .builder()
                .params(paramErrors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}