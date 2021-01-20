package com.resow.wiapi.infrastructure.acl;

import com.resow.wiapi.application.ICurrentWeatherService;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.domain.LocationToCollectWoeid;
import com.resow.wiapi.infrastructure.WeatherIntegrationAPIApplication;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WeatherIntegrationAPIApplication.class)
@AutoConfigureMockMvc
public class CurrentWeatherGatewayTest {

    @Autowired
    private ICurrentWeatherService currentWeatherService;

    @Value("${url.hgbrasil}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Value("${key.hgbrasil}")
    private String key;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testFindByCityname() {

        Assertions.assertDoesNotThrow(() -> {

            final String cityname = "petropolis, rj";

            String uriString = UriComponentsBuilder
                    .fromUriString(this.url)
                    .encode(Charset.forName("utf-8"))
                    .pathSegment("weather")
                    .queryParam("key", this.key)
                    .queryParam("city_name", URLEncoder.encode(cityname, StandardCharsets.UTF_8.toString()))
                    .toUriString();

            mockServer.expect(ExpectedCount.manyTimes(),
                    requestTo(new URI(uriString)))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators
                            .withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{\"by\":\"city_name\",\"valid_key\":true,\"results\":{\"temp\":24,\"date\":\"12/01/2021\",\"time\":\"21:29\",\"condition_code\":\"26\",\"description\":\"Tempo nublado\",\"currently\":\"noite\",\"cid\":\"\",\"city\":\"Campinas, SP\",\"img_id\":\"26n\",\"humidity\":87,\"wind_speedy\":\"4 km/h\",\"sunrise\":\"5:34 am\",\"sunset\":\"6:59 pm\",\"condition_slug\":\"cloud\",\"city_name\":\"Campinas\",\"forecast\":[{\"date\":\"12/01\",\"weekday\":\"Ter\",\"max\":28,\"min\":21,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"13/01\",\"weekday\":\"Qua\",\"max\":29,\"min\":21,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"14/01\",\"weekday\":\"Qui\",\"max\":30,\"min\":21,\"description\":\"Tempestades isoladas\",\"condition\":\"storm\"},{\"date\":\"15/01\",\"weekday\":\"Sex\",\"max\":28,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"16/01\",\"weekday\":\"Sáb\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"17/01\",\"weekday\":\"Dom\",\"max\":28,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"18/01\",\"weekday\":\"Seg\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"19/01\",\"weekday\":\"Ter\",\"max\":30,\"min\":20,\"description\":\"Tempo nublado\",\"condition\":\"cloud\"},{\"date\":\"20/01\",\"weekday\":\"Qua\",\"max\":29,\"min\":20,\"description\":\"Parcialmente nublado\",\"condition\":\"cloudly_day\"},{\"date\":\"21/01\",\"weekday\":\"Qui\",\"max\":30,\"min\":22,\"description\":\"Parcialmente nublado\",\"condition\":\"cloudly_day\"}]},\"execution_time\":0.0,\"from_cache\":true}"));

            LocationToCollect locationToCollect = new LocationToCollect(1l, cityname);

            CurrentWeather currentWeather = this.currentWeatherService.find(locationToCollect).orElse(null);
            Assertions.assertTrue(Objects.nonNull(currentWeather));
            Assertions.assertEquals(24, currentWeather.getTemperature());
        });

    }

    @Test
    public void testFindByWoeid() {

        Assertions.assertDoesNotThrow(() -> {

            final String cityname = "petropolis, rj";
            final String woeid = "4321";

            String uriString = UriComponentsBuilder
                    .fromUriString(this.url)
                    .encode(Charset.forName("utf-8"))
                    .pathSegment("weather")
                    .queryParam("key", this.key)
                    .queryParam("woeid", URLEncoder.encode(woeid, StandardCharsets.UTF_8.toString()))
                    .toUriString();

            mockServer.expect(ExpectedCount.manyTimes(),
                    requestTo(new URI(uriString)))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators
                            .withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{\"by\":\"woeid\",\"valid_key\":false,\"results\":{\"temp\":25,\"date\":\"13/01/2021\",\"time\":\"10:51\",\"condition_code\":\"28\",\"description\":\"Tempo nublado\",\"currently\":\"dia\",\"cid\":\"\",\"city\":\"São Paulo, SP\",\"img_id\":\"28\",\"humidity\":73,\"wind_speedy\":\"2.06 km/h\",\"sunrise\":\"05:31 am\",\"sunset\":\"06:58 pm\",\"condition_slug\":\"cloudly_day\",\"city_name\":\"São Paulo\",\"forecast\":[{\"date\":\"13/01\",\"weekday\":\"Qua\",\"max\":26,\"min\":21,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"14/01\",\"weekday\":\"Qui\",\"max\":25,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"15/01\",\"weekday\":\"Sex\",\"max\":26,\"min\":18,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"16/01\",\"weekday\":\"Sáb\",\"max\":27,\"min\":19,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"17/01\",\"weekday\":\"Dom\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"18/01\",\"weekday\":\"Seg\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"19/01\",\"weekday\":\"Ter\",\"max\":28,\"min\":20,\"description\":\"Tempestades isoladas\",\"condition\":\"storm\"},{\"date\":\"20/01\",\"weekday\":\"Qua\",\"max\":28,\"min\":21,\"description\":\"Tempo nublado\",\"condition\":\"cloud\"},{\"date\":\"21/01\",\"weekday\":\"Qui\",\"max\":23,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"22/01\",\"weekday\":\"Sex\",\"max\":23,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"}]},\"execution_time\":0.0,\"from_cache\":true}"));

            LocationToCollect locationToCollect = new LocationToCollect(1l, cityname);
            LocationToCollectWoeid locationToCollectWoeid = new LocationToCollectWoeid(locationToCollect, woeid);

            CurrentWeather currentWeather = this.currentWeatherService.find(locationToCollectWoeid).orElse(null);
            Assertions.assertTrue(Objects.nonNull(currentWeather));
            Assertions.assertEquals(25, currentWeather.getTemperature());
        });

    }

    @Test
    public void testFindByCitynameEmpty() {

        Assertions.assertDoesNotThrow(() -> {

            final String cityname = "petropolis, rj";

            String uriString = UriComponentsBuilder
                    .fromUriString(this.url)
                    .encode(Charset.forName("utf-8"))
                    .pathSegment("weather")
                    .queryParam("key", this.key)
                    .queryParam("saf", URLEncoder.encode(cityname, StandardCharsets.UTF_8.toString()))
                    .toUriString();

            mockServer.expect(ExpectedCount.manyTimes(),
                    requestTo(new URI(uriString)))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators
                            .withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{\"by\":\"city_name\",\"valid_key\":true,\"results\":{\"temp\":24,\"date\":\"12/01/2021\",\"time\":\"21:29\",\"condition_code\":\"26\",\"description\":\"Tempo nublado\",\"currently\":\"noite\",\"cid\":\"\",\"city\":\"Campinas, SP\",\"img_id\":\"26n\",\"humidity\":87,\"wind_speedy\":\"4 km/h\",\"sunrise\":\"5:34 am\",\"sunset\":\"6:59 pm\",\"condition_slug\":\"cloud\",\"city_name\":\"Campinas\",\"forecast\":[{\"date\":\"12/01\",\"weekday\":\"Ter\",\"max\":28,\"min\":21,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"13/01\",\"weekday\":\"Qua\",\"max\":29,\"min\":21,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"14/01\",\"weekday\":\"Qui\",\"max\":30,\"min\":21,\"description\":\"Tempestades isoladas\",\"condition\":\"storm\"},{\"date\":\"15/01\",\"weekday\":\"Sex\",\"max\":28,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"16/01\",\"weekday\":\"Sáb\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"17/01\",\"weekday\":\"Dom\",\"max\":28,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"18/01\",\"weekday\":\"Seg\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"19/01\",\"weekday\":\"Ter\",\"max\":30,\"min\":20,\"description\":\"Tempo nublado\",\"condition\":\"cloud\"},{\"date\":\"20/01\",\"weekday\":\"Qua\",\"max\":29,\"min\":20,\"description\":\"Parcialmente nublado\",\"condition\":\"cloudly_day\"},{\"date\":\"21/01\",\"weekday\":\"Qui\",\"max\":30,\"min\":22,\"description\":\"Parcialmente nublado\",\"condition\":\"cloudly_day\"}]},\"execution_time\":0.0,\"from_cache\":true}"));

            LocationToCollect locationToCollect = new LocationToCollect(1l, cityname);

            CurrentWeather currentWeather = this.currentWeatherService.find(locationToCollect).orElse(null);
            Assertions.assertTrue(Objects.isNull(currentWeather));
        });

    }

    @Test
    public void testFindByWoeidEmpty() {

        Assertions.assertDoesNotThrow(() -> {

            final String cityname = "petropolis, rj";
            final String woeid = "4321";

            String uriString = UriComponentsBuilder
                    .fromUriString(this.url)
                    .encode(Charset.forName("utf-8"))
                    .pathSegment("weather")
                    .queryParam("key", this.key)
                    .queryParam("sadf", URLEncoder.encode(woeid, StandardCharsets.UTF_8.toString()))
                    .toUriString();

            mockServer.expect(ExpectedCount.manyTimes(),
                    requestTo(new URI(uriString)))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators
                            .withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{\"by\":\"woeid\",\"valid_key\":false,\"results\":{\"temp\":25,\"date\":\"13/01/2021\",\"time\":\"10:51\",\"condition_code\":\"28\",\"description\":\"Tempo nublado\",\"currently\":\"dia\",\"cid\":\"\",\"city\":\"São Paulo, SP\",\"img_id\":\"28\",\"humidity\":73,\"wind_speedy\":\"2.06 km/h\",\"sunrise\":\"05:31 am\",\"sunset\":\"06:58 pm\",\"condition_slug\":\"cloudly_day\",\"city_name\":\"São Paulo\",\"forecast\":[{\"date\":\"13/01\",\"weekday\":\"Qua\",\"max\":26,\"min\":21,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"14/01\",\"weekday\":\"Qui\",\"max\":25,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"15/01\",\"weekday\":\"Sex\",\"max\":26,\"min\":18,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"16/01\",\"weekday\":\"Sáb\",\"max\":27,\"min\":19,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"17/01\",\"weekday\":\"Dom\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"18/01\",\"weekday\":\"Seg\",\"max\":27,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"19/01\",\"weekday\":\"Ter\",\"max\":28,\"min\":20,\"description\":\"Tempestades isoladas\",\"condition\":\"storm\"},{\"date\":\"20/01\",\"weekday\":\"Qua\",\"max\":28,\"min\":21,\"description\":\"Tempo nublado\",\"condition\":\"cloud\"},{\"date\":\"21/01\",\"weekday\":\"Qui\",\"max\":23,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"},{\"date\":\"22/01\",\"weekday\":\"Sex\",\"max\":23,\"min\":20,\"description\":\"Tempestades\",\"condition\":\"storm\"}]},\"execution_time\":0.0,\"from_cache\":true}"));

            LocationToCollect locationToCollect = new LocationToCollect(1l, cityname);
            LocationToCollectWoeid locationToCollectWoeid = new LocationToCollectWoeid(locationToCollect, woeid);

            CurrentWeather currentWeather = this.currentWeatherService.find(locationToCollectWoeid).orElse(null);
            Assertions.assertTrue(Objects.isNull(currentWeather));
        });

    }
}
