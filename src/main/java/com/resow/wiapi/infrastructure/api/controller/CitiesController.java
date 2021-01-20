package com.resow.wiapi.infrastructure.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PathVariable;
import com.resow.wiapi.application.dto.TemperaturesByCitynameLast30DaysDTO;
import com.resow.wiapi.application.dto.assembler.ITemperaturesByCityLast30DaysAssemblerDTO;
import com.resow.wiapi.domain.CurrentWeather;
import java.util.List;
import java.util.Optional;
import com.resow.wiapi.application.dto.assembler.ILocationToCollectAssemblerDTO;
import com.resow.wiapi.application.exceptions.CitynameMustBeInformedException;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.resow.wiapi.application.ITemperatureMonitoringService;
import com.resow.wiapi.application.dto.AllLatestTemperaturesDTO;
import com.resow.wiapi.application.dto.LocationToCollectByCitynameDTO;
import com.resow.wiapi.application.dto.assembler.IAllLatestTemperaturesAssemblerDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 *
 * @author brunomcarvalho89@gmail.com
 *
 * http://localhost:8080/swagger-ui/index.html#/
 */
@RestController
@RequestMapping("cities")
public class CitiesController {

    @Autowired
    private ITemperatureMonitoringService temperatureService;

    @Autowired
    private ILocationToCollectAssemblerDTO locationToCollectByCitynameAssemblerDTO;

    @Autowired
    private ITemperaturesByCityLast30DaysAssemblerDTO temperaturesByCityLast30DaysAssemblerDTO;

    @Autowired
    private IAllLatestTemperaturesAssemblerDTO allLatestTemperaturesAssemblerDTO;

    @GetMapping
    public ResponseEntity findAll() {

        List<EntityModel<LocationToCollectByCitynameDTO>> locations = this.temperatureService
                .findAllCitiesForCollect()
                .stream()
                .map(item -> {

                    EntityModel<LocationToCollectByCitynameDTO> entityModel = EntityModel.of(item);
                    entityModel.add(
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).findAllTemperaturesByCityname(item.getCityname()))
                                    .withRel(LinkRelation.of("temperatures"))
                                    .withTitle("Find all temperatures for the city.")
                    );

                    entityModel.add(
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deregisterCity(item.getCityname()))
                                    .withRel(LinkRelation.of("remove-city"))
                                    .withTitle("Remove the city from monitoring.")
                    );

                    entityModel.add(
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deleteTemperaturesByCityname(item.getCityname()))
                                    .withRel(LinkRelation.of("remove-temperatures-by-city"))
                                    .withTitle("Removes the registered temperatures.")
                    );

                    return entityModel;
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<LocationToCollectByCitynameDTO>> self = CollectionModel.of(locations);
        self.add(
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).findAll())
                        .withSelfRel()
        );

        return ResponseEntity.ok(self);
    }

    @GetMapping("temperatures")
    public ResponseEntity findAllTemperatures(@RequestParam(name = "page") int page, @RequestParam(name = "max-results") int maxResults) {

        Optional<AllLatestTemperaturesDTO> findAllTheLatestTemperatures = this.temperatureService.findAllTheLatestTemperatures(page, maxResults);

        if (findAllTheLatestTemperatures.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        AllLatestTemperaturesDTO allLatestTemperaturesDTO = findAllTheLatestTemperatures.get();

        List<EntityModel<AllLatestTemperaturesDTO.Temperature>> collect = allLatestTemperaturesDTO
                .getTemperatures()
                .stream()
                .map(item -> {

                    EntityModel<AllLatestTemperaturesDTO.Temperature> entityModel = EntityModel.of(item);
                    entityModel.add(
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).findAllTemperaturesByCityname(item.getCity()))
                                    .withRel(LinkRelation.of("temperatures"))
                                    .withTitle("Find all temperatures for the city.")
                    );

                    entityModel.add(
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deregisterCity(item.getCity()))
                                    .withRel(LinkRelation.of("remove-city"))
                                    .withTitle("Remove the city from monitoring.")
                    );

                    entityModel.add(
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deleteTemperaturesByCityname(item.getCity()))
                                    .withRel(LinkRelation.of("remove-temperatures-by-city"))
                                    .withTitle("Removes the registered temperatures.")
                    );

                    return entityModel;
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<AllLatestTemperaturesDTO.Temperature>> self = CollectionModel.of(collect);
        self.add(
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).findAllTemperatures(page + 1, maxResults))
                        .withSelfRel()
        );

        return ResponseEntity.ok(self);
    }

    @GetMapping("cityname/{city_name}/temperatures")
    public ResponseEntity findAllTemperaturesByCityname(@PathVariable("city_name") String cityname) {

        Optional<TemperaturesByCitynameLast30DaysDTO> assemble = this.temperatureService.temperaturesByCityname(CurrentWeather.Time.LAST_30_HOURS, cityname);

        if (assemble.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(assemble.get());
    }

    @GetMapping("woeid/{woeid}/temperatures")
    public ResponseEntity findAllTemperaturesByWoeid(@PathVariable("woeid") String woeid) {

        Optional<TemperaturesByCitynameLast30DaysDTO> assemble = this.temperatureService.temperaturesByWoeid(CurrentWeather.Time.LAST_30_HOURS, woeid);

        if (assemble.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(assemble.get());
    }

    @PostMapping("cityname/{city_name}")
    public ResponseEntity registerByCityname(@PathVariable("city_name") String cityname) {

        if (cityname.isEmpty() || Objects.isNull(cityname)) {
            throw new CitynameMustBeInformedException("Cityname must be informed.");
        }

        Optional<LocationToCollectByCitynameDTO> registeredCity = this.temperatureService.registerByCityname(cityname);
        if (registeredCity.isPresent()) {

            LocationToCollectByCitynameDTO item = registeredCity.get();

            CollectionModel<Object> links = CollectionModel.empty();
            links.add(
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).findAllTemperaturesByCityname(item.getCityname()))
                            .withRel(LinkRelation.of("temperatures"))
                            .withTitle("Find all temperatures for the city.")
            );

            links.add(
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deregisterCity(item.getCityname()))
                            .withRel(LinkRelation.of("remove-city"))
                            .withTitle("Remove the city from monitoring.")
            );

            links.add(
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deleteTemperaturesByCityname(item.getCityname()))
                            .withRel(LinkRelation.of("remove-temperatures-by-city"))
                            .withTitle("Removes the registered temperatures.")
            );

            return ResponseEntity.ok(links);
        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("woeid/{woeid}")
    public ResponseEntity registerByWoeid(@PathVariable("woeid") String woeid, @RequestParam(name = "cityname") String cityname) {

        if (woeid.isEmpty() || Objects.isNull(woeid)) {
            throw new CitynameMustBeInformedException("Cityname must be informed.");
        }

        Optional<LocationToCollectByCitynameDTO> registeredCity = this.temperatureService.registerByWoeid(cityname, woeid);
        if (registeredCity.isPresent()) {

            LocationToCollectByCitynameDTO item = registeredCity.get();

            CollectionModel<Object> links = CollectionModel.empty();
            links.add(
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).findAllTemperaturesByCityname(item.getCityname()))
                            .withRel(LinkRelation.of("temperatures"))
                            .withTitle("Find all temperatures for the city.")
            );

            links.add(
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deregisterCity(item.getCityname()))
                            .withRel(LinkRelation.of("remove-city"))
                            .withTitle("Remove the city from monitoring.")
            );

            links.add(
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deleteTemperaturesByCityname(item.getCityname()))
                            .withRel(LinkRelation.of("remove-temperatures-by-city"))
                            .withTitle("Removes the registered temperatures.")
            );

            return ResponseEntity.ok(links);
        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("cep/{cep}")
    public ResponseEntity registerByZipCode(@PathVariable("cep") String cep) {

        Optional<LocationToCollectByCitynameDTO> registeredCity = this.temperatureService.registerByZipCode(cep);
        if (registeredCity.isPresent()) {

            LocationToCollectByCitynameDTO item = registeredCity.get();

            CollectionModel<Object> links = CollectionModel.empty();
            links.add(
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).findAllTemperaturesByCityname(item.getCityname()))
                            .withRel(LinkRelation.of("temperatures"))
                            .withTitle("Find all temperatures for the city.")
            );

            links.add(
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deregisterCity(item.getCityname()))
                            .withRel(LinkRelation.of("remove-city"))
                            .withTitle("Remove the city from monitoring.")
            );

            links.add(
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CitiesController.class).deleteTemperaturesByCityname(item.getCityname()))
                            .withRel(LinkRelation.of("remove-temperatures-by-city"))
                            .withTitle("Removes the registered temperatures.")
            );

            return ResponseEntity.ok(links);
        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("cityname/{city_name}")
    public ResponseEntity deregisterCity(@PathVariable("city_name") String cityname) {

        if (cityname.isEmpty() || Objects.isNull(cityname)) {
            throw new CitynameMustBeInformedException("Cityname must be informed.");
        }

        this.temperatureService.deregisterByCityname(cityname);

        if (this.temperatureService
                .findAllCitiesForCollect()
                .stream()
                .filter(item -> item.getCityname().equalsIgnoreCase(cityname))
                .findFirst()
                .isPresent()) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("woeid/{woeid}")
    public ResponseEntity deregisterWoeid(@PathVariable("woeid") String woeid) {

        if (woeid.isEmpty() || Objects.isNull(woeid)) {
            throw new CitynameMustBeInformedException("Cityname must be informed.");
        }

        this.temperatureService.deregisterByWoeid(woeid);

        if (this.temperatureService.locationToCollectExistsByWoeid(woeid)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("cityname/{city_name}/temperatures")
    public ResponseEntity deleteTemperaturesByCityname(@PathVariable("city_name") String cityname) {

        if (cityname.isEmpty() || Objects.isNull(cityname)) {
            throw new CitynameMustBeInformedException("Cityname must be informed.");
        }

        this.temperatureService.clearTemperaturesByCityname(cityname);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("woeid/{woeid}/temperatures")
    public ResponseEntity deleteTemperaturesByWoeid(@PathVariable("woeid") String woeid) {

        if (woeid.isEmpty() || Objects.isNull(woeid)) {
            throw new CitynameMustBeInformedException("Cityname must be informed.");
        }

        this.temperatureService.clearTemperaturesByWoeid(woeid);

        return ResponseEntity.ok().build();
    }
}
