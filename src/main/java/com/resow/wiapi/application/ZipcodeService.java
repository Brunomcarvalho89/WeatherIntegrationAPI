package com.resow.wiapi.application;

import com.resow.wiapi.application.exceptions.ZipCodeException;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface ZipcodeService {

    Optional<String> addressByZipCode(String zipcode) throws ZipCodeException;

}
