package com.resow.wiapi.application.exceptions;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class LocationToCollectNotFoundException extends RuntimeException {

    public LocationToCollectNotFoundException(String message) {
        super(message);
    }
}
