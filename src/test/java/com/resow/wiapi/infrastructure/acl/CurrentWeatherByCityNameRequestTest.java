package com.resow.wiapi.infrastructure.acl;

import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.CurrentWeatherByCityNameRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author bruno
 */
public class CurrentWeatherByCityNameRequestTest {

    @Test
    public void testConstructor() {

        final String cityname = "cityname, tst";

        CurrentWeatherByCityNameRequest currentWeatherByCityNameRequest = new CurrentWeatherByCityNameRequest(cityname);

        Assertions.assertEquals(cityname, currentWeatherByCityNameRequest.getCityName());
    }

    @Test
    public void testGettersAndSetters() {

        final String cityname = "cityname, tst";
        final String cityname2 = "cityname, tst";

        CurrentWeatherByCityNameRequest currentWeatherByCityNameRequest = new CurrentWeatherByCityNameRequest(cityname);
        currentWeatherByCityNameRequest.setCityName(cityname2);

        Assertions.assertEquals(cityname2, currentWeatherByCityNameRequest.getCityName());
    }

}
