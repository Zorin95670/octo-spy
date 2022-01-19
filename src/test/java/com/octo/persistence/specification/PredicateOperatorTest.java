package com.octo.persistence.specification;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class PredicateOperatorTest {

    @Test
    void constructorTest() {
        assertEquals("eq", PredicateOperator.EQUALS.getValue());
        assertEquals("lt", PredicateOperator.INFERIOR.getValue());
        assertEquals("gt", PredicateOperator.SUPERIOR.getValue());
        assertEquals("bt", PredicateOperator.BETWEEN.getValue());
    }

    @Test
    void isValidTest() {
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
    void getTest() {
        assertTrue(PredicateOperator.get("bad").isEmpty());
        Optional<PredicateOperator> test = PredicateOperator.get(PredicateOperator.EQUALS.getValue());
        assertFalse(test.isEmpty());
        assertEquals(PredicateOperator.EQUALS, test.get());
    }
}
