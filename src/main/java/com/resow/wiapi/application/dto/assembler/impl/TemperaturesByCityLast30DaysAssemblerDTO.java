package com.resow.wiapi.application.dto.assembler.impl;

import com.resow.wiapi.application.dto.TemperaturesByCitynameLast30DaysDTO;
import com.resow.wiapi.domain.CurrentWeather;
import java.util.List;
import com.resow.wiapi.application.dto.assembler.ITemperaturesByCityLast30DaysAssemblerDTO;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class TemperaturesByCityLast30DaysAssemblerDTO implements ITemperaturesByCityLast30DaysAssemblerDTO {

    @Override
    public Optional<TemperaturesByCitynameLast30DaysDTO> assemble(List<CurrentWeather> currentWeathers) {

        if (currentWeathers.isEmpty()) {
            return Optional.empty();
        }

        Optional<CurrentWeather> findFirst = currentWeathers.stream().findFirst();

        TemperaturesByCitynameLast30DaysDTO temperaturesByCitynameLast30DaysDTO = new TemperaturesByCitynameLast30DaysDTO();
        temperaturesByCitynameLast30DaysDTO.setCity(findFirst.get().getAddress().getCityname());

        currentWeathers.forEach((CurrentWeather arg0) -> {
            temperaturesByCitynameLast30DaysDTO.addTemperature(arg0.getDate(), arg0.getTemperature());
        });

        return Optional.of(temperaturesByCitynameLast30DaysDTO);

    }

}
