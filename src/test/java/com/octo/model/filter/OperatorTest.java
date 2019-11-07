package com.octo.model.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OperatorTest {

    @Test
    public void constructorTest() {
        assertEquals("eq", Operator.EQUALS.getValue());
        assertEquals("lt", Operator.INFERIOR.getValue());
        assertEquals("gt", Operator.SUPERIOR.getValue());
        assertEquals("bt", Operator.BETWEEN.getValue());
    }

    @Test
    public void isValidTest() {
        assertTrue(Operator.isValid("eq"));
        assertTrue(Operator.isValid("LT"));
        assertTrue(Operator.isValid("Gt"));
        assertTrue(Operator.isValid("bT"));
        assertFalse(Operator.isValid("="));
        assertFalse(Operator.isValid("<"));
        assertFalse(Operator.isValid(">"));
        assertFalse(Operator.isValid("<>"));
        assertFalse(Operator.isValid(null));
        assertFalse(Operator.isValid("!="));
        assertFalse(Operator.isValid("><"));
        assertFalse(Operator.isValid("!"));
        assertFalse(Operator.isValid("bad"));
    }
}
