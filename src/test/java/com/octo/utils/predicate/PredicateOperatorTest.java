package com.octo.utils.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PredicateOperatorTest {

    @Test
    public void constructorTest() {
        assertEquals("eq", PredicateOperator.EQUALS.getValue());
        assertEquals("lt", PredicateOperator.INFERIOR.getValue());
        assertEquals("gt", PredicateOperator.SUPERIOR.getValue());
        assertEquals("bt", PredicateOperator.BETWEEN.getValue());
    }

    @Test
    public void isValidTest() {
        assertTrue(PredicateOperator.isValid("eq"));
        assertTrue(PredicateOperator.isValid("LT"));
        assertTrue(PredicateOperator.isValid("Gt"));
        assertTrue(PredicateOperator.isValid("bT"));
        assertFalse(PredicateOperator.isValid("="));
        assertFalse(PredicateOperator.isValid("<"));
        assertFalse(PredicateOperator.isValid(">"));
        assertFalse(PredicateOperator.isValid("<>"));
        assertFalse(PredicateOperator.isValid(null));
        assertFalse(PredicateOperator.isValid("!="));
        assertFalse(PredicateOperator.isValid("><"));
        assertFalse(PredicateOperator.isValid("!"));
        assertFalse(PredicateOperator.isValid("bad"));
    }

    @Test
    public void getTest() {
        assertNull(PredicateOperator.get("bad"));
        assertEquals(PredicateOperator.EQUALS, PredicateOperator.get(PredicateOperator.EQUALS.getValue()));
    }
}
