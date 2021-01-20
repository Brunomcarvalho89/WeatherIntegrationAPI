package com.resow.wiapi.application.dto.assembler;

import com.resow.wiapi.application.dto.TemperaturesByCitynameLast30DaysDTO;
import com.resow.wiapi.domain.CurrentWeather;
import java.util.List;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface ITemperaturesByCityLast30DaysAssemblerDTO extends IAssemblerDTO<List<CurrentWeather>, TemperaturesByCitynameLast30DaysDTO> {

}
