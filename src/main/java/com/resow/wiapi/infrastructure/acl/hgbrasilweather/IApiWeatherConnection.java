package com.resow.wiapi.infrastructure.acl.hgbrasilweather;

import com.resow.wiapi.infrastructure.acl.Request;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.exceptions.MethodApiWeatherConnectionException;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.gateway.CurrentWeatherGateway;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.CurrentWeatherByCityNameRequest;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.CurrentWeatherByWOEIDRequest;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.response.CurrentWeatherResponse;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Component
public interface IApiWeatherConnection {

    default Optional<CurrentWeatherResponse> currentWeather(Request request) {

        try {
            Stream<Method> filter = Stream
                    .of(this.getClass().getMethods())
                    .filter((Method arg0) -> {
                        Class<?>[] params = arg0.getParameterTypes();
                        Class<?> returnType = arg0.getReturnType();

                        return params.length == 1
                                && params[0].equals(request.getClass())
                                && returnType.equals(Optional.class);
                    });

            Method equivalentMethodForParameter = filter
                    .findFirst()
                    .orElseThrow(() -> new MethodApiWeatherConnectionException("Method not found for type parameter."));

            return (Optional<CurrentWeatherResponse>) equivalentMethodForParameter.invoke(this, request);

        } catch (Exception ex) {
            Logger.getLogger(CurrentWeatherGateway.class.getName()).log(Level.SEVERE, ex.getMessage());
            return Optional.empty();
        }
    }

    Optional<CurrentWeatherResponse> currentWeather(CurrentWeatherByWOEIDRequest request);

    Optional<CurrentWeatherResponse> currentWeather(CurrentWeatherByCityNameRequest request);

}
