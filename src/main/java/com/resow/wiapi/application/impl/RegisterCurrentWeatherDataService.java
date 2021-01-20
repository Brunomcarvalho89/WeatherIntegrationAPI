package com.resow.wiapi.application.impl;

import com.resow.wiapi.application.ICurrentWeatherService;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.repository.ICurrentWeatherRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectRepository;
import java.util.Optional;
import com.resow.wiapi.application.IRegisterCurrentWeatherDataService;
import com.resow.wiapi.domain.LocationToCollect;
import java.util.List;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class RegisterCurrentWeatherDataService implements IRegisterCurrentWeatherDataService {

    private final ICurrentWeatherService currentWeatherService;
    private final ICurrentWeatherRepository currentWeatherRepository;
    private final ILocationToCollectRepository locationToCollectRepository;

    public RegisterCurrentWeatherDataService(ICurrentWeatherService currentWeatherService,
            ICurrentWeatherRepository currentWeatherRepository,
            ILocationToCollectRepository locationToCollectRepository) {

        this.currentWeatherService = currentWeatherService;
        this.currentWeatherRepository = currentWeatherRepository;
        this.locationToCollectRepository = locationToCollectRepository;
    }

    @Override
    public void collectAll() {

        List<LocationToCollect> locationToCollectList = this.locationToCollectRepository.findAll();

        locationToCollectList
                .forEach(locationToCollect -> {

                    Optional<CurrentWeather> oCurrentWeather = this.currentWeatherService.find(locationToCollect);

                    if (oCurrentWeather.isPresent()) {

                        CurrentWeather currentWeather = oCurrentWeather.get();

                        this.currentWeatherRepository.save(currentWeather);
                    }
                });
    }
}
