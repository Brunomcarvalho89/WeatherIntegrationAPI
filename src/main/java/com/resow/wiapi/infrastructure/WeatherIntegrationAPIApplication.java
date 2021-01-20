package com.resow.wiapi.infrastructure;

import com.resow.wiapi.domain.CurrentWeather;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@SpringBootApplication
@EntityScan(basePackageClasses = {CurrentWeather.class})
public class WeatherIntegrationAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherIntegrationAPIApplication.class, args);
    }
}
