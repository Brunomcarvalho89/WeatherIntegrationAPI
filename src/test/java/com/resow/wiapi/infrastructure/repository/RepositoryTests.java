package com.resow.wiapi.infrastructure.repository;

import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.LocationToCollectWoeid;
import com.resow.wiapi.domain.repository.ICurrentWeatherRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectWoeidRepository;
import com.resow.wiapi.infrastructure.WeatherIntegrationAPIApplication;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WeatherIntegrationAPIApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTests {

    @Autowired
    private ILocationToCollectRepository locationToCollectRepository;

    @Autowired
    private ICurrentWeatherRepository currentWeatherRepository;

    @Autowired
    private ILocationToCollectWoeidRepository locationToCollectWoeidRepository;

    private static final String WOEID = "14531";
    private static final String CITYNAME1 = "cityname1";
    private static final String CITYNAME2 = "cityname2";
    private static final String CITYNAME_REMOVE = "cityname-remove";

    @Test
    @Order(1)
    public void testSaveLocationToCollect() {

        LocationToCollect locationToCollect = new LocationToCollect();
        locationToCollect.setCityname(CITYNAME1);
        Assertions.assertTrue(locationToCollectRepository.save(locationToCollect));

        LocationToCollect locationToCollectRemove = new LocationToCollect();
        locationToCollectRemove.setCityname(CITYNAME_REMOVE);
        Assertions.assertTrue(locationToCollectRepository.save(locationToCollectRemove));

        LocationToCollectWoeid locationToCollectWoeid = new LocationToCollectWoeid();
        locationToCollectWoeid.setCityname(CITYNAME2);
        locationToCollectWoeid.setWoeid(WOEID);
        Assertions.assertTrue(locationToCollectRepository.save(locationToCollectWoeid));
    }

    @Test
    @Order(2)
    public void testFindAllLocationToCollect() {
        List<LocationToCollect> findAll = this.locationToCollectRepository.findAll();
        Assertions.assertEquals(3, findAll.size());
    }

    @Test
    @Order(3)
    public void testFindLocationToCollectByCityname() {
        Optional<LocationToCollect> findByCityname = this.locationToCollectRepository.findByCityname(CITYNAME1);
        Assertions.assertTrue(findByCityname.isPresent());
    }

    @Test
    @Order(4)
    public void testFindLocationToCollectByID() {
        Optional<LocationToCollect> findByID = this.locationToCollectRepository.findByID(0l);
        Assertions.assertTrue(findByID.isPresent());
    }

    @Test
    @Order(5)
    public void testFindLocationToCollectByWoeid() {
        Optional<LocationToCollectWoeid> findByWoeid = this.locationToCollectWoeidRepository.findByWoeid(WOEID);
        Assertions.assertTrue(findByWoeid.isPresent());
    }

    @Test
    @Order(6)
    public void testRemoveLocationToCollect() {
        Optional<LocationToCollect> findByCityname = this.locationToCollectRepository.findByCityname(CITYNAME_REMOVE);
        this.locationToCollectRepository.remove(findByCityname.get());
        findByCityname = this.locationToCollectRepository.findByCityname(CITYNAME_REMOVE);
        Assertions.assertTrue(findByCityname.isEmpty());
    }

    @Test
    @Order(7)
    public void testSaveCurrentWeather() {
        Integer temperature = 10;
        LocalDateTime localDateTime = LocalDateTime.now();
        Optional<LocationToCollect> findByCityname = this.locationToCollectRepository.findByCityname(CITYNAME1);
        CurrentWeather currentWeather = new CurrentWeather(temperature, localDateTime, findByCityname.get());
        Assertions.assertTrue(this.currentWeatherRepository.save(currentWeather));

        temperature = 12;
        localDateTime = LocalDateTime.now();
        Optional<LocationToCollectWoeid> findByWoeid = this.locationToCollectWoeidRepository.findByWoeid(WOEID);
        CurrentWeather currentWeatherWoeid = new CurrentWeather(temperature, localDateTime, findByWoeid.get());
        Assertions.assertTrue(this.currentWeatherRepository.save(currentWeatherWoeid));
    }

    @Test
    @Order(8)
    public void testFindTemperaturesByTimeAndCityname() {
        List<CurrentWeather> findByTimeAndCityname = this.currentWeatherRepository.findByTimeAndCityname(CurrentWeather.Time.LAST_30_HOURS, CITYNAME1);
        Assertions.assertTrue(findByTimeAndCityname.size() > 0);
    }

    @Test
    @Order(9)
    public void testFindTemperaturesByTimeAndWoeid() {
        List<CurrentWeather> findByTimeAndCityname = this.currentWeatherRepository.findByTimeAndWoeid(CurrentWeather.Time.LAST_30_HOURS, WOEID);
        Assertions.assertTrue(findByTimeAndCityname.size() > 0);
    }

    @Test
    @Order(10)
    public void testFindAllTheLatestPageableTemperatures() {
        List<CurrentWeather> findAllTheLatestPageable = this.currentWeatherRepository.findAllTheLatestPageable(0, 2);
        Assertions.assertEquals(2, findAllTheLatestPageable.size());
    }

    @Test
    @Order(11)
    public void testUpdateAddressForTemperature() {

        Optional<LocationToCollect> oLocationToCollect = this.locationToCollectRepository.findByCityname(CITYNAME1);

        LocationToCollect locationToCollect = new LocationToCollect();
        locationToCollect.setCityname("cityname3");

        this.locationToCollectRepository.save(locationToCollect);
        this.currentWeatherRepository.updateAddress(locationToCollect, oLocationToCollect.get());
    }

    @Test
    @Order(12)
    public void testRemoveTemperaturesByCityname() {
        this.currentWeatherRepository.removeByCityname(CITYNAME1);
        List<CurrentWeather> findByTimeAndCityname = this.currentWeatherRepository.findByTimeAndCityname(CurrentWeather.Time.LAST_30_HOURS, CITYNAME1);
        Assertions.assertTrue(findByTimeAndCityname.isEmpty());
    }

    @Test
    @Order(13)
    public void testRemoveTemperaturesByWoeid() {
        this.currentWeatherRepository.removeByWoeid(WOEID);
        List<CurrentWeather> findByTimeAndCityname = this.currentWeatherRepository.findByTimeAndCityname(CurrentWeather.Time.LAST_30_HOURS, WOEID);
        Assertions.assertTrue(findByTimeAndCityname.isEmpty());
    }

}
