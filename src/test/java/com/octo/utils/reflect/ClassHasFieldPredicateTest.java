package com.octo.utils.reflect;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ClassHasFieldPredicateTest {

    @Test
    void testSimpleClass() {
        class Test {
            @SuppressWarnings("unused") // Used in tests below.
            private String test;
        }
        final ClassHasFieldPredicate predicate = new ClassHasFieldPredicate(Test.class);

        assertFalse(predicate.test("bad"));
        assertTrue(predicate.test("test"));
    }

    @Test
    void testClassWithSubClass() {
        class Test {
            @SuppressWarnings("unused") // Used in tests below.
            private String test;
        }
        class SubTest extends Test {
            @SuppressWarnings("unused") // Used in tests below.
            private String subTest;
        }
        final ClassHasFieldPredicate predicate = new ClassHasFieldPredicate(SubTest.class);

        assertFalse(predicate.test("bad"));
        assertTrue(predicate.test("subTest"));
        assertTrue(predicate.test("test"));
    }
}
