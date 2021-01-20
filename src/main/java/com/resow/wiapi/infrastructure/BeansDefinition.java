package com.resow.wiapi.infrastructure;

import com.resow.wiapi.application.ICurrentWeatherService;
import com.resow.wiapi.application.impl.RegisterCurrentWeatherDataService;
import com.resow.wiapi.domain.repository.ICurrentWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import com.resow.wiapi.application.IRegisterCurrentWeatherDataService;
import org.springframework.context.annotation.Configuration;
import com.resow.wiapi.application.impl.TemperatureMonitoringService;
import com.resow.wiapi.application.dto.assembler.ITemperaturesByCityLast30DaysAssemblerDTO;
import com.resow.wiapi.application.dto.assembler.impl.LocationToCollectByCitynameAssemblerDTO;
import com.resow.wiapi.application.dto.assembler.impl.TemperaturesByCityLast30DaysAssemblerDTO;
import com.resow.wiapi.domain.repository.ILocationToCollectRepository;
import com.resow.wiapi.application.dto.assembler.ILocationToCollectAssemblerDTO;
import com.resow.wiapi.domain.repository.ILocationToCollectWoeidRepository;
import com.resow.wiapi.application.ITemperatureMonitoringService;
import com.resow.wiapi.application.ZipcodeService;
import com.resow.wiapi.application.dto.assembler.IAllLatestTemperaturesAssemblerDTO;
import com.resow.wiapi.application.dto.assembler.impl.AllLatestTemperaturesAssemblerDTO;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Configuration
public class BeansDefinition {

    @Autowired
    private ICurrentWeatherService currentWeatherService;

    @Autowired
    private ICurrentWeatherRepository currentWeatherRepository;

    @Autowired
    private ILocationToCollectRepository locationToCollectRepository;

    @Autowired
    private ILocationToCollectWoeidRepository locationToCollectWoeidRepository;

    @Autowired
    private ZipcodeService zipcodeService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    IRegisterCurrentWeatherDataService registerCurrentWeatherDataService() {
        return new RegisterCurrentWeatherDataService(
                this.currentWeatherService,
                this.currentWeatherRepository,
                this.locationToCollectRepository);
    }

    @Bean
    ITemperatureMonitoringService locationToCollectService() {
        return new TemperatureMonitoringService(
                this.locationToCollectRepository,
                this.locationToCollectWoeidRepository,
                this.currentWeatherRepository,
                this.locationToCollectByCitynameAssemblerDTO(),
                this.temperaturesByCityLast30DaysAssemblerDTO(),
                this.allLatestTemperaturesAssemblerDTO(),
                this.zipcodeService
        );
    }

    @Bean
    ILocationToCollectAssemblerDTO locationToCollectByCitynameAssemblerDTO() {
        return new LocationToCollectByCitynameAssemblerDTO();
    }

    @Bean
    ITemperaturesByCityLast30DaysAssemblerDTO temperaturesByCityLast30DaysAssemblerDTO() {
        return new TemperaturesByCityLast30DaysAssemblerDTO();
    }

    @Bean
    IAllLatestTemperaturesAssemblerDTO allLatestTemperaturesAssemblerDTO() {
        return new AllLatestTemperaturesAssemblerDTO();
    }
}
