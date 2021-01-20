package com.resow.wiapi.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Entity(name = "location_to_collect_by_woeid")
@PrimaryKeyJoinColumn(name = "id")
public class LocationToCollectWoeid extends LocationToCollect {

    private String woeid;

    public LocationToCollectWoeid() {
    }

    public LocationToCollectWoeid(LocationToCollect locationToCollect, String woeid) {
        super();
        this.setCityname(locationToCollect.getCityname());
        this.setWoeid(woeid);
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }
}
