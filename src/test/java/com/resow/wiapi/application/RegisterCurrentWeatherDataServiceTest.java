package com.resow.wiapi.application;

import com.resow.wiapi.application.impl.RegisterCurrentWeatherDataService;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.resources.CurrentWeatherRepositoryMock;
import com.resow.wiapi.resources.CurrentWeatherServiceMock;
import com.resow.wiapi.resources.LocationToCollectRepositoryMock;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class RegisterCurrentWeatherDataServiceTest {

    @Test
    public void collectAll() {

        final Long idLocationToCollect = 2l;
        final String cityname = "City-Test";

        final LocationToCollect address = new LocationToCollect();
        address.setId(idLocationToCollect);
        address.setCityname(cityname);

        LocationToCollectRepositoryMock locationToCollectRepositoryMock = new LocationToCollectRepositoryMock(Boolean.TRUE);
        locationToCollectRepositoryMock.save(address);

        CurrentWeatherRepositoryMock currentWeatherRepositoryMock = new CurrentWeatherRepositoryMock();

        RegisterCurrentWeatherDataService registerCurrentWeatherDataService = new RegisterCurrentWeatherDataService(
                new CurrentWeatherServiceMock(),
                currentWeatherRepositoryMock,
                locationToCollectRepositoryMock);

        registerCurrentWeatherDataService.collectAll();

        List<CurrentWeather> findByTimeAndCityname = currentWeatherRepositoryMock.findByTimeAndCityname(CurrentWeather.Time.LAST_30_HOURS, "citytest");

        Assertions.assertEquals(1, findByTimeAndCityname.size());
    }
}
