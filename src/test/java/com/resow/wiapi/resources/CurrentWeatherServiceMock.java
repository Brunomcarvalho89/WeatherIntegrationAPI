package com.resow.wiapi.resources;

import com.resow.wiapi.application.ICurrentWeatherService;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author bruno
 */
public class CurrentWeatherServiceMock implements ICurrentWeatherService {

    @Override
    public Optional<CurrentWeather> find(LocationToCollect locationToCollect) {

        final Integer temperature = 1;
        final LocalDateTime date = LocalDateTime.now();

        final Long id = 2l;
        final String cityname = "citytest";

        final LocationToCollect address = new LocationToCollect();
        address.setId(id);
        address.setCityname(cityname);

        CurrentWeather currentWeather = new CurrentWeather(temperature, date, address);

        return Optional.of(currentWeather);
    }

}
