package com.resow.wiapi.infrastructure.acl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.response.CurrentWeatherFromResponseFactory;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.response.CurrentWeatherResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class CurrentWeatherFromResponseFactoryTest {

    @Test
    public void currentWeather() throws JsonProcessingException {

        final Long idLocationToCollect = 2l;
        final String cityname = "City-Test";

        LocationToCollect locationToCollect = new LocationToCollect();
        locationToCollect.setId(idLocationToCollect);
        locationToCollect.setCityname(cityname);

        CurrentWeatherResponse currentWeatherResponse = new ObjectMapper()
                .readValue(
                        "{\"by\":\"city_name\",\"valid_key\":true,\"results\":{\"temp\":24,\"date\":\"12/01/2021\",\"time\":\"21:29\",\"condition_code\":\"26\",\"description\":\"Tempo nublado\",\"currently\":\"noite\",\"cid\":\"\",\"city\":\"Campinas, SP\",\"img_id\":\"26n\",\"humidity\":87,\"wind_speedy\":\"4 km/h\",\"sunrise\":\"5:34 am\",\"sunset\":\"6:59 pm\",\"condition_slug\":\"cloud\",\"city_name\":\"Campinas\",\"forecast\":[{\"date\":\"12/01\",\"weekday\":\"Ter\",\"max\":28,\"min\":21,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"13/01\",\"weekday\":\"Qua\",\"max\":29,\"min\":21,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"14/01\",\"weekday\":\"Qui\",\"max\":30,\"min\":21,\"description\":\"Tempestades isoladas\",\"condition\":\"storm\"},{\"date\":\"15/01\",\"weekday\":\"Sex\",\"max\":28,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"16/01\",\"weekday\":\"Sáb\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"17/01\",\"weekday\":\"Dom\",\"max\":28,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"18/01\",\"weekday\":\"Seg\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"19/01\",\"weekday\":\"Ter\",\"max\":30,\"min\":20,\"description\":\"Tempo nublado\",\"condition\":\"cloud\"},{\"date\":\"20/01\",\"weekday\":\"Qua\",\"max\":29,\"min\":20,\"description\":\"Parcialmente nublado\",\"condition\":\"cloudly_day\"},{\"date\":\"21/01\",\"weekday\":\"Qui\",\"max\":30,\"min\":22,\"description\":\"Parcialmente nublado\",\"condition\":\"cloudly_day\"}]},\"execution_time\":0.0,\"from_cache\":true}",
                        CurrentWeatherResponse.class);

        LocalDateTime localDateTime = LocalDateTime.parse("12/01/2021-21:29", DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm"));

        CurrentWeatherFromResponseFactory currentWeatherFromResponseFactory = new CurrentWeatherFromResponseFactory();
        CurrentWeather currentWeather = currentWeatherFromResponseFactory.currentWeather(currentWeatherResponse, locationToCollect);

        Assertions.assertEquals(locationToCollect, currentWeather.getAddress());
        Assertions.assertEquals(24, currentWeather.getTemperature());
        Assertions.assertEquals(localDateTime, currentWeather.getDate());
    }

}
