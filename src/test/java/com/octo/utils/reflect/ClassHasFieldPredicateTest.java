package com.octo.utils.reflect;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("unit")
class ClassHasFieldPredicateTest {

    @Test
    void testSimpleClass() {
        class Test {
            private String test;
        }
        final ClassHasFieldPredicate predicate = new ClassHasFieldPredicate(Test.class);

        assertFalse(predicate.test("bad"));
        assertTrue(predicate.test("test"));
    }

    @Test
    void testClassWithSubClass() {
        class Test {
            private String test;
        }
        class SubTest extends Test {
            private String subTest;
        }
        final ClassHasFieldPredicate predicate = new ClassHasFieldPredicate(SubTest.class);

        assertFalse(predicate.test("bad"));
        assertTrue(predicate.test("subTest"));
        assertTrue(predicate.test("test"));
    }
}
