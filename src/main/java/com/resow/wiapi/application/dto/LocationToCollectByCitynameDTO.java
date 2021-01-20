package com.resow.wiapi.application.dto;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class LocationToCollectByCitynameDTO extends DTOBase {

    private String cityname;

    public LocationToCollectByCitynameDTO(String cityname) {
        this.cityname = cityname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

}
