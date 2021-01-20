package com.resow.wiapi.application.dto.assembler;

import com.resow.wiapi.application.dto.AllLatestTemperaturesDTO;
import com.resow.wiapi.domain.CurrentWeather;
import java.util.List;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface IAllLatestTemperaturesAssemblerDTO extends IAssemblerDTO<List<CurrentWeather>, AllLatestTemperaturesDTO> {

}
