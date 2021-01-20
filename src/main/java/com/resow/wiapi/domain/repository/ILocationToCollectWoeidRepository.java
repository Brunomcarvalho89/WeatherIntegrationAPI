package com.resow.wiapi.domain.repository;

import com.resow.wiapi.domain.LocationToCollectWoeid;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface ILocationToCollectWoeidRepository extends ILocationToCollectRepository {

    Optional<LocationToCollectWoeid> findByWoeid(String woeid);
}
