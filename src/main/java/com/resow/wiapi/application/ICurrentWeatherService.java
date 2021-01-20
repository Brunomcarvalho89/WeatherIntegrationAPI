package com.resow.wiapi.application;

import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface ICurrentWeatherService {

    Optional<CurrentWeather> find(LocationToCollect locationToCollect);

}
