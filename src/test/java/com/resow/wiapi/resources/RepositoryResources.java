package com.resow.wiapi.resources;

import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.LocationToCollectWoeid;
import com.resow.wiapi.domain.repository.ICurrentWeatherRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectWoeidRepository;
import java.time.LocalDateTime;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class RepositoryResources {

    public void fillDatabase(
            ILocationToCollectRepository locationToCollectRepository,
            ICurrentWeatherRepository currentWeatherRepository,
            ILocationToCollectWoeidRepository locationToCollectWoeidRepository) {

        LocationToCollect addLocationToCollect1 = this.addLocationToCollect(locationToCollectRepository, 1l, "cityname1");
        this.addCurrentWeather(currentWeatherRepository, 1l, 10, LocalDateTime.now().minusHours(10), addLocationToCollect1);
        this.addCurrentWeather(currentWeatherRepository, 2l, 20, LocalDateTime.now().minusHours(29).minusMinutes(59), addLocationToCollect1);
        this.addCurrentWeather(currentWeatherRepository, 3l, 30, LocalDateTime.now().minusHours(30).minusMinutes(1), addLocationToCollect1);

        LocationToCollect addLocationToCollect2 = this.addLocationToCollect(locationToCollectRepository, 2l, "cityname2");
        this.addCurrentWeather(currentWeatherRepository, 4l, 10, LocalDateTime.now().minusHours(10), addLocationToCollect2);
        this.addCurrentWeather(currentWeatherRepository, 5l, 20, LocalDateTime.now().minusHours(29).plusMinutes(59), addLocationToCollect2);
        this.addCurrentWeather(currentWeatherRepository, 6l, 30, LocalDateTime.now().minusHours(30).minusMinutes(1), addLocationToCollect2);

        LocationToCollect addLocationToCollect3 = this.addLocationToCollect(locationToCollectRepository, 3l, "cityname3");
        this.addCurrentWeather(currentWeatherRepository, 7l, 10, LocalDateTime.now().minusHours(10), addLocationToCollect3);
        this.addCurrentWeather(currentWeatherRepository, 8l, 20, LocalDateTime.now().minusHours(29).plusMinutes(59), addLocationToCollect3);
        this.addCurrentWeather(currentWeatherRepository, 9l, 30, LocalDateTime.now().minusHours(30).minusMinutes(1), addLocationToCollect3);

        LocationToCollect addLocationToCollect4 = this.addLocationToCollect(locationToCollectWoeidRepository, 4l, "cityname4-woeid", "5461");
        this.addCurrentWeather(currentWeatherRepository, 7l, 10, LocalDateTime.now().minusHours(10), addLocationToCollect4);
        this.addCurrentWeather(currentWeatherRepository, 8l, 20, LocalDateTime.now().minusHours(29).plusMinutes(59), addLocationToCollect4);
        this.addCurrentWeather(currentWeatherRepository, 9l, 30, LocalDateTime.now().minusHours(30).minusMinutes(1), addLocationToCollect4);
    }

    private LocationToCollect addLocationToCollect(ILocationToCollectRepository locationToCollectRepository, final Long idLocationToCollect, final String cityname) {

        LocationToCollect locationToCollect = new LocationToCollect();
        locationToCollect.setCityname(cityname);

        locationToCollectRepository.save(locationToCollect);

        return locationToCollect;
    }

    private LocationToCollect addLocationToCollect(ILocationToCollectRepository locationToCollectRepository, final Long idLocationToCollect, final String cityname, final String woeid) {

        LocationToCollectWoeid locationToCollect = new LocationToCollectWoeid();
        locationToCollect.setCityname(cityname);
        locationToCollect.setWoeid(woeid);

        locationToCollectRepository.save(locationToCollect);

        return locationToCollect;
    }

    private void addCurrentWeather(ICurrentWeatherRepository currentWeatherRepository, Long id, Integer temperature, LocalDateTime date, LocationToCollect address) {
        CurrentWeather currentWeather = new CurrentWeather(temperature, date, address);
        currentWeatherRepository.save(currentWeather);
    }
}
