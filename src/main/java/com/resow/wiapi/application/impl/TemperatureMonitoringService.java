package com.resow.wiapi.application.impl;

import java.util.List;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.application.exceptions.LocationToCollectAlreadyRegisterException;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.LocationToCollectWoeid;
import com.resow.wiapi.domain.repository.ICurrentWeatherRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectWoeidRepository;
import java.util.Optional;
import com.resow.wiapi.application.ITemperatureMonitoringService;
import com.resow.wiapi.application.ZipcodeService;
import com.resow.wiapi.application.dto.AllLatestTemperaturesDTO;
import com.resow.wiapi.application.dto.LocationToCollectByCitynameDTO;
import com.resow.wiapi.application.dto.TemperaturesByCitynameLast30DaysDTO;
import com.resow.wiapi.application.dto.assembler.IAllLatestTemperaturesAssemblerDTO;
import com.resow.wiapi.application.dto.assembler.ILocationToCollectAssemblerDTO;
import com.resow.wiapi.application.dto.assembler.ITemperaturesByCityLast30DaysAssemblerDTO;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class TemperatureMonitoringService implements ITemperatureMonitoringService {

    private final ILocationToCollectRepository locationToCollectRepository;
    private final ILocationToCollectWoeidRepository locationToCollectWoeidRepository;
    private final ICurrentWeatherRepository currentWeatherRepository;
    private final ILocationToCollectAssemblerDTO locationToCollectByCitynameAssemblerDTO;
    private final ITemperaturesByCityLast30DaysAssemblerDTO temperaturesByCityLast30DaysAssemblerDTO;
    private final IAllLatestTemperaturesAssemblerDTO allLatestTemperaturesAssemblerDTO;
    private final ZipcodeService zipcodeService;

    public TemperatureMonitoringService(
            ILocationToCollectRepository locationToCollectByCitynameRepository,
            ILocationToCollectWoeidRepository locationToCollectWoeidRepository,
            ICurrentWeatherRepository currentWeatherRepository,
            ILocationToCollectAssemblerDTO locationToCollectByCitynameAssemblerDTO,
            ITemperaturesByCityLast30DaysAssemblerDTO temperaturesByCityLast30DaysAssemblerDTO,
            IAllLatestTemperaturesAssemblerDTO allLatestTemperaturesAssemblerDTO,
            ZipcodeService zipcodeService) {

        this.locationToCollectRepository = locationToCollectByCitynameRepository;
        this.locationToCollectWoeidRepository = locationToCollectWoeidRepository;
        this.currentWeatherRepository = currentWeatherRepository;
        this.locationToCollectByCitynameAssemblerDTO = locationToCollectByCitynameAssemblerDTO;
        this.temperaturesByCityLast30DaysAssemblerDTO = temperaturesByCityLast30DaysAssemblerDTO;
        this.allLatestTemperaturesAssemblerDTO = allLatestTemperaturesAssemblerDTO;
        this.zipcodeService = zipcodeService;
    }

    @Override
    public List<LocationToCollectByCitynameDTO> findAllCitiesForCollect() {
        return this.locationToCollectRepository
                .findAll()
                .stream()
                .map((LocationToCollect arg0) -> {
                    Optional<LocationToCollectByCitynameDTO> assemble = this.locationToCollectByCitynameAssemblerDTO.assemble(arg0);
                    if (assemble.isPresent()) {
                        return assemble.get();
                    }
                    return null;
                }).filter(item -> Objects.nonNull(item))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean locationToCollectExistsByWoeid(String woeid) {
        return this.locationToCollectWoeidRepository.findByWoeid(woeid).isPresent();
    }

    @Override
    public Optional<LocationToCollect> findLocationToCollectById(Long id) {
        return this.locationToCollectRepository.findByID(id);
    }

    @Override
    public Optional<TemperaturesByCitynameLast30DaysDTO> temperaturesByCityname(CurrentWeather.Time time, String cityname) {
        List<CurrentWeather> temperatures = this.currentWeatherRepository.findByTimeAndCityname(time, cityname);
        return this.temperaturesByCityLast30DaysAssemblerDTO.assemble(temperatures);
    }

    @Override
    public Optional<TemperaturesByCitynameLast30DaysDTO> temperaturesByWoeid(CurrentWeather.Time time, String woeid) {
        List<CurrentWeather> temperatures = this.currentWeatherRepository.findByTimeAndWoeid(time, woeid);
        return this.temperaturesByCityLast30DaysAssemblerDTO.assemble(temperatures);
    }

    @Override
    public Optional<LocationToCollectByCitynameDTO> registerByCityname(String cityname) {

        Optional<LocationToCollect> findByCityname = this.locationToCollectRepository.findByCityname(cityname);

        if (findByCityname.isPresent()) {
            throw new LocationToCollectAlreadyRegisterException("Location to collect already register for city: " + cityname);
        }

        LocationToCollect locationToCollect = new LocationToCollect();
        locationToCollect.setCityname(cityname);

        if (this.locationToCollectRepository.save(locationToCollect)) {
            return this.locationToCollectByCitynameAssemblerDTO.assemble(locationToCollect);
        }

        return Optional.empty();
    }

    @Override
    public Optional<LocationToCollectByCitynameDTO> registerByWoeid(String cityname, String woeid) {

        Optional<LocationToCollectWoeid> findByWoeid = this.locationToCollectWoeidRepository.findByWoeid(woeid);

        if (findByWoeid.isPresent()) {
            throw new LocationToCollectAlreadyRegisterException("Location to collect already register for woeid: " + woeid);
        }

        Optional<LocationToCollect> oLocationToCollect = this.locationToCollectWoeidRepository.findByCityname(cityname);

        if (oLocationToCollect.isPresent()) {

            LocationToCollect locationToCollect = oLocationToCollect.get();
            LocationToCollectWoeid locationToCollectWoeid = new LocationToCollectWoeid(locationToCollect, woeid);

            this.locationToCollectWoeidRepository.save(locationToCollectWoeid);
            this.currentWeatherRepository.updateAddress(locationToCollectWoeid, locationToCollect);
            this.locationToCollectRepository.remove(locationToCollect);

        } else {
            LocationToCollectWoeid locationToCollect = new LocationToCollectWoeid();
            locationToCollect.setCityname(cityname);
            locationToCollect.setWoeid(woeid);
            this.locationToCollectWoeidRepository.save(locationToCollect);
        }

        Optional<LocationToCollectWoeid> findByWoeid1 = this.locationToCollectWoeidRepository.findByWoeid(woeid);
        if (findByWoeid1.isPresent()) {
            return this.locationToCollectByCitynameAssemblerDTO.assemble(findByWoeid1.get());
        }

        return Optional.empty();
    }

    @Override
    public Optional<LocationToCollectByCitynameDTO> registerByZipCode(String zipcode) {

        Optional<String> addressByZipCode = this.zipcodeService.addressByZipCode(zipcode);

        if (addressByZipCode.isPresent()) {

            return this.registerByCityname(addressByZipCode.get());
        }

        return Optional.empty();
    }

    @Override
    public void deregisterByCityname(String cityname) {

        Optional<LocationToCollect> findByCityname = this.locationToCollectRepository.findByCityname(cityname);

        if (findByCityname.isEmpty()) {
            return;
        }

        this.locationToCollectRepository.remove(findByCityname.get());
    }

    @Override
    public void deregisterByWoeid(String woeid) {

        Optional<LocationToCollectWoeid> findByWoeid = this.locationToCollectWoeidRepository.findByWoeid(woeid);

        if (findByWoeid.isEmpty()) {
            return;
        }

        this.locationToCollectWoeidRepository.remove(findByWoeid.get());
    }

    @Override
    public void clearTemperaturesByCityname(String cityname) {
        this.currentWeatherRepository.removeByCityname(cityname);
    }

    @Override
    public void clearTemperaturesByWoeid(String woeid) {
        this.currentWeatherRepository.removeByWoeid(woeid);
    }

    @Override
    public Optional<AllLatestTemperaturesDTO> findAllTheLatestTemperatures(int page, int maxResults) {
        List<CurrentWeather> findAllTheLatestPageable = this.currentWeatherRepository.findAllTheLatestPageable(page, maxResults);
        return this.allLatestTemperaturesAssemblerDTO.assemble(findAllTheLatestPageable);
    }

}
