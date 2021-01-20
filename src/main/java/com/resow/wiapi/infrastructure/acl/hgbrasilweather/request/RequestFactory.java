package com.resow.wiapi.infrastructure.acl.hgbrasilweather.request;

import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.LocationToCollectWoeid;

import com.resow.wiapi.infrastructure.acl.Request;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class RequestFactory implements IRequestFactory {

    @Override
    public Request get(LocationToCollect locationToCollect) {

        if (locationToCollect instanceof LocationToCollectWoeid) {
            return new CurrentWeatherByWOEIDRequest(((LocationToCollectWoeid) locationToCollect).getWoeid());
        } else {
            return new CurrentWeatherByCityNameRequest(locationToCollect.getCityname());
        }
    }
}
