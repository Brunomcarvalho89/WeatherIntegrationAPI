package com.resow.wiapi.infrastructure.acl.hgbrasilweather.response;

import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface ICurrentWeatherFromResponseFactory {

    CurrentWeather currentWeather(CurrentWeatherResponse currentWeatherResponse, LocationToCollect locationToCollect);

}
