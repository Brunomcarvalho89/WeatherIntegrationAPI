package com.resow.wiapi.infrastructure.acl.hgbrasilweather.request;

import com.resow.wiapi.domain.LocationToCollect;
import com.resow.wiapi.infrastructure.acl.Request;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public interface IRequestFactory {

    Request get(LocationToCollect locationToCollect);
}
