package com.resow.wiapi.infrastructure.api;

import com.resow.wiapi.infrastructure.api.exception.ExceptionDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class ExceptionDetailsTest {

    @Test
    public void testBuilder() {

        final String details = "details";
        final String title = "Teste Exception Details";
        final Integer status = 200;
        final Long timestamp = 3131315646L;

        ExceptionDetails build = ExceptionDetails.builder()
                .withDetails(details)
                .withStatus(status)
                .withTimestamp(timestamp)
                .withTitle(title)
                .build();

        Assertions.assertEquals(details, build.getDetails());
        Assertions.assertEquals(title, build.getTitle());
        Assertions.assertEquals(status, build.getStatus());
        Assertions.assertEquals(timestamp, build.getTimestamp());
    }

    @Test
    public void testBuilderEmpty() {

        ExceptionDetails build = ExceptionDetails.builder()
                .build();

        Assertions.assertEquals("", build.getDetails());
        Assertions.assertEquals("", build.getTitle());
        Assertions.assertEquals(-1, build.getStatus());
        Assertions.assertEquals(0, build.getTimestamp());
    }

}
