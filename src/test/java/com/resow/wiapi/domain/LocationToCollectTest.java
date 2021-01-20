package com.resow.wiapi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author bruno
 */
public class LocationToCollectTest {

    @Test
    public void testConstructor() {

        final Long idLocationToCollect = 2l;
        final String cityname = "City-Test";

        final LocationToCollect address = new LocationToCollect(idLocationToCollect, cityname);
        
        Assertions.assertEquals(idLocationToCollect, address.getId());
        Assertions.assertEquals(cityname, address.getCityname());
    }
    
    @Test
    public void testGettersAndSetters() {

        final Long idLocationToCollect = 2l;
        final String cityname = "City-Test";

        final LocationToCollect address = new LocationToCollect();
        address.setId(idLocationToCollect);
        address.setCityname(cityname);

        Assertions.assertEquals(idLocationToCollect, address.getId());
        Assertions.assertEquals(cityname, address.getCityname());
    }

}
