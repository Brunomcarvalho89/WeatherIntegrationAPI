package com.resow.wiapi.resources;

import com.resow.wiapi.application.ZipcodeService;
import com.resow.wiapi.application.exceptions.ZipCodeException;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class ZipcodeServiceMock implements ZipcodeService {

    @Override
    public Optional<String> addressByZipCode(String zipcode) throws ZipCodeException {
        return Optional.of("Rio de Janeiro, RJ");
    }

    public static class ZipcodeServiceEmptyMock implements ZipcodeService {

        @Override
        public Optional<String> addressByZipCode(String zipcode) throws ZipCodeException {
            return Optional.empty();
        }
    }
}
