package com.resow.wiapi.application.exceptions;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class LocationToCollectAlreadyRegisterException extends RuntimeException {

    public LocationToCollectAlreadyRegisterException(String message) {
        super(message);
    }
}
