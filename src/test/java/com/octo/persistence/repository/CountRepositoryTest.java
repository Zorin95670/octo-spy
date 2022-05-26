package com.octo.persistence.repository;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.octo.helper.MockHelper;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Deployment;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class CountRepositoryTest extends MockHelper {
    @Mock
    EntityManager entityManager;
    @InjectMocks
    CountRepositoryImpl<Deployment> repository;


    @Test
    void testCountField() {
        CriteriaBuilder builder = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<Deployment> criteriaQuery = Mockito.mock(CriteriaQuery.class);
        Root<Deployment> root = Mockito.mock(Root.class);
        Path<Object> path = Mockito.mock(Path.class);
        Predicate predicate = Mockito.mock(Predicate.class);

        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(builder);
        Mockito.when(builder.createQuery(Mockito.any(Deployment.class.getClass()))).thenReturn(criteriaQuery);
        Mockito.when(criteriaQuery.from(Mockito.any(Deployment.class.getClass()))).thenReturn(root);
        Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
        Mockito.when(path.in(Mockito.any(Collection.class))).thenReturn(predicate);

        TypedQuery<Object[]> query = Mockito.mock(TypedQuery.class);
        Object[] array = new Object[]{"1", 1};
        List<Object[]> list = new ArrayList<>();
        list.add(array);

        Mockito.when(entityManager.createQuery(Mockito.any(CriteriaQuery.class))).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(list);
        Specification<Deployment> specification = Mockito.mock(Specification.class);

        ObjectNode expected = JsonNodeFactory.instance.objectNode();
        expected.put("1", 1);
        assertEquals(expected, repository.count(Deployment.class, specification, "id", null));

        expected = JsonNodeFactory.instance.objectNode();
        expected.put("1", 1);
        assertEquals(expected, repository.count(Deployment.class, specification, "id", "1"));

        expected = JsonNodeFactory.instance.objectNode();
        expected.put("0", 0);
        expected.put("1", 1);
        assertEquals(expected, repository.count(Deployment.class, specification, "id", "0,1"));
    }

    @Test
    void testCountFields() {
        CriteriaBuilder builder = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<Deployment> criteriaQuery = Mockito.mock(CriteriaQuery.class);
        Root<Deployment> root = Mockito.mock(Root.class);
        Path<Object> path = Mockito.mock(Path.class);
        Expression<Long> expression = Mockito.mock(Expression.class);
        Predicate predicate = Mockito.mock(Predicate.class);

        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(builder);
        Mockito.when(builder.createQuery(Mockito.any(Deployment.class.getClass()))).thenReturn(criteriaQuery);
        Mockito.when(builder.count(Mockito.any())).thenReturn(expression);
        Mockito.when(criteriaQuery.from(Mockito.any(Deployment.class.getClass()))).thenReturn(root);
        Mockito.when(criteriaQuery.groupBy(Mockito.any(List.class))).thenReturn(criteriaQuery);
        Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
        Mockito.when(expression.alias(Mockito.any())).thenReturn(expression);


        TypedQuery<Object[]> query = Mockito.mock(TypedQuery.class);
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"1", 1});
        list.add(new Object[]{"2", 2});

        Mockito.when(entityManager.createQuery(Mockito.any(CriteriaQuery.class))).thenReturn(query);
        Mockito.when(query.getResultStream()).thenReturn(list.stream());
        Specification<Deployment> specification = Mockito.mock(Specification.class);

        String expected = "[{\"id\":\"1\",\"count\":\"1\"},{\"id\":\"2\",\"count\":\"2\"}]";
        assertEquals(expected, repository.count(Deployment.class, specification, List.of("id")).toString());
    }

    @Test
    void testConvertValueToObject() {
        assertNull(repository.convertValueToObject(Deployment.class, null, null));
        assertEquals("1", repository.convertValueToObject(Deployment.class, "id", "1"));
        assertTrue((Boolean) repository.convertValueToObject(Deployment.class, "alive", "true"));
        assertFalse((Boolean) repository.convertValueToObject(Deployment.class, "alive", "false"));

        GlobalException exception = null;
        try {
            repository.convertValueToObject(Deployment.class, "alive", "abc");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.WRONG_FIELD.getMessage(), exception.getMessage());

    }
}
