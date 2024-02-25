package com.spikes.webflux.handlers;

import com.spikes.webflux.models.ApiError;
import com.spikes.webflux.models.ParamError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiError>> handleGenericException(
            final Exception ex
    ) {
        logger.error(ex.getClass() + ": " + ex.getMessage());
        final ApiError errorResponse = new ApiError(
                "Internal Server Error",
                null
        );
        return Mono.just(
                new ResponseEntity<>(
                        errorResponse,
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
        );
    }

    /**
     * Will get invoke when invalid data is sent in rest request object.
     */
    @Override
    @NonNull
    protected Mono<ResponseEntity<Object>> handleMethodValidationException(
            final MethodValidationException ex,
            @NonNull final HttpStatus status,
            @NonNull final ServerWebExchange exchange
    ) {
        final List<ParamError> paramErrors = new ArrayList<>();
        for (final ParameterValidationResult error : ex.getAllValidationResults()) {
            paramErrors.add(
                    new ParamError(
                            error.getMethodParameter().getParameterName(),
                            error.getResolvableErrors().toString()
                    )
            );
        }
        final ApiError response = new ApiError(
                null,
                paramErrors
        );
        return Mono.just(
                new ResponseEntity<>(response, HttpStatus.BAD_REQUEST)
        );
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Mono<ResponseEntity<ApiError>> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException ex
    ) {
        final ApiError errorResponse = new ApiError(
                ex.getMessage(),
                null
        );
        return Mono.just(
                new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST
                )
        );
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<ApiError>> handleConstraintViolationException(
            final ConstraintViolationException ex
    ) {
        final List<ParamError> paramErrors = new ArrayList<>();
        for (final ConstraintViolation<?> error : ex.getConstraintViolations()) {
            paramErrors.add(
                    new ParamError(
                            error.getPropertyPath().toString().split("\\.")[1],
                            error.getMessage()
                    )
            );
        }
        final ApiError response = new ApiError(
                null,
                paramErrors
        );
        return Mono.just(
                new ResponseEntity<>(response, HttpStatus.BAD_REQUEST)
        );
    }
}