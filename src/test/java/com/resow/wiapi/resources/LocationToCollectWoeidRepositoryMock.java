package com.resow.wiapi.resources;

import com.resow.wiapi.domain.LocationToCollectWoeid;
import com.resow.wiapi.domain.repository.ILocationToCollectWoeidRepository;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class LocationToCollectWoeidRepositoryMock extends LocationToCollectRepositoryMock implements ILocationToCollectWoeidRepository {

    public LocationToCollectWoeidRepositoryMock() {
        this(Boolean.TRUE);
    }
    
    public LocationToCollectWoeidRepositoryMock(Boolean returnSaveFalse) {
        super(returnSaveFalse);
    }

    @Override
    public Optional<LocationToCollectWoeid> findByWoeid(String woeid) {

        return this.locationToCollectList
                .stream()
                .filter(item -> {

                    if (item instanceof LocationToCollectWoeid) {
                        return ((LocationToCollectWoeid) item).getWoeid().equals(woeid);
                    }
                    return Boolean.FALSE;
                })
                .map(item -> (LocationToCollectWoeid) item)
                .findFirst();
    }
}
