package com.resow.wiapi.infrastructure.acl.hgbrasilweather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resow.wiapi.infrastructure.acl.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class CurrentWeatherResponse extends Response {

    @JsonProperty("by")
    private String by;

    @JsonProperty("valid_key")
    private Boolean validKey;

    @JsonProperty("execution_time")
    private Long executionTime;

    @JsonProperty("from_cache")
    private Boolean fromCache;

    @JsonProperty("results")
    private Result results;

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Boolean getValidKey() {
        return validKey;
    }

    public void setValidKey(Boolean validKey) {
        this.validKey = validKey;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public Boolean getFromCache() {
        return fromCache;
    }

    public void setFromCache(Boolean fromCache) {
        this.fromCache = fromCache;
    }

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.by);
        hash = 67 * hash + Objects.hashCode(this.validKey);
        hash = 67 * hash + Objects.hashCode(this.executionTime);
        hash = 67 * hash + Objects.hashCode(this.fromCache);
        hash = 67 * hash + Objects.hashCode(this.results);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CurrentWeatherResponse other = (CurrentWeatherResponse) obj;
        if (!Objects.equals(this.by, other.by)) {
            return false;
        }
        if (!Objects.equals(this.validKey, other.validKey)) {
            return false;
        }
        if (!Objects.equals(this.executionTime, other.executionTime)) {
            return false;
        }
        if (!Objects.equals(this.fromCache, other.fromCache)) {
            return false;
        }
        if (!Objects.equals(this.results, other.results)) {
            return false;
        }
        return true;
    }

    public static class Result {

        @JsonProperty("temp")
        private Integer temp;

        //"02/12"
        @JsonProperty("date")
        private String date;

        // "01:36"
        @JsonProperty("time")
        private String time;

        @JsonProperty("condition_code")
        private String conditionCode;

        @JsonProperty("description")
        private String description;

        @JsonProperty("currently")
        private String currently;

        @JsonProperty("cid")
        private String cid;

        @JsonProperty("city")
        private String city;

        @JsonProperty("img_id")
        private String imgId;

        @JsonProperty("humidity")
        private Long humidity;

        @JsonProperty("wind_speedy")
        private String windSpeedy;

        @JsonProperty("sunrise")
        private String sunrise;

        @JsonProperty("sunset")
        private String sunset;

        @JsonProperty("condition_slug")
        private String conditionSlug;

        @JsonProperty("city_name")
        private String cityName;

        @JsonProperty("forecast")
        private List<Forecast> forecast;

        @JsonProperty("latitude")
        private Double latitude;

        @JsonProperty("longitude")
        private Double longitude;

        public Integer getTemp() {
            return temp;
        }

        public void setTemp(Integer temp) {
            this.temp = temp;
        }

        public LocalDateTime getDate() {
            LocalDateTime localDateTime = LocalDateTime.parse(this.date + "-" + this.time, DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm"));
            localDateTime.withYear(LocalDate.now().getYear());
            return localDateTime;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getConditionCode() {
            return conditionCode;
        }

        public void setConditionCode(String conditionCode) {
            this.conditionCode = conditionCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCurrently() {
            return currently;
        }

        public void setCurrently(String currently) {
            this.currently = currently;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public Long getHumidity() {
            return humidity;
        }

        public void setHumidity(Long humidity) {
            this.humidity = humidity;
        }

        public String getWindSpeedy() {
            return windSpeedy;
        }

        public void setWindSpeedy(String windSpeedy) {
            this.windSpeedy = windSpeedy;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getConditionSlug() {
            return conditionSlug;
        }

        public void setConditionSlug(String conditionSlug) {
            this.conditionSlug = conditionSlug;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public List<Forecast> getForecast() {
            return forecast;
        }

        public void setForecast(List<Forecast> forecast) {
            this.forecast = forecast;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 11 * hash + Objects.hashCode(this.temp);
            hash = 11 * hash + Objects.hashCode(this.date);
            hash = 11 * hash + Objects.hashCode(this.time);
            hash = 11 * hash + Objects.hashCode(this.conditionCode);
            hash = 11 * hash + Objects.hashCode(this.description);
            hash = 11 * hash + Objects.hashCode(this.currently);
            hash = 11 * hash + Objects.hashCode(this.cid);
            hash = 11 * hash + Objects.hashCode(this.city);
            hash = 11 * hash + Objects.hashCode(this.imgId);
            hash = 11 * hash + Objects.hashCode(this.humidity);
            hash = 11 * hash + Objects.hashCode(this.windSpeedy);
            hash = 11 * hash + Objects.hashCode(this.sunrise);
            hash = 11 * hash + Objects.hashCode(this.sunset);
            hash = 11 * hash + Objects.hashCode(this.conditionSlug);
            hash = 11 * hash + Objects.hashCode(this.cityName);
            hash = 11 * hash + Objects.hashCode(this.forecast);
            hash = 11 * hash + Objects.hashCode(this.latitude);
            hash = 11 * hash + Objects.hashCode(this.longitude);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Result other = (Result) obj;
            if (!Objects.equals(this.date, other.date)) {
                return false;
            }
            if (!Objects.equals(this.time, other.time)) {
                return false;
            }
            if (!Objects.equals(this.conditionCode, other.conditionCode)) {
                return false;
            }
            if (!Objects.equals(this.description, other.description)) {
                return false;
            }
            if (!Objects.equals(this.currently, other.currently)) {
                return false;
            }
            if (!Objects.equals(this.cid, other.cid)) {
                return false;
            }
            if (!Objects.equals(this.city, other.city)) {
                return false;
            }
            if (!Objects.equals(this.imgId, other.imgId)) {
                return false;
            }
            if (!Objects.equals(this.windSpeedy, other.windSpeedy)) {
                return false;
            }
            if (!Objects.equals(this.sunrise, other.sunrise)) {
                return false;
            }
            if (!Objects.equals(this.sunset, other.sunset)) {
                return false;
            }
            if (!Objects.equals(this.conditionSlug, other.conditionSlug)) {
                return false;
            }
            if (!Objects.equals(this.cityName, other.cityName)) {
                return false;
            }
            if (!Objects.equals(this.temp, other.temp)) {
                return false;
            }
            if (!Objects.equals(this.humidity, other.humidity)) {
                return false;
            }
            if (!Objects.equals(this.forecast, other.forecast)) {
                return false;
            }
            if (!Objects.equals(this.latitude, other.latitude)) {
                return false;
            }
            if (!Objects.equals(this.longitude, other.longitude)) {
                return false;
            }
            return true;
        }

    }

    public static class Forecast {

        //"02/12"
        @JsonProperty("date")
        private String date;

        @JsonProperty("weekday")
        private String weekday;

        @JsonProperty("max")
        private Integer max;

        @JsonProperty("min")
        private Integer min;

        @JsonProperty("description")
        private String description;

        @JsonProperty("condition")
        private String condition;

        public LocalDate getDate() {
            LocalDate localDate = LocalDate.parse(this.date, DateTimeFormatter.ofPattern("dd/MM"));
            localDate.withYear(LocalDate.now().getYear());
            return localDate;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeekday() {
            return weekday;
        }

        public void setWeekday(String weekday) {
            this.weekday = weekday;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + Objects.hashCode(this.date);
            hash = 67 * hash + Objects.hashCode(this.weekday);
            hash = 67 * hash + Objects.hashCode(this.max);
            hash = 67 * hash + Objects.hashCode(this.min);
            hash = 67 * hash + Objects.hashCode(this.description);
            hash = 67 * hash + Objects.hashCode(this.condition);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Forecast other = (Forecast) obj;
            if (!Objects.equals(this.date, other.date)) {
                return false;
            }
            if (!Objects.equals(this.weekday, other.weekday)) {
                return false;
            }
            if (!Objects.equals(this.description, other.description)) {
                return false;
            }
            if (!Objects.equals(this.condition, other.condition)) {
                return false;
            }
            if (!Objects.equals(this.max, other.max)) {
                return false;
            }
            if (!Objects.equals(this.min, other.min)) {
                return false;
            }
            return true;
        }

    }

}
