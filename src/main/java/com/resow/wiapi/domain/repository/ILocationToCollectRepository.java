package com.resow.wiapi.domain.repository;

import com.resow.wiapi.domain.LocationToCollect;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface ILocationToCollectRepository {

    List<LocationToCollect> findAll();

    Optional<LocationToCollect> findByCityname(String cityname);

    Boolean save(LocationToCollect toCollect);

    void remove(LocationToCollect toCollect);

    Optional<LocationToCollect> findByID(Long id);
}
