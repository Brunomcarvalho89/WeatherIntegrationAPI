package com.resow.wiapi.infrastructure.acl;

import com.resow.wiapi.infrastructure.acl.hgbrasilweather.response.CurrentWeatherResponse;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class CurrentWeatherResponseTest {

    @Test
    public void testGettersAndSetters() {

        String by = "by-1";
        Boolean validKey = Boolean.TRUE;
        Long executionTime = 3471932174993214l;
        Boolean fromCache = Boolean.FALSE;

        Integer temp = 1;
        String date = "21/02/2008";
        String time = "12:52";
        String conditionCode = "asdf";
        String description = "lfjasdlfjasjdf";
        String currently = "sdafsd";
        String cid = "cid";
        String city = "Petrópolis, RJ";
        String imgId = "23456";
        Long humidity = 13212312l;
        String windSpeedy = "windS";
        String sunrise = "ccc";
        String sunset = "sunset";
        String conditionSlug = "conditionSlug";
        String cityName = "Petropolis";
        Double latitude = 12.2;
        Double longitude = 43.1;

        String dateF = "25/3/1999";
        String weekday = "sunday";
        Integer max = 10;
        Integer min = 5;
        String descriptionF = "description forecast";
        String conditionF = "condition test";

        CurrentWeatherResponse.Forecast forecast = new CurrentWeatherResponse.Forecast();
        forecast.setDate(dateF);
        forecast.setWeekday(weekday);
        forecast.setMax(max);
        forecast.setMin(min);
        forecast.setDescription(descriptionF);
        forecast.setCondition(conditionF);

        List<CurrentWeatherResponse.Forecast> forecastList = List.of(forecast);

        CurrentWeatherResponse.Result result = new CurrentWeatherResponse.Result();
        result.setTemp(temp);
        result.setDate(date);
        result.setTime(time);
        result.setConditionCode(conditionCode);
        result.setDescription(description);
        result.setCurrently(currently);
        result.setCid(cid);
        result.setCity(city);
        result.setImgId(imgId);
        result.setHumidity(humidity);
        result.setWindSpeedy(windSpeedy);
        result.setSunrise(sunrise);
        result.setSunset(sunset);
        result.setConditionSlug(conditionSlug);
        result.setCityName(cityName);
        result.setForecast(forecastList);
        result.setLatitude(latitude);
        result.setLongitude(longitude);

        CurrentWeatherResponse currentWeatherResponse = new CurrentWeatherResponse();
        currentWeatherResponse.setBy(by);
        currentWeatherResponse.setValidKey(validKey);
        currentWeatherResponse.setExecutionTime(executionTime);
        currentWeatherResponse.setFromCache(fromCache);
        currentWeatherResponse.setResults(result);

        Assertions.assertEquals(currentWeatherResponse.getBy(), by);
        Assertions.assertEquals(currentWeatherResponse.getValidKey(), validKey);
        Assertions.assertEquals(currentWeatherResponse.getExecutionTime(), executionTime);
        Assertions.assertEquals(currentWeatherResponse.getFromCache(), fromCache);
        Assertions.assertEquals(currentWeatherResponse.getResults(), result);
    }

}
