package com.mercadonatest.eanapi.core.handlers;

import com.mercadonatest.eanapi.core.models.exceptions.ConflictEanValueException;
import com.mercadonatest.eanapi.core.models.exceptions.EntityNotFoundException;
import com.mercadonatest.eanapi.core.models.exceptions.PageNotFoundException;
import com.mercadonatest.eanapi.core.models.responses.ApiErrorResponse;
import com.mercadonatest.eanapi.core.models.responses.ParamErrorResponse;
import com.mercadonatest.eanapi.modules.auth.domain.models.exceptions.UnauthorizedException;
import com.mercadonatest.eanapi.core.models.exceptions.MinMaxEanValueException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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
    public ResponseEntity<ApiErrorResponse> handleGenericException(final Exception ex) {
        logger.error(ex.getClass() + ": " + ex.getMessage());
        final ApiErrorResponse errorResponse = ApiErrorResponse
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
        final List<ParamErrorResponse> paramErrors = new ArrayList<>();
        for (final FieldError error : ex.getFieldErrors()) {
            paramErrors.add(
                    ParamErrorResponse
                            .builder()
                            .field(error.getField())
                            .message(error.getDefaultMessage())
                            .build()
            );
        }
        final ApiErrorResponse response = ApiErrorResponse
                .builder()
                .params(paramErrors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when unauthorized exception is thrown.
     */
    @ExceptionHandler(
            {
                    UnauthorizedException.class
            }
    )
    public ResponseEntity<ApiErrorResponse> handleUnauthorizedException(final UnauthorizedException ex) {
        final ApiErrorResponse errorResponse = ApiErrorResponse
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Will get invoke when an NotFoundException is thrown.
     */
    @ExceptionHandler(
            {
                    EntityNotFoundException.class,
                    PageNotFoundException.class
            }
    )
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(final RuntimeException ex) {
        final ApiErrorResponse errorResponse = ApiErrorResponse
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException ex
    ) {
        final ApiErrorResponse errorResponse = ApiErrorResponse
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
            final ConstraintViolationException ex
    ) {
        final List<ParamErrorResponse> paramErrors = new ArrayList<>();
        for (final ConstraintViolation<?> error : ex.getConstraintViolations()) {
            paramErrors.add(
                    ParamErrorResponse
                            .builder()
                            .field(error.getPropertyPath().toString().split("\\.")[1])
                            .message(error.getMessage())
                            .build()
            );
        }
        final ApiErrorResponse response = ApiErrorResponse
                .builder()
                .params(paramErrors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when any API exception is thrown.
     */
    @ExceptionHandler(
            {
                    MinMaxEanValueException.class,
                    ConflictEanValueException.class
            }
    )
    public ResponseEntity<ApiErrorResponse> handleApiException(final RuntimeException ex) {
        final ApiErrorResponse errorResponse = ApiErrorResponse
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}