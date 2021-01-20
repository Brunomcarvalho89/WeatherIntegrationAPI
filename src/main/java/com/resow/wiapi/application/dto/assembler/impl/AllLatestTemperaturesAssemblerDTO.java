package com.resow.wiapi.application.dto.assembler.impl;

import com.resow.wiapi.application.dto.assembler.*;
import com.resow.wiapi.application.dto.AllLatestTemperaturesDTO;
import com.resow.wiapi.domain.CurrentWeather;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class AllLatestTemperaturesAssemblerDTO implements IAllLatestTemperaturesAssemblerDTO {

    @Override
    public Optional<AllLatestTemperaturesDTO> assemble(List<CurrentWeather> currentWeathers) {
        if (currentWeathers.isEmpty()) {
            return Optional.empty();
        }

        AllLatestTemperaturesDTO allLatestTemperaturesDTO = new AllLatestTemperaturesDTO();

        currentWeathers
                .stream()
                .forEach((CurrentWeather currentWeather) -> {
                    allLatestTemperaturesDTO.addTemperature(
                            currentWeather.getAddress().getCityname(),
                            currentWeather.getDate(),
                            currentWeather.getTemperature());
                });

        return Optional.of(allLatestTemperaturesDTO);

    }

}
