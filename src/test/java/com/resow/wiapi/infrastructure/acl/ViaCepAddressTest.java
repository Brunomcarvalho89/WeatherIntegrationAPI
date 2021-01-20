package com.resow.wiapi.infrastructure.acl;

import com.resow.wiapi.infrastructure.acl.viacep.gateway.ViaCepAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class ViaCepAddressTest {

    @Test
    public void testGettersAndSetters() {

        String cep = "25555666";
        String logradouro = "log";
        String complemento = "casa 123";
        String bairro = "bairro";
        String localidade = "petropolis";
        String uf = "rj";
        String unidade = "unidade";
        String ibge = "ibge";
        String gia = "gia";
        String ddd = "24";
        String siafi = "siafi";

        ViaCepAddress viaCepAddress = new ViaCepAddress();
        viaCepAddress.setCep(cep);
        viaCepAddress.setLogradouro(logradouro);
        viaCepAddress.setComplemento(complemento);
        viaCepAddress.setBairro(bairro);
        viaCepAddress.setLocalidade(localidade);
        viaCepAddress.setUf(uf);
        viaCepAddress.setUnidade(unidade);
        viaCepAddress.setIbge(ibge);
        viaCepAddress.setGia(gia);
        viaCepAddress.setDdd(ddd);
        viaCepAddress.setSiafi(siafi);

        Assertions.assertEquals(cep, viaCepAddress.getCep());
        Assertions.assertEquals(logradouro, viaCepAddress.getLogradouro());
        Assertions.assertEquals(complemento, viaCepAddress.getComplemento());
        Assertions.assertEquals(bairro, viaCepAddress.getBairro());
        Assertions.assertEquals(localidade, viaCepAddress.getLocalidade());
        Assertions.assertEquals(ibge, viaCepAddress.getIbge());
        Assertions.assertEquals(gia, viaCepAddress.getGia());
        Assertions.assertEquals(ddd, viaCepAddress.getDdd());
        Assertions.assertEquals(siafi, viaCepAddress.getSiafi());
    }

}
