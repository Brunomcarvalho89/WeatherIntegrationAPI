package com.resow.wiapi.infrastructure.acl.hgbrasilweather.request;

import com.resow.wiapi.infrastructure.acl.Request;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class CurrentWeatherByCityNameRequest extends Request {

    private String cityName;

    public CurrentWeatherByCityNameRequest(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
