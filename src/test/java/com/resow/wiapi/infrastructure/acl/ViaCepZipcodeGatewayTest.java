package com.resow.wiapi.infrastructure.acl;

import com.resow.wiapi.application.ICurrentWeatherService;
import com.resow.wiapi.infrastructure.WeatherIntegrationAPIApplication;
import com.resow.wiapi.infrastructure.acl.viacep.gateway.ViaCepZipcodeGateway;
import com.resow.wiapi.infrastructure.acl.viacep.gateway.ZipcodeConnection;
import java.net.URI;
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

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WeatherIntegrationAPIApplication.class)
@AutoConfigureMockMvc
public class ViaCepZipcodeGatewayTest {

    @Value("${url.viacep}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private ViaCepZipcodeGateway viaCepZipcodeGateway;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testFindByCEP() {

        Assertions.assertDoesNotThrow(() -> {

            final String cep = "25564333";

            URI URI = new URI(String.format(this.url, cep));

            String returnBody = "{\"cep\": \"01001-000\",\"logradouro\": \"Praça da Sé\",\"complemento\": \"lado ímpar\",\"bairro\": \"Sé\",\"localidade\": \"São Paulo\",\"uf\": \"SP\",\"ibge\": \"3550308\",\"gia\": \"1004\",\"ddd\": \"11\",\"siafi\": \"7107\"}";

            mockServer.expect(ExpectedCount.manyTimes(), requestTo(URI))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators
                            .withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(returnBody));

            String address = this.viaCepZipcodeGateway.addressByZipCode(cep).orElse(null);
            Assertions.assertTrue(Objects.nonNull(address));
            Assertions.assertEquals("São Paulo, SP", address);
        });
    }

    @Test
    public void testFindByCEPBadRequest() {

        Assertions.assertDoesNotThrow(() -> {

            final String cep = "25564333";
            
            URI URI = new URI(String.format(this.url, cep));

            String returnBody = "{\"cep\": \"01001-000\",\"logradouro\": \"Praça da Sé\",\"complemento\": \"lado ímpar\",\"bairro\": \"Sé\",\"localidade\": \"São Paulo\",\"uf\": \"SP\",\"ibge\": \"3550308\",\"gia\": \"1004\",\"ddd\": \"11\",\"siafi\": \"7107\"}";

            mockServer.expect(ExpectedCount.manyTimes(), requestTo(URI))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators
                            .withStatus(HttpStatus.BAD_REQUEST)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(returnBody));

            String address = this.viaCepZipcodeGateway.addressByZipCode(cep).orElse(null);
            Assertions.assertTrue(Objects.isNull(address));
        });
    }
    
    @Test
    public void testFindByCEPStringErro() {

        Assertions.assertDoesNotThrow(() -> {

            final String cep = "25564333";
            
            URI URI = new URI(String.format(this.url, cep));

            String returnBody = "erro";

            mockServer.expect(ExpectedCount.manyTimes(), requestTo(URI))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators
                            .withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(returnBody));

            String address = this.viaCepZipcodeGateway.addressByZipCode(cep).orElse(null);
            Assertions.assertTrue(Objects.isNull(address));
        });
    }
}
