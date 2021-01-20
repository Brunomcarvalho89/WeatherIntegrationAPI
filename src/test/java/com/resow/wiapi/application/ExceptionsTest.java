package com.resow.wiapi.application;

import com.resow.wiapi.application.exceptions.CitynameMustBeInformedException;
import com.resow.wiapi.application.exceptions.CurrentWeatherEmptyException;
import com.resow.wiapi.application.exceptions.GetDataException;
import com.resow.wiapi.application.exceptions.LocationToCollectAlreadyRegisterException;
import com.resow.wiapi.application.exceptions.LocationToCollectNotFoundException;
import com.resow.wiapi.application.exceptions.ZipCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class ExceptionsTest {

    @Test
    public void citynameMustBeInformedExceptionTest() {

        final String message = "mensagem de teste";

        CitynameMustBeInformedException citynameMustBeInformedException = new CitynameMustBeInformedException(message);

        Assertions.assertEquals(message, citynameMustBeInformedException.getMessage());
    }

    @Test
    public void currentWeatherEmptyExceptionTest() {

        final String message = "mensagem de teste";

        CurrentWeatherEmptyException currentWeatherEmptyException = new CurrentWeatherEmptyException(message);

        Assertions.assertEquals(message, currentWeatherEmptyException.getMessage());
    }

    @Test
    public void getDataExceptionTest() {

        final String message = "mensagem de teste";

        GetDataException getDataException = new GetDataException(message);

        Assertions.assertEquals(message, getDataException.getMessage());
    }

    @Test
    public void locationToCollectAlreadyRegisterExceptionTest() {

        final String message = "mensagem de teste";

        LocationToCollectAlreadyRegisterException locationToCollectAlreadyRegisterException = new LocationToCollectAlreadyRegisterException(message);

        Assertions.assertEquals(message, locationToCollectAlreadyRegisterException.getMessage());
    }

    @Test
    public void locationToCollectNotFoundExceptionTest() {

        final String message = "mensagem de teste";

        LocationToCollectNotFoundException locationToCollectNotFoundException = new LocationToCollectNotFoundException(message);

        Assertions.assertEquals(message, locationToCollectNotFoundException.getMessage());
    }

    @Test
    public void zipCodeExceptionTest() {

        final String message = "mensagem de teste";

        ZipCodeException zipCodeException = new ZipCodeException(message);

        Assertions.assertEquals(message, zipCodeException.getMessage());
    }

}
