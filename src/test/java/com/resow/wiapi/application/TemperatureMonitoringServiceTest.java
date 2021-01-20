package com.resow.wiapi.application;

import com.resow.wiapi.application.dto.AllLatestTemperaturesDTO;
import com.resow.wiapi.application.dto.LocationToCollectByCitynameDTO;
import com.resow.wiapi.application.dto.TemperaturesByCitynameLast30DaysDTO;
import com.resow.wiapi.application.dto.assembler.impl.AllLatestTemperaturesAssemblerDTO;
import com.resow.wiapi.application.dto.assembler.impl.LocationToCollectByCitynameAssemblerDTO;
import com.resow.wiapi.application.dto.assembler.impl.TemperaturesByCityLast30DaysAssemblerDTO;
import com.resow.wiapi.application.exceptions.LocationToCollectAlreadyRegisterException;
import com.resow.wiapi.application.impl.TemperatureMonitoringService;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.LocationToCollectWoeid;
import com.resow.wiapi.domain.repository.ICurrentWeatherRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectRepository;
import com.resow.wiapi.resources.CurrentWeatherRepositoryMock;
import com.resow.wiapi.resources.LocationToCollectRepositoryMock;
import com.resow.wiapi.resources.LocationToCollectWoeidRepositoryMock;
import com.resow.wiapi.resources.ZipcodeServiceMock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class TemperatureMonitoringServiceTest {

    private TemperatureMonitoringService temperatureMonitoringService;

    @Test
    void testFindAllCitiesForCollect() {

        List<LocationToCollectByCitynameDTO> findAllCitiesForCollect = this.temperatureMonitoringService.findAllCitiesForCollect();

        Assertions.assertEquals(3, findAllCitiesForCollect.size());
    }

    @Test
    void testFindLocationToCollectById() {

        Optional<LocationToCollect> findLocationToCollectById = this.temperatureMonitoringService.findLocationToCollectById(2l);

        Assertions.assertTrue(findLocationToCollectById.isPresent());
        Assertions.assertEquals(2, findLocationToCollectById.get().getId());
    }

    @Test
    void testTemperaturesByCityname() {

        Optional<TemperaturesByCitynameLast30DaysDTO> temperaturesByCityname = this.temperatureMonitoringService.temperaturesByCityname(CurrentWeather.Time.LAST_30_HOURS, "cityname1");

        Assertions.assertTrue(temperaturesByCityname.isPresent());
        Assertions.assertEquals(2, temperaturesByCityname.get().getTemperatures().size());

    }

    @Test
    void testTemperaturesByWoeid() {

        Optional<TemperaturesByCitynameLast30DaysDTO> temperaturesByCityname = this.temperatureMonitoringService.temperaturesByWoeid(CurrentWeather.Time.LAST_30_HOURS, "5461");

        Assertions.assertTrue(temperaturesByCityname.isPresent());
        Assertions.assertEquals(2, temperaturesByCityname.get().getTemperatures().size());
    }

    @Test
    void testFindAllTheLatestTemperatures() {

        Optional<AllLatestTemperaturesDTO> findAllTheLatestTemperatures = this.temperatureMonitoringService.findAllTheLatestTemperatures(1, 2);

        Assertions.assertTrue(findAllTheLatestTemperatures.isPresent());
        Assertions.assertEquals(2, findAllTheLatestTemperatures.get().getTemperatures().size());

        findAllTheLatestTemperatures = this.temperatureMonitoringService.findAllTheLatestTemperatures(2, 2);

        Assertions.assertTrue(findAllTheLatestTemperatures.isPresent());
        Assertions.assertEquals(2, findAllTheLatestTemperatures.get().getTemperatures().size());
    }

    @Test
    void testRegisterByCityname() {

        Optional<LocationToCollectByCitynameDTO> registerByCityname = this.temperatureMonitoringService.registerByCityname("Cidade Teste");
        Assertions.assertTrue(registerByCityname.isPresent());

        Assertions.assertThrows(LocationToCollectAlreadyRegisterException.class, () -> {
            this.temperatureMonitoringService.registerByCityname("Cidade Teste");
        });

        Boolean returnMethodSaveLocationToCollectRepository = Boolean.FALSE;
        this.init(returnMethodSaveLocationToCollectRepository, Boolean.FALSE);

        registerByCityname = this.temperatureMonitoringService.registerByCityname("Cidade Teste");
        Assertions.assertTrue(registerByCityname.isEmpty());

    }

    @Test
    void testRegisterByWoeid() {

        // Testando cidade não cadastrada
        Optional<LocationToCollectByCitynameDTO> registerByCityname = this.temperatureMonitoringService.registerByWoeid("Cidade Teste", "12312");
        Assertions.assertTrue(registerByCityname.isPresent());

        // Testando throw woeid cadastrado.
        Assertions.assertThrows(LocationToCollectAlreadyRegisterException.class, () -> {
            this.temperatureMonitoringService.registerByWoeid("Cidade Teste", "12312");
        });

        // Testando cidade não cadastrada.
        registerByCityname = this.temperatureMonitoringService.registerByWoeid("Cidade Teste", "12313");
        Assertions.assertTrue(registerByCityname.isPresent());

        Boolean returnMethodSaveLocationToCollectRepository = Boolean.FALSE;
        this.init(returnMethodSaveLocationToCollectRepository, Boolean.FALSE);

        registerByCityname = this.temperatureMonitoringService.registerByWoeid("Cidade nova", "22222");
        Assertions.assertTrue(registerByCityname.isEmpty());

    }

    @Test
    void testRegisterByZipCode() {

        Optional<LocationToCollectByCitynameDTO> registerByCityname = this.temperatureMonitoringService.registerByZipCode("25645000");
        Assertions.assertTrue(registerByCityname.isPresent());

        this.init(Boolean.FALSE, Boolean.TRUE);

        registerByCityname = this.temperatureMonitoringService.registerByZipCode("25645000");
        Assertions.assertTrue(registerByCityname.isEmpty());
    }

    @Test
    void testDeregisterByCityname() {

        this.temperatureMonitoringService.deregisterByCityname("cityname1");

        Assertions.assertTrue(this.temperatureMonitoringService
                .findAllCitiesForCollect()
                .stream()
                .filter(item -> item.getCityname().equalsIgnoreCase("cityname1"))
                .findFirst()
                .isEmpty());
    }

    @Test
    void testDeregisterByWoeid() {

        this.temperatureMonitoringService.deregisterByWoeid("5461");

        Assertions.assertTrue(this.temperatureMonitoringService
                .findAllCitiesForCollect()
                .stream()
                .filter(item -> item.getCityname().equalsIgnoreCase("cityname4-woeid"))
                .findFirst()
                .isEmpty());
    }

    @Test
    void testClearTemperaturesByCityname() {

        this.temperatureMonitoringService.clearTemperaturesByCityname("cityname2");

        Assertions.assertTrue(this.temperatureMonitoringService.temperaturesByCityname(CurrentWeather.Time.LAST_30_HOURS, "cityname2").isEmpty());
    }

    @Test
    void testClearTemperaturesByWoeid() {

        this.temperatureMonitoringService.clearTemperaturesByWoeid("5461");

        Assertions.assertTrue(this.temperatureMonitoringService.temperaturesByWoeid(CurrentWeather.Time.LAST_30_HOURS, "5461").isEmpty());
    }

    @BeforeEach
    public void load() {
        this.init(Boolean.TRUE, Boolean.FALSE);
    }

    public void init(Boolean returnMethodSaveLocationToCollectRepository, Boolean zipcodeServiceReturnEmpty) {

        LocationToCollectRepositoryMock locationToCollectRepositoryMock = new LocationToCollectRepositoryMock(returnMethodSaveLocationToCollectRepository);
        LocationToCollectWoeidRepositoryMock locationToCollectWoeidRepositoryMock = new LocationToCollectWoeidRepositoryMock(returnMethodSaveLocationToCollectRepository);
        CurrentWeatherRepositoryMock currentWeatherRepositoryMock = new CurrentWeatherRepositoryMock();
        LocationToCollectByCitynameAssemblerDTO locationToCollectByCitynameAssemblerDTO = new LocationToCollectByCitynameAssemblerDTO();
        TemperaturesByCityLast30DaysAssemblerDTO temperaturesByCityLast30DaysAssemblerDTO = new TemperaturesByCityLast30DaysAssemblerDTO();
        AllLatestTemperaturesAssemblerDTO allLatestTemperaturesAssemblerDTO = new AllLatestTemperaturesAssemblerDTO();
        ZipcodeServiceMock zipcodeServiceMock = new ZipcodeServiceMock();
        ZipcodeServiceMock.ZipcodeServiceEmptyMock zipcodeServiceEmptyMock = new ZipcodeServiceMock.ZipcodeServiceEmptyMock();

        LocationToCollect addLocationToCollect1 = this.addLocationToCollect(locationToCollectRepositoryMock, 1l, "cityname1");
        this.addCurrentWeather(currentWeatherRepositoryMock, 1l, 10, LocalDateTime.now().minusHours(10), addLocationToCollect1);
        this.addCurrentWeather(currentWeatherRepositoryMock, 2l, 20, LocalDateTime.now().minusHours(29).minusMinutes(59), addLocationToCollect1);
        this.addCurrentWeather(currentWeatherRepositoryMock, 3l, 30, LocalDateTime.now().minusHours(30).minusMinutes(1), addLocationToCollect1);

        LocationToCollect addLocationToCollect2 = this.addLocationToCollect(locationToCollectRepositoryMock, 2l, "cityname2");
        this.addCurrentWeather(currentWeatherRepositoryMock, 4l, 10, LocalDateTime.now().minusHours(10), addLocationToCollect2);
        this.addCurrentWeather(currentWeatherRepositoryMock, 5l, 20, LocalDateTime.now().minusHours(29).plusMinutes(59), addLocationToCollect2);
        this.addCurrentWeather(currentWeatherRepositoryMock, 6l, 30, LocalDateTime.now().minusHours(30).minusMinutes(1), addLocationToCollect2);

        LocationToCollect addLocationToCollect3 = this.addLocationToCollect(locationToCollectRepositoryMock, 3l, "cityname3");
        this.addCurrentWeather(currentWeatherRepositoryMock, 7l, 10, LocalDateTime.now().minusHours(10), addLocationToCollect3);
        this.addCurrentWeather(currentWeatherRepositoryMock, 8l, 20, LocalDateTime.now().minusHours(29).plusMinutes(59), addLocationToCollect3);
        this.addCurrentWeather(currentWeatherRepositoryMock, 9l, 30, LocalDateTime.now().minusHours(30).minusMinutes(1), addLocationToCollect3);

        LocationToCollect addLocationToCollect4 = this.addLocationToCollect(locationToCollectWoeidRepositoryMock, 4l, "cityname4-woeid", "5461");
        this.addCurrentWeather(currentWeatherRepositoryMock, 7l, 10, LocalDateTime.now().minusHours(10), addLocationToCollect4);
        this.addCurrentWeather(currentWeatherRepositoryMock, 8l, 20, LocalDateTime.now().minusHours(29).plusMinutes(59), addLocationToCollect4);
        this.addCurrentWeather(currentWeatherRepositoryMock, 9l, 30, LocalDateTime.now().minusHours(30).minusMinutes(1), addLocationToCollect4);

        this.temperatureMonitoringService = new TemperatureMonitoringService(
                locationToCollectRepositoryMock,
                locationToCollectWoeidRepositoryMock,
                currentWeatherRepositoryMock,
                locationToCollectByCitynameAssemblerDTO,
                temperaturesByCityLast30DaysAssemblerDTO,
                allLatestTemperaturesAssemblerDTO,
                zipcodeServiceReturnEmpty ? zipcodeServiceEmptyMock : zipcodeServiceMock);
    }

    private LocationToCollect addLocationToCollect(ILocationToCollectRepository locationToCollectRepository, final Long idLocationToCollect, final String cityname) {

        LocationToCollect locationToCollect = new LocationToCollect(idLocationToCollect, cityname);

        locationToCollectRepository.save(locationToCollect);

        return locationToCollect;
    }

    private LocationToCollect addLocationToCollect(ILocationToCollectRepository locationToCollectRepository, final Long idLocationToCollect, final String cityname, final String woeid) {

        LocationToCollectWoeid locationToCollect = new LocationToCollectWoeid();
        locationToCollect.setId(idLocationToCollect);
        locationToCollect.setCityname(cityname);
        locationToCollect.setWoeid(woeid);

        locationToCollectRepository.save(locationToCollect);

        return locationToCollect;
    }

    private void addCurrentWeather(ICurrentWeatherRepository currentWeatherRepository, Long id, Integer temperature, LocalDateTime date, LocationToCollect address) {

        CurrentWeather currentWeather = new CurrentWeather(temperature, date, address);
        currentWeather.setId(id);

        currentWeatherRepository.save(currentWeather);
    }
}
