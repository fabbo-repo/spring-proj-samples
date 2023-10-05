package com.spyke.stripe.config;

import com.spyke.stripe.exceptions.PaymentException;
import com.spyke.stripe.exceptions.RequiresPaymentMethodException;
import com.spyke.stripe.exceptions.UnexpectedPaymentMethodException;
import com.spyke.stripe.responses.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
     * Will get invoke when any API exception is thrown.
     */
    @ExceptionHandler(
            {
                    PaymentException.class,
                    RequiresPaymentMethodException.class,
                    UnexpectedPaymentMethodException.class
            }
    )
    public ResponseEntity<ApiErrorResponse> handleApiException(final RuntimeException ex) {
        final ApiErrorResponse errorResponse = ApiErrorResponse
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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
}