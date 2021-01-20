package com.resow.wiapi.application.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class TemperaturesByCitynameLast30DaysDTO extends DTOBase {

    private String city;
    private List<Temperature> temperatures;

    public TemperaturesByCitynameLast30DaysDTO() {
        this.temperatures = new ArrayList<>();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Temperature> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    public void addTemperature(LocalDateTime datetime, Integer temperature) {
        this.temperatures.add(new Temperature(datetime, temperature));
    }

    public static class Temperature {

        private LocalDateTime date;
        private Integer temperature;

        public Temperature() {
        }

        public Temperature(LocalDateTime date, Integer temperature) {
            this.date = date;
            this.temperature = temperature;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setLocalDateTime(LocalDateTime date) {
            this.date = date;
        }

        public void setDate(Date date) {

            LocalDateTime ofInstant = LocalDateTime.ofInstant(
                    date.toInstant(),
                    ZoneId.systemDefault());

            this.date = ofInstant;
        }

        public Integer getTemperature() {
            return temperature;
        }

        public void setTemperature(Integer temperature) {
            this.temperature = temperature;
        }

    }
}
