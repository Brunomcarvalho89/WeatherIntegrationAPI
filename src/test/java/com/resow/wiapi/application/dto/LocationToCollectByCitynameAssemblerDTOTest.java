package com.resow.wiapi.application.dto;

import com.resow.wiapi.application.dto.LocationToCollectByCitynameDTO;
import com.resow.wiapi.application.dto.assembler.impl.LocationToCollectByCitynameAssemblerDTO;
import com.resow.wiapi.domain.LocationToCollect;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class LocationToCollectByCitynameAssemblerDTOTest {

    @Test
    public void testAssemble() {

        final Long idLocationToCollect = 2l;
        final String cityname = "City-Test";

        final LocationToCollect address = new LocationToCollect();
        address.setId(idLocationToCollect);
        address.setCityname(cityname);

        LocationToCollectByCitynameAssemblerDTO locationToCollectByCitynameAssemblerDTO = new LocationToCollectByCitynameAssemblerDTO();

        Optional<LocationToCollectByCitynameDTO> assemble = locationToCollectByCitynameAssemblerDTO.assemble(address);

        Assertions.assertTrue(assemble.isPresent());

        Assertions.assertEquals(cityname, assemble.get().getCityname());
    }
}
