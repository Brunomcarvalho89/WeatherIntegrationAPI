package com.resow.wiapi.infrastructure.service.impl;

import com.resow.wiapi.infrastructure.service.IWeatherDataCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import com.resow.wiapi.application.IRegisterCurrentWeatherDataService;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Service
@EnableScheduling
@EnableAsync
@Profile("prod")
public class WeatherDataCollectorScheduled implements IWeatherDataCollector {

    private final IRegisterCurrentWeatherDataService currentWeatherDataService;

    @Autowired
    public WeatherDataCollectorScheduled(IRegisterCurrentWeatherDataService currentWeatherDataService) {
        this.currentWeatherDataService = currentWeatherDataService;
    }

    @Override
    @Async 
    @Scheduled(fixedRate = 5000)
    public void collect() {
        this.currentWeatherDataService.collectAll();
    }
}
