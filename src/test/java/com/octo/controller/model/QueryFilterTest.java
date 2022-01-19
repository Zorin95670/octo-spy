package com.octo.controller.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class QueryFilterTest {

    @Test
    void testGetPagination() {
        QueryFilter filter = new QueryFilter();
        Pageable pageable = filter.getPagination();

        assertNotNull(pageable);
        assertNotNull(pageable.getSort());
        assertFalse(pageable.getSort().isSorted());

        filter.setSort("asc");
        filter.setOrder("id");
        pageable = filter.getPagination();

        assertNotNull(pageable);
        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
        assertNotNull(pageable.getSort());
        assertEquals("id: ASC", pageable.getSort().toString());

        filter.setSort("bad");
        filter.setOrder("id");
        filter.setCount(100);
        filter.setPage(50);
        pageable = filter.getPagination();

        assertNotNull(pageable);
        assertEquals(50, pageable.getPageNumber());
        assertEquals(100, pageable.getPageSize());
        assertNotNull(pageable.getSort());
        assertEquals("id: DESC", pageable.getSort().toString());
    }
}
