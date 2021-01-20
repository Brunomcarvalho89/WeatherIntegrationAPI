package com.resow.wiapi.infrastructure.acl.hgbrasilweather.gateway;

import com.resow.wiapi.application.ICurrentWeatherService;
import com.resow.wiapi.domain.CurrentWeather;
import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.infrastructure.acl.Request;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.IApiWeatherConnection;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.response.ICurrentWeatherFromResponseFactory;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.request.RequestFactory;
import com.resow.wiapi.infrastructure.acl.hgbrasilweather.response.CurrentWeatherResponse;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
@Service
public class CurrentWeatherGateway implements ICurrentWeatherService {

    private static final Logger LOG = Logger.getLogger(CurrentWeatherGateway.class.getName());

    private final ICurrentWeatherFromResponseFactory currentWeatherFromResponseFactory;
    private final IApiWeatherConnection apiWeatherConnection;

    @Value("${url.hgbrasil}")
    private String url;

    @Autowired
    public CurrentWeatherGateway(
            ICurrentWeatherFromResponseFactory currentWeatherFromResponseFactory,
            IApiWeatherConnection apiWeatherConnection) {

        this.currentWeatherFromResponseFactory = currentWeatherFromResponseFactory;
        this.apiWeatherConnection = apiWeatherConnection;
    }

    @Override
    public Optional<CurrentWeather> find(LocationToCollect locationToSelect) {

        Request get = new RequestFactory().get(locationToSelect);

        Optional<CurrentWeatherResponse> currentWeather = this.apiWeatherConnection.currentWeather(get);

        if (currentWeather.isEmpty()) {
            return Optional.empty();
        }

        CurrentWeatherResponse currentWeatherResponse = currentWeather.get();

        return Optional.of(this.currentWeatherFromResponseFactory.currentWeather(currentWeatherResponse, locationToSelect));
    }

}
