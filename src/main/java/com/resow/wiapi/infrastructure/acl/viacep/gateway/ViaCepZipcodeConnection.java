package com.resow.wiapi.infrastructure.acl.viacep.gateway;

import com.resow.wiapi.application.exceptions.ZipCodeException;
import com.resow.wiapi.infrastructure.acl.viacep.gateway.ZipcodeConnection;
import java.net.URI;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Service
public class ViaCepZipcodeConnection implements ZipcodeConnection {

    private static final Logger LOG = Logger.getLogger(ViaCepZipcodeConnection.class.getName());

    @Value("${url.viacep}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<String> getAddress(String cep) throws ZipCodeException {
        try {
            URI URI = new URI(String.format(this.url, cep));

            RequestEntity request = RequestEntity
                    .get(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .build();

            ResponseEntity<String> response = restTemplate.exchange(request, String.class);

            if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new ZipCodeException("Invalid format for the provided zip code.");
            }

            String responseValue = response.getBody();
            if (responseValue.contains("erro")) {
                throw new ZipCodeException("No data for the zip code.");
            }

            return Optional.of(response.getBody());

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
        return Optional.empty();
    }

}
