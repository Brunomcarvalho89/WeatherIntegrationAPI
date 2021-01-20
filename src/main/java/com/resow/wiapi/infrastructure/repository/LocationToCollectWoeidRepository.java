package com.resow.wiapi.infrastructure.repository;

import com.resow.wiapi.domain.LocationToCollectWoeid;
import com.resow.wiapi.domain.repository.ILocationToCollectWoeidRepository;
import java.util.Optional;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Repository
public class LocationToCollectWoeidRepository extends LocationToCollectRepository implements ILocationToCollectWoeidRepository {

    private static final Logger LOG = Logger.getLogger(LocationToCollectWoeidRepository.class.getName());

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<LocationToCollectWoeid> findByWoeid(String woeid) {
        try {
            TypedQuery<LocationToCollectWoeid> createQuery = this.entityManager.createQuery("select l from location_to_collect_by_woeid l where l.woeid=:woeid", LocationToCollectWoeid.class);
            createQuery.setParameter("woeid", woeid);
            return Optional.of(createQuery.getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
