package com.octo.dao.filter.common;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.entity.Environment;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class EqualsFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testApply() {
        EqualsFilter<Environment> filter = new EqualsFilter<>("name", "QA");

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Environment> query = builder.createQuery(Environment.class);
        Root<Environment> root = query.from(Environment.class);

        Predicate predicate = filter.apply(builder, root);

        assertNotNull(predicate);
    }
}
