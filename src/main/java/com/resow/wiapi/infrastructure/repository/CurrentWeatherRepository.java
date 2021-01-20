package com.resow.wiapi.infrastructure.repository;

import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.repository.ICurrentWeatherRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Repository
public class CurrentWeatherRepository implements ICurrentWeatherRepository {

    private static final Logger LOG = Logger.getLogger(CurrentWeatherRepository.class.getName());

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Boolean save(CurrentWeather currentWeather) {
        try {

            LocationToCollect locationToCollectManaged = entityManager.merge(currentWeather.getAddress());

            currentWeather.setAddress(locationToCollectManaged);

            this.entityManager.persist(currentWeather);

            return Boolean.TRUE;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro: Não foi possível salvar os dados de CurrentWeather.");
            LOG.log(Level.SEVERE, "Erro: CurrentWeatherRepository => " + ex.getMessage());
            return Boolean.FALSE;
        }
    }

    @Override
    public List<CurrentWeather> findByTimeAndCityname(CurrentWeather.Time time, String cityname) {

        try {
            TypedQuery<CurrentWeather> query = this.entityManager
                    .createQuery(
                            "select c from current_weather c where c.date >= :time and c.address.cityname=:cityname",
                            CurrentWeather.class);

            query.setParameter("time", time.calculate(LocalDateTime.now()));
            query.setParameter("cityname", cityname);

            return query.getResultList();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro: CurrentWeatherRepository => " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<CurrentWeather> findByTimeAndWoeid(CurrentWeather.Time time, String woeid) {

        try {
            TypedQuery<CurrentWeather> query = this.entityManager
                    .createQuery(
                            "select c from current_weather c,location_to_collect_by_woeid lw where c.address = lw and c.date >= :time and lw.woeid =:woeid",
                            CurrentWeather.class);

            query.setParameter("time", time.calculate(LocalDateTime.now()));
            query.setParameter("woeid", woeid);

            return query.getResultList();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro: CurrentWeatherRepository => " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void removeByCityname(String cityname) {
        try {

            Query query = this.entityManager
                    .createQuery("delete from current_weather c where c.address.id in  (select l.id from location_to_collect l where l.cityname = :cityname)");

            query.setParameter("cityname", cityname);

            query.executeUpdate();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro: CurrentWeatherRepository => " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void removeByWoeid(String woeid) {
        try {
            Query query = this.entityManager
                    .createQuery("delete from current_weather c "
                            + "where "
                            + "c.address.id =  (select lw from location_to_collect_by_woeid lw where c.address = lw and lw.woeid =:woeid)");

            query.setParameter("woeid", woeid);

            query.executeUpdate();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro: CurrentWeatherRepository => " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public List<CurrentWeather> findAllTheLatestPageable(int page, int maxResults) {

        try {
            TypedQuery<CurrentWeather> query = this.entityManager
                    .createQuery(
                            "select c from current_weather c "
                            + "where "
                            + "c.id = (select max(c3.id) from current_weather c3 where c3.address = c.address) "
                            + "and "
                            + "c.date = (select max(c2.date) from current_weather c2 where c2.address = c.address and c.id = c2.id) "
                            + "order by c.address desc",
                            CurrentWeather.class);

            query.setFirstResult(page * maxResults);
            query.setMaxResults(maxResults);

            return query.getResultList();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro: CurrentWeatherRepository => " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void updateAddress(LocationToCollect locationToCollect, LocationToCollect locationToCollectOld) {

        try {
            Query query = this.entityManager
                    .createQuery(
                            "update current_weather c set c.address=:locationToCollect where c.address=:locationToCollectOld");

            query.setParameter("locationToCollect", locationToCollect);
            query.setParameter("locationToCollectOld", locationToCollectOld);

            query.executeUpdate();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro: CurrentWeatherRepository => " + ex.getMessage());
            ex.printStackTrace();
        }

    }
}
