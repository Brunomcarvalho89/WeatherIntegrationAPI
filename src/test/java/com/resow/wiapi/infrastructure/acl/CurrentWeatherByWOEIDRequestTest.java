package com.resow.wiapi.infrastructure.acl;

import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.CurrentWeatherByCityNameRequest;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.CurrentWeatherByWOEIDRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class CurrentWeatherByWOEIDRequestTest {

    @Test
    public void testConstructor() {

        final String woeid = "987654";

        CurrentWeatherByWOEIDRequest currentWeatherByWOEIDRequest = new CurrentWeatherByWOEIDRequest(woeid);

        Assertions.assertEquals(woeid, currentWeatherByWOEIDRequest.getWoeid());
    }

    @Test
    public void testGettersAndSetters() {

        final String woeid = "6542";
        final String woeid2 = "cityname, tst";

        CurrentWeatherByWOEIDRequest currentWeatherByWOEIDRequest = new CurrentWeatherByWOEIDRequest(woeid);
        currentWeatherByWOEIDRequest.setWoeid(woeid2);

        Assertions.assertEquals(woeid2, currentWeatherByWOEIDRequest.getWoeid());
    }

}
