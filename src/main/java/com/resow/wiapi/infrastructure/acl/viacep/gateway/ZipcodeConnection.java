package com.resow.wiapi.infrastructure.acl.viacep.gateway;

import com.resow.wiapi.application.exceptions.ZipCodeException;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface ZipcodeConnection {

    public Optional<String> getAddress(String cep) throws ZipCodeException;
}
