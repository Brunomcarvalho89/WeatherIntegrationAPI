package com.resow.wiapi.resources;

import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.LocationToCollectWoeid;
import com.resow.wiapi.domain.repository.ICurrentWeatherRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author brunomcarvalho89@gmail.co
 */
public class CurrentWeatherRepositoryMock implements ICurrentWeatherRepository {

    private List<CurrentWeather> currentWeatherList = new ArrayList<>();

    @Override
    public Boolean save(CurrentWeather currentWeather) {
        return this.currentWeatherList.add(currentWeather);
    }

    @Override
    public List<CurrentWeather> findAllTheLatestPageable(int page, int maxResults) {

        Set<Long> collect = this.currentWeatherList
                .stream()
                .map(item -> item.getAddress().getId())
                .collect(Collectors.toSet());

        List<CurrentWeather> distinctByIdAndMaxDate = collect
                .stream()
                .map(item
                        -> this.currentWeatherList
                        .stream()
                        .filter(itemCurrentWeather -> Objects.equals(itemCurrentWeather.getAddress().getId(), item))
                        .max((CurrentWeather arg0, CurrentWeather arg1) -> arg0.getDate().isAfter(arg1.getDate()) ? 1 : -1)
                        .orElse(null))
                .filter(item -> item != null)
                .collect(Collectors.toList());

        return this.getPage(distinctByIdAndMaxDate, page, maxResults);
    }

    @Override
    public List<CurrentWeather> findByTimeAndCityname(CurrentWeather.Time time, String cityname) {

        LocalDateTime localDateTime = LocalDateTime.now();

        LocalDateTime calculate = time.calculate(localDateTime);

        return this.currentWeatherList
                .stream()
                .filter(item -> item.getAddress().getCityname().equals(cityname))
                .filter(item -> item.getDate().isAfter(calculate))
                .collect(Collectors.toList());
    }

    @Override

    public List<CurrentWeather> findByTimeAndWoeid(CurrentWeather.Time time, String woeid) {

        LocalDateTime localDateTime = LocalDateTime.now();

        LocalDateTime calculate = time.calculate(localDateTime);

        return this.currentWeatherList
                .stream()
                .filter(item -> {
                    if (item.getAddress() instanceof LocationToCollectWoeid) {
                        return ((LocationToCollectWoeid) item.getAddress()).getWoeid().equals(woeid);
                    }
                    return Boolean.FALSE;
                })
                .filter(item -> item.getDate().isAfter(calculate))
                .collect(Collectors.toList());
    }

    @Override
    public void removeByCityname(String cityname) {
        this.currentWeatherList
                .removeIf(item -> item.getAddress().getCityname().equalsIgnoreCase(cityname));
    }

    @Override
    public void removeByWoeid(String woeid) {
        this.currentWeatherList
                .removeIf(item -> {
                    if (item.getAddress() instanceof LocationToCollectWoeid) {
                        return ((LocationToCollectWoeid) item.getAddress()).getWoeid().equals(woeid);
                    }
                    return Boolean.FALSE;
                });
    }

    @Override
    public void updateAddress(LocationToCollect locationToCollect, LocationToCollect locationToCollectOld) {

        this.currentWeatherList
                .forEach((CurrentWeather arg0) -> {
                    if (arg0.getAddress().getId().equals(locationToCollectOld.getId())) {
                        arg0.setAddress(locationToCollect);
                    }
                });

    }

    public <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if (pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if (sourceList == null || sourceList.size() <= fromIndex) {
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }
}
