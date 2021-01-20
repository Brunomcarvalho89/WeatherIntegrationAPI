package com.resow.wiapi.application.dto;

import com.resow.wiapi.application.dto.AllLatestTemperaturesDTO;
import com.resow.wiapi.application.dto.LocationToCollectByCitynameDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class LocationToCollectByCitynameDTOTest {

    @Test
    public void testConstructor() {

        final String cityname = "cityname";

        LocationToCollectByCitynameDTO locationToCollectByCitynameDTO = new LocationToCollectByCitynameDTO(cityname);

        Assertions.assertEquals(cityname, locationToCollectByCitynameDTO.getCityname());
    }

    @Test
    public void testSetTemperature() {

        final String cityname = "cityname";
        final String cityname2 = "cityname2";

        LocationToCollectByCitynameDTO locationToCollectByCitynameDTO = new LocationToCollectByCitynameDTO(cityname);
        locationToCollectByCitynameDTO.setCityname(cityname2);

        Assertions.assertEquals(cityname2, locationToCollectByCitynameDTO.getCityname());
    }
}
