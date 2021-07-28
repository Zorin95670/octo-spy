package com.octo.service;

import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.octo.dao.IDAO;
import com.octo.model.entity.Deployment;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    IDAO<Deployment, QueryFilter> deploymentDAO;

    @InjectMocks
    ClientService service;

    @Test
    void testFindALl() {
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        CriteriaBuilder builder = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<String> criteria = Mockito.mock(CriteriaQuery.class);
        Root<Deployment> root = Mockito.mock(Root.class);
        Path<Object> client = Mockito.mock(Path.class);
        TypedQuery<String> query = Mockito.mock(TypedQuery.class);

        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(builder);
        Mockito.when(entityManager.createQuery(Mockito.any(criteria.getClass()))).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(null);
        Mockito.when(builder.createQuery(Mockito.eq(String.class))).thenReturn(criteria);
        Mockito.when(builder.asc(Mockito.any())).thenReturn(Mockito.mock(Order.class));
        Mockito.when(criteria.from(Mockito.eq(Deployment.class))).thenReturn(root);
        Mockito.when(criteria.select(Mockito.any())).thenReturn(criteria);
        Mockito.when(criteria.distinct(Mockito.anyBoolean())).thenReturn(criteria);
        Mockito.when(criteria.orderBy(Mockito.any(Order.class))).thenReturn(criteria);

        Mockito.when(root.get(Mockito.anyString())).thenReturn(client);
        Mockito.when(this.deploymentDAO.getEntityManager()).thenReturn(entityManager);

        assertNull(this.service.findAll());
    }
}
