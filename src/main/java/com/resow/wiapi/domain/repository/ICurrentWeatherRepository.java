package com.resow.wiapi.domain.repository;

import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import java.util.List;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface ICurrentWeatherRepository {

    Boolean save(CurrentWeather currentWeather);

    List<CurrentWeather> findAllTheLatestPageable(int page, int maxResults);

    List<CurrentWeather> findByTimeAndCityname(CurrentWeather.Time time, String cityname);

    List<CurrentWeather> findByTimeAndWoeid(CurrentWeather.Time time, String woeid);

    void removeByCityname(String cityname);

    void removeByWoeid(String woeid);
    
    void updateAddress(LocationToCollect locationToCollect, LocationToCollect locationToCollectOld);
}
