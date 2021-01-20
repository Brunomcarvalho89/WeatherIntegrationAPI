package com.resow.wiapi.infrastructure.acl.viacep.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resow.wiapi.application.exceptions.ZipCodeException;
import java.util.Optional;
import com.resow.wiapi.application.ZipcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Service
public class ViaCepZipcodeGateway implements ZipcodeService {

    private final ZipcodeConnection zipcodeConnection;

    @Autowired
    public ViaCepZipcodeGateway(ZipcodeConnection zipcodeConnection) {
        this.zipcodeConnection = zipcodeConnection;
    }

    @Override
    public Optional<String> addressByZipCode(String zipcode) throws ZipCodeException {

        try {

            Optional<String> oAddress = zipcodeConnection.getAddress(zipcode);

            if (oAddress.isPresent()) {

                String address = oAddress.get();

                ViaCepAddress viaCepAddress = new ObjectMapper().readValue(address, ViaCepAddress.class);

                return Optional.of(viaCepAddress.getLocalidade() + ", " + viaCepAddress.getUf());
            }
        } catch (Exception ex) {
            throw new ZipCodeException(ex.getMessage());
        }

        return Optional.empty();
    }

}
