package com.resow.wiapi.application.dto;

import com.resow.wiapi.application.dto.AllLatestTemperaturesDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class AllLatestTemperaturesDTOTest {

    @Test
    public void testAddTemperature() {

        final String cityname = "cityname";
        final LocalDateTime localDateTime = LocalDateTime.now();
        final Integer iTemperature = 20;

        AllLatestTemperaturesDTO allLatestTemperaturesDTO = new AllLatestTemperaturesDTO();
        allLatestTemperaturesDTO.addTemperature("cityname", localDateTime, iTemperature);

        Assertions.assertEquals(1, allLatestTemperaturesDTO.getTemperatures().size());

        AllLatestTemperaturesDTO.Temperature temperature = allLatestTemperaturesDTO.getTemperatures().stream().findFirst().get();

        Assertions.assertEquals(temperature.getCity(), cityname);
        Assertions.assertEquals(temperature.getDate(), localDateTime);
        Assertions.assertEquals(temperature.getTemperature(), iTemperature);
    }
    
    @Test
    public void testSetTemperature() {

        final String cityname = "cityname";
        final LocalDateTime localDateTime = LocalDateTime.now();
        final Integer iTemperature = 20;

        List<AllLatestTemperaturesDTO.Temperature> temperatures = new ArrayList();
        temperatures.add(new AllLatestTemperaturesDTO.Temperature(cityname, localDateTime, iTemperature));
        
        AllLatestTemperaturesDTO allLatestTemperaturesDTO = new AllLatestTemperaturesDTO();
        allLatestTemperaturesDTO.setTemperatures(temperatures);

        Assertions.assertEquals(1, allLatestTemperaturesDTO.getTemperatures().size());

        AllLatestTemperaturesDTO.Temperature temperature = allLatestTemperaturesDTO.getTemperatures().stream().findFirst().get();

        Assertions.assertEquals(temperature.getCity(), cityname);
        Assertions.assertEquals(temperature.getDate(), localDateTime);
        Assertions.assertEquals(temperature.getTemperature(), iTemperature);
    }
}
