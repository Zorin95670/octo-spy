package com.octo.persistence.specification;

import com.octo.helper.MockHelper;
import com.octo.persistence.model.Project;
import com.octo.persistence.specification.filter.BooleanPredicateFilter;
import com.octo.persistence.specification.filter.DatePredicateFilter;
import com.octo.persistence.specification.filter.FilterType;
import com.octo.persistence.specification.filter.IPredicateFilter;
import com.octo.persistence.specification.filter.NumberPredicateFilter;
import com.octo.persistence.specification.filter.TextPredicateFilter;
import com.octo.persistence.specification.filter.TokenPredicateFilter;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("unit")
class SpecificationHelperTest extends MockHelper {

    @Test
    void testGetFilters() {
        SpecificationHelper<Entity> helper = new SpecificationHelper<>(Entity.class, Map.of(
                "aNumber", "1",
                "aString", "test",
                "aDate", "null",
                "aBoolean", "true",
                "aToken", "a"
        ));

        List<IPredicateFilter> filters = helper.getFilters();
        assertNotNull(filters);
        assertEquals(5, filters.size());
        assertEquals(NumberPredicateFilter.class, filters.get(0).getClass());
        assertEquals(TextPredicateFilter.class, filters.get(1).getClass());
        assertEquals(BooleanPredicateFilter.class, filters.get(2).getClass());
        assertEquals(TokenPredicateFilter.class, filters.get(3).getClass());
        assertEquals(DatePredicateFilter.class, filters.get(4).getClass());
    }

    @Test
    void testToPredicate() {
        EntityManager entityManager = mockEntityManager(Project.class);
        SpecificationHelper<Project> helper = new SpecificationHelper<>(Project.class, Map.of(
                "id", "1"
        ));
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Project> query = builder.createQuery(Project.class);
        final Root<Project> root = query.from(Project.class);
        assertNotNull(helper.toPredicate(root, query, builder));
    }

    class Entity {
        @FilterType(type = FilterType.Type.NUMBER)
        private Long aNumber;
        @FilterType(type = FilterType.Type.TEXT)
        private String aString;
        @FilterType(type = FilterType.Type.BOOLEAN)
        private boolean aBoolean;
        @FilterType(type = FilterType.Type.TOKEN)
        private String aToken;
        @FilterType(type = FilterType.Type.DATE)
        private Date aDate;
    }
}
