package com.resow.wiapi.application.dto.assembler.impl;

import com.resow.wiapi.application.dto.LocationToCollectByCitynameDTO;
import java.util.Optional;
import com.resow.wiapi.application.dto.assembler.ILocationToCollectAssemblerDTO;
import com.resow.wiapi.domain.LocationToCollect;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class LocationToCollectByCitynameAssemblerDTO implements ILocationToCollectAssemblerDTO {

    @Override
    public Optional<LocationToCollectByCitynameDTO> assemble(LocationToCollect entity) {
        return Optional.of(new LocationToCollectByCitynameDTO(entity.getCityname()));
    }

}
