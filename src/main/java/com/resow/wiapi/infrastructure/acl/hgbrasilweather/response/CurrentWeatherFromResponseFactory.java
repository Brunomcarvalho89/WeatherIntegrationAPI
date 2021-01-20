package com.resow.wiapi.infrastructure.acl.hgbrasilweather.response;

import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import org.springframework.stereotype.Service;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Service
public class CurrentWeatherFromResponseFactory implements ICurrentWeatherFromResponseFactory {

    public CurrentWeather currentWeather(CurrentWeatherResponse currentWeatherResponse, LocationToCollect locationToCollect) {

        CurrentWeather currentWeather = new CurrentWeather(
                currentWeatherResponse
                        .getResults()
                        .getTemp(),
                currentWeatherResponse
                        .getResults()
                        .getDate(),
                locationToCollect);

        return currentWeather;
    }

}
