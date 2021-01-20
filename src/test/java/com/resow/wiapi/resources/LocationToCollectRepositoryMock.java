package com.resow.wiapi.resources;

import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.repository.ILocationToCollectRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class LocationToCollectRepositoryMock implements ILocationToCollectRepository {

    protected List<LocationToCollect> locationToCollectList = new ArrayList<>();

    private final Boolean returnSaveFalse;

    public LocationToCollectRepositoryMock(Boolean returnSaveFalse) {
        this.returnSaveFalse = returnSaveFalse;
    }

    @Override
    public List<LocationToCollect> findAll() {
        return this.locationToCollectList;
    }

    @Override
    public Optional<LocationToCollect> findByCityname(String cityname) {
        return this.locationToCollectList
                .stream()
                .filter(item -> item.getCityname().equalsIgnoreCase(cityname))
                .findFirst();
    }

    @Override
    public Boolean save(LocationToCollect toCollect) {
        
        if (!returnSaveFalse) {
            return Boolean.FALSE;
        }

        return this.locationToCollectList.add(toCollect);
    }

    @Override
    public void remove(LocationToCollect toCollect) {
        this.locationToCollectList.removeIf(item -> item.getId().equals(toCollect.getId()));
    }

    @Override
    public Optional<LocationToCollect> findByID(Long id) {
        return this.locationToCollectList.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

}
