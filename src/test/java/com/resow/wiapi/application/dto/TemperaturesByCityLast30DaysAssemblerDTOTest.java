package com.resow.wiapi.application.dto;

import com.resow.wiapi.application.dto.TemperaturesByCitynameLast30DaysDTO;
import com.resow.wiapi.application.dto.assembler.impl.TemperaturesByCityLast30DaysAssemblerDTO;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class TemperaturesByCityLast30DaysAssemblerDTOTest {

    @Test
    public void testAssemble() {

        final Integer iTemperature = 1;
        final LocalDateTime date = LocalDateTime.now();

        final Long id = 2l;
        final String cityname = "City-Test";

        final LocationToCollect address = new LocationToCollect();
        address.setId(id);
        address.setCityname(cityname);

        CurrentWeather currentWeather = new CurrentWeather(iTemperature, date, address);

        TemperaturesByCityLast30DaysAssemblerDTO TemperaturesByCityLast30DaysAssemblerDTO = new TemperaturesByCityLast30DaysAssemblerDTO();

        Optional<TemperaturesByCitynameLast30DaysDTO> assemble = TemperaturesByCityLast30DaysAssemblerDTO.assemble(List.of(currentWeather));

        Assertions.assertTrue(assemble.isPresent());

        TemperaturesByCitynameLast30DaysDTO allLatestTemperaturesDTO = assemble.get();

        TemperaturesByCitynameLast30DaysDTO.Temperature temperature = allLatestTemperaturesDTO.getTemperatures().stream().findFirst().get();

        Assertions.assertEquals(allLatestTemperaturesDTO.getCity(), cityname);
        Assertions.assertEquals(temperature.getDate(), date);
        Assertions.assertEquals(temperature.getTemperature(), iTemperature);

    }
}
