package com.octo.utils.predicate.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.octo.helpers.TestDTO;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;

public class QueryFilterTest {

    @Test
    public void testValidateWithBadPagination() {
        QueryFilter filter = new QueryFilter();
        GlobalException exception = null;
        filter.setPage(-1);
        try {
            filter.validate(0, 0);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.WRONG_VALUE.getMessage(), exception.getMessage());
        assertEquals("page", exception.getError().getField());
    }

    @Test
    public void testValidateWithBadCount() {
        QueryFilter filter = new QueryFilter();
        GlobalException exception = null;
        filter.setCount(-1);
        try {
            filter.validate(0, 0);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.WRONG_VALUE.getMessage(), exception.getMessage());
        assertEquals("count", exception.getError().getField());
    }

    @Test
    public void testValidateSetDefaultCount() {
        QueryFilter filter = new QueryFilter();

        filter.validate(5, 10);

        assertEquals(5, filter.getCount());
    }

    @Test
    public void testValidateSetMaximumCount() {
        QueryFilter filter = new QueryFilter();
        filter.setCount(15);

        filter.validate(5, 10);

        assertEquals(10, filter.getCount());
    }

    @Test
    public void testValidateWithGoodCount() {
        QueryFilter filter = new QueryFilter();
        filter.setCount(15);

        filter.validate(5, 100);

        assertEquals(15, filter.getCount());
    }

    @Test
    public void testGetSpecificFilter() {
        TestQueryFilter dto = new TestQueryFilter("test");
        assertNull(dto.getFilter(null, null));
        List<IPredicateFilter> filters = dto.getFilters(TestDTO.class);
        assertNotNull(filters);
    }

    class TestQueryFilter extends QueryFilter {
        @FilterType(type = FilterType.Type.TEXT)
        private String test;

        public TestQueryFilter(String value) {
            test = value;
        }

        @Override
        public boolean isSpecificFilter(String name) {
            return "test".equals(name);
        }

        @Override
        public IPredicateFilter getSpecificFilter(String name, String value) {
            return new TextPredicateFilter(name, value);
        }

        public IPredicateFilter getFilter(String name, String value) {
            return super.getSpecificFilter(name, value);
        }
    }

}
