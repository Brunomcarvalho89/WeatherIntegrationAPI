package com.resow.wiapi.infrastructure.acl;

import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.LocationToCollectWoeid;
import com.resow.wiapi.infrastructure.acl.Request;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.CurrentWeatherByCityNameRequest;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.CurrentWeatherByWOEIDRequest;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.RequestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class RequestFactoryTest {

    @Test
    public void testGet() {

        final Long idLocationToCollect = 2l;
        final String cityname = "City-Test";
        final String woeid = "125436";

        LocationToCollect locationToCollect = new LocationToCollect();
        locationToCollect.setId(idLocationToCollect);
        locationToCollect.setCityname(cityname);

        RequestFactory requestFactory = new RequestFactory();
        Request get = requestFactory.get(locationToCollect);

        Assertions.assertEquals(get.getClass(), CurrentWeatherByCityNameRequest.class);
        Assertions.assertEquals(((CurrentWeatherByCityNameRequest) get).getCityName(), cityname);

        LocationToCollectWoeid locationToCollectWoeid = new LocationToCollectWoeid();
        locationToCollectWoeid.setId(idLocationToCollect);
        locationToCollectWoeid.setCityname(cityname);
        locationToCollectWoeid.setWoeid(woeid);

        get = requestFactory.get(locationToCollectWoeid);

        Assertions.assertEquals(get.getClass(), CurrentWeatherByWOEIDRequest.class);
        Assertions.assertEquals(((CurrentWeatherByWOEIDRequest) get).getWoeid(), woeid);
    }
}
