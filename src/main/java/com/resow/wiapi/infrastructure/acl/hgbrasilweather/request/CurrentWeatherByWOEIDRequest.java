package com.resow.wiapi.infrastructure.acl.hgbrasilweather.request;

import com.resow.wiapi.infrastructure.acl.Request;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class CurrentWeatherByWOEIDRequest extends Request {

    private String woeid;

    public CurrentWeatherByWOEIDRequest(String woeid) {
        this.woeid = woeid;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }
}
