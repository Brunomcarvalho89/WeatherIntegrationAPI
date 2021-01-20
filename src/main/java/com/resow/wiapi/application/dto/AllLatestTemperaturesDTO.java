package com.resow.wiapi.application.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class AllLatestTemperaturesDTO extends DTOBase {

    private List<Temperature> temperatures;

    public AllLatestTemperaturesDTO() {
        this.temperatures = new ArrayList<>();
    }

    public List<Temperature> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    public void addTemperature(String city, LocalDateTime datetime, Integer temperature) {
        this.temperatures.add(new Temperature(city, datetime, temperature));
    }

    public static class Temperature {

        private String city;
        private LocalDateTime date;
        private Integer temperature;

        public Temperature(String city, LocalDateTime date, Integer temperature) {
            this.city = city;
            this.date = date;
            this.temperature = temperature;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public Integer getTemperature() {
            return temperature;
        }

        public void setTemperature(Integer temperature) {
            this.temperature = temperature;
        }

    }
}
