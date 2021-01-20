package com.resow.wiapi.application.dto;

import com.resow.wiapi.application.dto.TemperaturesByCitynameLast30DaysDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class TemperaturesByCitynameLast30DaysDTOTest {

    @Test
    public void testAddTemperature() {

        final String cityname = "cityname";
        final LocalDateTime localDateTime = LocalDateTime.now();
        final Integer iTemperature = 20;

        TemperaturesByCitynameLast30DaysDTO temperaturesByCitynameLast30DaysDTO = new TemperaturesByCitynameLast30DaysDTO();
        temperaturesByCitynameLast30DaysDTO.setCity(cityname);
        temperaturesByCitynameLast30DaysDTO.addTemperature(localDateTime, iTemperature);

        Assertions.assertEquals(1, temperaturesByCitynameLast30DaysDTO.getTemperatures().size());

        TemperaturesByCitynameLast30DaysDTO.Temperature temperature = temperaturesByCitynameLast30DaysDTO.getTemperatures().stream().findFirst().get();

        Assertions.assertEquals(temperaturesByCitynameLast30DaysDTO.getCity(), cityname);
        Assertions.assertEquals(temperature.getDate(), localDateTime);
        Assertions.assertEquals(temperature.getTemperature(), iTemperature);
    }

    @Test
    public void testSetTemperature() {

        final String cityname = "cityname";
        final LocalDateTime localDateTime = LocalDateTime.now();
        final Integer iTemperature = 20;

        List<TemperaturesByCitynameLast30DaysDTO.Temperature> temperatures = new ArrayList();
        temperatures.add(new TemperaturesByCitynameLast30DaysDTO.Temperature(localDateTime, iTemperature));

        TemperaturesByCitynameLast30DaysDTO allLatestTemperaturesDTO = new TemperaturesByCitynameLast30DaysDTO();
        allLatestTemperaturesDTO.setCity(cityname);
        allLatestTemperaturesDTO.setTemperatures(temperatures);

        Assertions.assertEquals(1, allLatestTemperaturesDTO.getTemperatures().size());

        TemperaturesByCitynameLast30DaysDTO.Temperature temperature = allLatestTemperaturesDTO.getTemperatures().stream().findFirst().get();

        Assertions.assertEquals(allLatestTemperaturesDTO.getCity(), cityname);
        Assertions.assertEquals(temperature.getDate(), localDateTime);
        Assertions.assertEquals(temperature.getTemperature(), iTemperature);
    }

}
