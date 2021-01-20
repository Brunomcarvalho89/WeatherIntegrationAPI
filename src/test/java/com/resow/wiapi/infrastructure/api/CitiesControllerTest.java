package com.resow.wiapi.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resow.wiapi.application.dto.TemperaturesByCitynameLast30DaysDTO;
import com.resow.wiapi.domain.repository.ICurrentWeatherRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectRepository;
import com.resow.wiapi.domain.repository.ILocationToCollectWoeidRepository;
import com.resow.wiapi.infrastructure.WeatherIntegrationAPIApplication;
import com.resow.wiapi.infrastructure.acl.viacep.gateway.ViaCepZipcodeGateway;
import com.resow.wiapi.resources.RepositoryResources;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author bruno
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WeatherIntegrationAPIApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CitiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${url.viacep}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private ViaCepZipcodeGateway viaCepZipcodeGateway;

    @Autowired
    private ILocationToCollectRepository locationToCollectRepository;

    @Autowired
    private ICurrentWeatherRepository currentWeatherRepository;

    @Autowired
    private ILocationToCollectWoeidRepository locationToCollectWoeidRepository;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @Order(1)
    public void testRegisterByCityname() {

        Assertions.assertDoesNotThrow(() -> {
            MvcResult andReturn = mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/cities/cityname/Petrópolis, RJ")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String contentAsString = andReturn.getResponse().getContentAsString();
            Assertions.assertTrue(contentAsString.length() > 0);

            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/cities/cityname/Petrópolis, RJ")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                    .andReturn();
        });
    }

    @Test
    @Order(2)
    public void testRegisterByWoeid() {
        Assertions.assertDoesNotThrow(() -> {
            MvcResult andReturn = mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/cities/woeid/1234?cityname=Cityname, RJ")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String contentAsString = andReturn.getResponse().getContentAsString();
            Assertions.assertTrue(contentAsString.length() > 0);

            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/cities/woeid/1234?cityname=Cityname, RJ")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                    .andReturn();
        });
    }

    @Test
    @Order(3)
    public void testRegisterByZipCode() {

        Assertions.assertDoesNotThrow(() -> {

            final String cep = "25555003";

            URI URI = new URI(String.format(this.url, cep));

            String returnBody = "{\"cep\": \"01001-000\",\"logradouro\": \"Praça da Sé\",\"complemento\": \"lado ímpar\",\"bairro\": \"Sé\",\"localidade\": \"São Paulo\",\"uf\": \"SP\",\"ibge\": \"3550308\",\"gia\": \"1004\",\"ddd\": \"11\",\"siafi\": \"7107\"}";

            mockServer.expect(ExpectedCount.manyTimes(), requestTo(URI))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators
                            .withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(returnBody));

            MvcResult andReturn = mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/cities/cep/" + cep)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String contentAsString = andReturn.getResponse().getContentAsString();
            Assertions.assertTrue(contentAsString.length() > 0);

            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/cities/cep/" + cep)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                    .andReturn();
        });
    }

    @Test
    @Order(4)
    public void testFindAll() {

        Assertions.assertDoesNotThrow(() -> {

            RepositoryResources repositoryResources = new RepositoryResources();
            repositoryResources.fillDatabase(this.locationToCollectRepository, this.currentWeatherRepository, this.locationToCollectWoeidRepository);

            MvcResult andReturn = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/cities/")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            HashMap<String, HashMap> readValue = new ObjectMapper().readValue(andReturn.getResponse().getContentAsString(), HashMap.class);
            HashMap<String, ArrayList> _embedded = readValue.get("_embedded");
            ArrayList locationToCollectByCitynameDTOList = _embedded.get("locationToCollectByCitynameDTOList");

            Assertions.assertTrue(locationToCollectByCitynameDTOList.size() > 0);
        });
    }

    @Test
    @Order(5)
    public void testFindAllTemperatures() {

        Assertions.assertDoesNotThrow(() -> {

            MvcResult andReturn = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/cities/temperatures?page=0&max-results=2")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            HashMap<String, HashMap> readValue = new ObjectMapper().readValue(andReturn.getResponse().getContentAsString(), HashMap.class);
            HashMap<String, ArrayList> _embedded = readValue.get("_embedded");
            ArrayList temperatures = _embedded.get("temperatureList");

            Assertions.assertTrue(temperatures.size() == 2);
        });
    }

    @Test
    @Order(6)
    public void testFindAllTemperaturesByCityname() {

        Assertions.assertDoesNotThrow(() -> {

            MvcResult andReturn = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/cities/cityname/cityname1/temperatures")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            TemperaturesByCitynameLast30DaysDTO temperaturesByCityname = new ObjectMapper().readValue(andReturn.getResponse().getContentAsString(), TemperaturesByCitynameLast30DaysDTO.class);
            Assertions.assertTrue(temperaturesByCityname.getTemperatures().size() == 2);
        });
    }

    @Test
    @Order(7)
    public void testFindAllTemperaturesByWoeid() {

        Assertions.assertDoesNotThrow(() -> {

            MvcResult andReturn = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/cities/woeid/5461/temperatures")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            TemperaturesByCitynameLast30DaysDTO temperaturesByCityname = new ObjectMapper().readValue(andReturn.getResponse().getContentAsString(), TemperaturesByCitynameLast30DaysDTO.class);
            Assertions.assertEquals("cityname4-woeid", temperaturesByCityname.getCity());
            Assertions.assertTrue(temperaturesByCityname.getTemperatures().size() == 2);
        });
    }

    @Test
    @Order(8)
    public void testDeregisterCityByCityname() {

        Assertions.assertDoesNotThrow(() -> {

            mockMvc.perform(
                    MockMvcRequestBuilders
                            .delete("/cities/cityname/Petrópolis, RJ")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        });
    }

    @Test
    @Order(9)
    public void testDeleteTemperaturesByCityname() {

        Assertions.assertDoesNotThrow(() -> {

            mockMvc.perform(
                    MockMvcRequestBuilders
                            .delete("/cities/cityname/cityname1/temperatures")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/cities/cityname/cityname1/temperatures")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andReturn();
        });
    }

    @Test
    @Order(10)
    public void testDeleteTemperaturesByWoeid() {

        Assertions.assertDoesNotThrow(() -> {

            mockMvc.perform(
                    MockMvcRequestBuilders
                            .delete("/cities/woeid/5461/temperatures")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/cities/woeid/5461/temperatures")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andReturn();
        });
    }

    @Test
    @Order(12)
    public void testDeregisterCityByWoeid() {

        Assertions.assertDoesNotThrow(() -> {

            mockMvc.perform(
                    MockMvcRequestBuilders
                            .delete("/cities/woeid/5461")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        });
    }
}
