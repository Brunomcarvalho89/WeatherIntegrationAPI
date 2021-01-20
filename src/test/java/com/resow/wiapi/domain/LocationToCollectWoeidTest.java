package com.resow.wiapi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author bruno
 */
public class LocationToCollectWoeidTest {

    @Test
    public void testGettersAndSetters() {

        final Long idLocationToCollect = 2l;
        final String cityname = "City-Test";
        final String woeid = "545452";

        final LocationToCollectWoeid address = new LocationToCollectWoeid();
        address.setId(idLocationToCollect);
        address.setCityname(cityname);
        address.setWoeid(woeid);

        Assertions.assertEquals(idLocationToCollect, address.getId());
        Assertions.assertEquals(cityname, address.getCityname());
        Assertions.assertEquals(woeid, address.getWoeid());
    }

}
