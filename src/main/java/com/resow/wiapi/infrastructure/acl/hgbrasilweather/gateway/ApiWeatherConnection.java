package com.resow.wiapi.infrastructure.acl.hgbrasilweather.gateway;

import com.resow.wiapi.application.exceptions.GetDataException;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.IApiWeatherConnection;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.CurrentWeatherByCityNameRequest;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.CurrentWeatherByWOEIDRequest;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.response.CurrentWeatherResponse;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Service
public class ApiWeatherConnection implements IApiWeatherConnection {

    @Value("${url.hgbrasil}")
    private String url;

    @Value("${key.hgbrasil}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<CurrentWeatherResponse> currentWeather(CurrentWeatherByWOEIDRequest request) {

        try {

            String urlString = UriComponentsBuilder
                    .fromUriString(this.url)
                    .encode(Charset.forName("utf-8"))
                    .pathSegment("weather")
                    .queryParam("key", this.key)
                    .queryParam("woeid", request.getWoeid())
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();

            ResponseEntity<CurrentWeatherResponse> response = this.restTemplate.exchange(
                    new URI(urlString),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    CurrentWeatherResponse.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return Optional.of(response.getBody());
            }

            throw new GetDataException("Erro ao obter os dados da api STATUS_CODE: " + response.getStatusCodeValue());

        } catch (Exception ex) {
            throw new GetDataException("Erro ao obter os dados da api: [MESSAGE: " + ex.getMessage() + " ]");
        }
    }

    @Override
    public Optional<CurrentWeatherResponse> currentWeather(CurrentWeatherByCityNameRequest request) {

        try {

            String urlString = UriComponentsBuilder
                    .fromUriString(this.url)
                    .encode(Charset.forName("utf-8"))
                    .pathSegment("weather")
                    .queryParam("key", this.key)
                    .queryParam("city_name", URLEncoder.encode(request.getCityName(), StandardCharsets.UTF_8.toString()))
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();

            ResponseEntity<CurrentWeatherResponse> response = this.restTemplate.exchange(
                    new URI(urlString),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    CurrentWeatherResponse.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return Optional.of(response.getBody());
            }

            throw new GetDataException("Erro ao obter os dados da api STATUS_CODE: " + response.getStatusCodeValue());

        } catch (Exception ex) {
            throw new GetDataException("Erro ao obter os dados da api: [MESSAGE: " + ex.getMessage() + " ]");
        }
    }

}
