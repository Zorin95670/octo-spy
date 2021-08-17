package com.octo.utils.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.helpers.EntityHelpers;
import com.octo.helpers.EntityTestSearch;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class PredicateMapperTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testApply() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<EntityHelpers> query = builder.createQuery(EntityHelpers.class);
        final Root<EntityHelpers> root = query.from(EntityHelpers.class);

        final EntityTestSearch dto = new EntityTestSearch();
        PredicateMapper<EntityHelpers, EntityTestSearch> mapper = new PredicateMapper<EntityHelpers, EntityTestSearch>(
                EntityHelpers.class, dto);

        Predicate[] predicates = mapper.apply(builder, root);

        assertNotNull(predicates);
        assertEquals(0, predicates.length);

        dto.setName("test");
        mapper = new PredicateMapper<EntityHelpers, EntityTestSearch>(EntityHelpers.class, dto);
        predicates = mapper.apply(builder, root);

        assertNotNull(predicates);
        assertEquals(1, predicates.length);
    }

}
