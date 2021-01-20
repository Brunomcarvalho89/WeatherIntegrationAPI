package com.resow.wiapi.application.dto.assembler;

import com.resow.wiapi.application.dto.DTOBase;
import java.util.Optional;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface IAssemblerDTO<T, E extends DTOBase> {

    Optional<E> assemble(T entity);
}
