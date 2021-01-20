package com.resow.wiapi.application.dto;

import com.resow.wiapi.application.dto.AllLatestTemperaturesDTO;
import com.resow.wiapi.application.dto.assembler.impl.AllLatestTemperaturesAssemblerDTO;
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
public class AllLatestTemperaturesAssemblerDTOTest {

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

        AllLatestTemperaturesAssemblerDTO allLatestTemperaturesAssemblerDTO = new AllLatestTemperaturesAssemblerDTO();
        Optional<AllLatestTemperaturesDTO> assemble = allLatestTemperaturesAssemblerDTO.assemble(List.of(currentWeather));

        Assertions.assertTrue(assemble.isPresent());

        AllLatestTemperaturesDTO allLatestTemperaturesDTO = assemble.get();

        Assertions.assertEquals(1, allLatestTemperaturesDTO.getTemperatures().size());
        AllLatestTemperaturesDTO.Temperature temperature = allLatestTemperaturesDTO.getTemperatures().stream().findFirst().get();

        Assertions.assertEquals(temperature.getCity(), cityname);
        Assertions.assertEquals(temperature.getDate(), date);
        Assertions.assertEquals(temperature.getTemperature(), iTemperature);

    }
}
