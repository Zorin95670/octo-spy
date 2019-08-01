package com.octo.model.dto.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultDTOTest {

    @Test
    public void testToString() {
        class Test extends DefaultDTO {
            @SuppressWarnings("unused")
            public String name;
        }
        Test test = new Test();
        test.name = "test";
        assertEquals("{\"name\":\"test\"}", test.toString());
    }

}
