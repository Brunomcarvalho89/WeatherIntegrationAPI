package com.resow.wiapi.infrastructure.repository;

import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.repository.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Repository
public class LocationToCollectRepository implements ILocationToCollectRepository {

    private static final Logger LOG = Logger.getLogger(LocationToCollectRepository.class.getName());

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<LocationToCollect> findAll() {
        try {
            return this.entityManager
                    .createQuery("select l from location_to_collect l")
                    .getResultList();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            return new ArrayList<LocationToCollect>();
        }
    }

    @Override
    @Transactional
    public Optional<LocationToCollect> findByCityname(String cityname) {
        try {
            TypedQuery<LocationToCollect> createQuery = this.entityManager.createQuery("select l from location_to_collect l where l.cityname like CONCAT('%', :cityname, '%')", LocationToCollect.class);
            createQuery.setParameter("cityname", cityname);
            return Optional.of(createQuery.getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Boolean save(LocationToCollect locationToCollect) {
        try {
            this.entityManager.persist(locationToCollect);
            return Boolean.TRUE;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro: Não foi possível salvar os dados de CurrentWeather.");
            LOG.log(Level.SEVERE, "Erro: CurrentWeatherRepository => " + ex.getMessage());
            return Boolean.FALSE;
        }
    }

    @Override
    public Optional<LocationToCollect> findByID(Long id) {
        try {
            TypedQuery<LocationToCollect> createQuery = this.entityManager.createQuery("select l from location_to_collect l where l.id=:id", LocationToCollect.class);
            createQuery.setParameter("id", id);
            return Optional.of(createQuery.getSingleResult());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void remove(LocationToCollect toCollect) {
        this.entityManager.remove(this.entityManager.merge(toCollect));
    }
}
