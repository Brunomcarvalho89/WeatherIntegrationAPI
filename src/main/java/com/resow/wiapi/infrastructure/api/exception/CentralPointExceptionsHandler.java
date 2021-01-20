package com.resow.wiapi.infrastructure.api.exception;

import com.resow.wiapi.application.exceptions.CitynameMustBeInformedException;
import com.resow.wiapi.application.exceptions.LocationToCollectAlreadyRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@ControllerAdvice
public class CentralPointExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CitynameMustBeInformedException.class)
    public ResponseEntity<?> handleCitynameMustBeInformedException(CitynameMustBeInformedException citynameMustBeInformedException) {

        ExceptionDetails exceptionDetails = ExceptionDetails
                .builder()
                .withTitle("Cityname must be informed.")
                .withDetails(citynameMustBeInformedException.getMessage())
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withTimestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LocationToCollectAlreadyRegisterException.class)
    public ResponseEntity<?> handleLocationToCollectAlreadyRegisterException(LocationToCollectAlreadyRegisterException locationToCollectAlreadyRegisterException) {

        ExceptionDetails exceptionDetails = ExceptionDetails
                .builder()
                .withTitle("Location to collect already register for city.")
                .withDetails(locationToCollectAlreadyRegisterException.getMessage())
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withTimestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
}
