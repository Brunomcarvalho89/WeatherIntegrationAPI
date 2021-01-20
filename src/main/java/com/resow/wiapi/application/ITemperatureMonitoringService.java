package com.resow.wiapi.application;

import com.resow.wiapi.application.dto.AllLatestTemperaturesDTO;
import com.resow.wiapi.application.dto.LocationToCollectByCitynameDTO;
import com.resow.wiapi.application.dto.TemperaturesByCitynameLast30DaysDTO;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface ITemperatureMonitoringService {

    List<LocationToCollectByCitynameDTO> findAllCitiesForCollect();
    
    Boolean locationToCollectExistsByWoeid(String woeid);

    Optional<LocationToCollect> findLocationToCollectById(Long id);

    Optional<TemperaturesByCitynameLast30DaysDTO> temperaturesByCityname(CurrentWeather.Time time, String cityname);

    Optional<TemperaturesByCitynameLast30DaysDTO> temperaturesByWoeid(CurrentWeather.Time time, String woeid);

    Optional<AllLatestTemperaturesDTO> findAllTheLatestTemperatures(int page, int maxResults);

    Optional<LocationToCollectByCitynameDTO> registerByCityname(String cityname);

    Optional<LocationToCollectByCitynameDTO> registerByWoeid(String cityname, String woeid);
    
    Optional<LocationToCollectByCitynameDTO> registerByZipCode(String cep);

    void deregisterByCityname(String cityname);

    void deregisterByWoeid(String woeid);

    void clearTemperaturesByCityname(String cityname);

    void clearTemperaturesByWoeid(String woeid);
}
