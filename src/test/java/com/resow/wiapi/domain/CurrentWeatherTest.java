package com.resow.wiapi.domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author bruno
 */
public class CurrentWeatherTest {

    @Test
    public void testConstructor() {

        final Integer temperature = 1;
        final LocalDateTime date = LocalDateTime.now();

        final Long id = 2l;
        final String cityname = "City-Test";

        final LocationToCollect address = new LocationToCollect();
        address.setId(id);
        address.setCityname(cityname);

        CurrentWeather currentWeather = new CurrentWeather(temperature, date, address);

        Assertions.assertEquals(temperature, currentWeather.getTemperature());
        Assertions.assertEquals(date, currentWeather.getDate());
        Assertions.assertEquals(address, currentWeather.getAddress());
    }

    @Test
    public void testGettersAndSetters() {

        final Long id = 12l;
        final Integer temperature = 1;
        final LocalDateTime date = LocalDateTime.now();

        final Long idLocationToCollect = 2l;
        final String cityname = "City-Test";

        final LocationToCollect address = new LocationToCollect();
        address.setId(idLocationToCollect);
        address.setCityname(cityname);

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setId(id);
        currentWeather.setTemperature(temperature);
        currentWeather.setDate(date);
        currentWeather.setAddress(address);

        Assertions.assertEquals(id, currentWeather.getId());
        Assertions.assertEquals(temperature, currentWeather.getTemperature());
        Assertions.assertEquals(date, currentWeather.getDate());
        Assertions.assertEquals(address, currentWeather.getAddress());
    }

    @Test
    public void testCurrentWeatherTime() {
        
        CurrentWeather.Time time = CurrentWeather.Time.LAST_30_HOURS;
        
        LocalDateTime localDateTime = LocalDateTime.now();
        
        Assertions.assertEquals(localDateTime.minusHours(30), time.calculate(localDateTime));
        
    }

}
