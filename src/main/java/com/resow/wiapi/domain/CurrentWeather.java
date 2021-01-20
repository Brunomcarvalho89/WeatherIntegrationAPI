package com.resow.wiapi.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.function.Function;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Entity(name = "current_weather")
public class CurrentWeather implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temperature;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private LocationToCollect address;

    public CurrentWeather() {
    }

    public CurrentWeather(Integer temperature, LocalDateTime date, LocationToCollect address) {
        this.temperature = temperature;
        this.date = date;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocationToCollect getAddress() {
        return address;
    }

    public void setAddress(LocationToCollect address) {
        this.address = address;
    }

    public enum Time {
        LAST_30_HOURS((LocalDateTime arg0) -> arg0.minusHours(30));

        Function<LocalDateTime, LocalDateTime> callbackCalculateLocalDateTime;

        private Time(Function<LocalDateTime, LocalDateTime> callbackCalculateLocalDateTime) {
            this.callbackCalculateLocalDateTime = callbackCalculateLocalDateTime;
        }

        public LocalDateTime calculate(LocalDateTime localDateTime) {
            return this.callbackCalculateLocalDateTime.apply(localDateTime);
        }
    }
}