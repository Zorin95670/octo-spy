package com.octo.helper;

import com.octo.model.common.Constants;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

public abstract class MockHelper {

    public UriInfo mockUriInfo() {
        UriInfo mock = Mockito.mock(UriInfo.class);
        Mockito.when(mock.getQueryParameters()).thenReturn(new MultivaluedHashMap<>());
        return mock;
    }

    public ContainerRequestContext mockContext() {
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        String encodedUser = Base64.getUrlEncoder().encodeToString("login:password".getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY,
                String.format("%s %s", Constants.AUTHENTICATION_BASIC_SCHEME, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);

        return context;
    }

    public <T> EntityManager mockEntityManager(Class<T> entityClass) {
        return mockEntityManager(entityClass, Mockito.mock(EntityManager.class));
    }

    public <T> EntityManager mockEntityManager(Class<T> entityClass, EntityManager entityManager) {
        CriteriaBuilder builder = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<T> query = Mockito.mock(CriteriaQuery.class);
        Root<T> root = Mockito.mock(Root.class);
        Path<Object> path = Mockito.mock(Path.class);
        Predicate predicate = Mockito.mock(Predicate.class);

        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(builder);
        Mockito.when(builder.createQuery(Mockito.any(entityClass.getClass()))).thenReturn(query);
        Mockito.when(query.from(Mockito.any(entityClass.getClass()))).thenReturn(root);
        Mockito.when(builder.and(Mockito.any())).thenReturn(predicate);
        Mockito.when(builder.or(Mockito.any())).thenReturn(predicate);
        Mockito.when(builder.not(Mockito.any())).thenReturn(predicate);
        Mockito.when(builder.lessThan(Mockito.any(), Mockito.any(Date.class))).thenReturn(predicate);
        Mockito.when(builder.greaterThan(Mockito.any(), Mockito.any(Date.class))).thenReturn(predicate);
        Mockito.when(builder.between(Mockito.any(), Mockito.any(Date.class), Mockito.any(Date.class)))
                .thenReturn(predicate);
        Mockito.when(builder.equal(Mockito.any(), Mockito.any())).thenReturn(predicate);
        Mockito.when(builder.notEqual(Mockito.any(), Mockito.any())).thenReturn(predicate);
        Mockito.when(root.get(Mockito.anyString())).thenReturn(path);
        Mockito.when(path.as(Mockito.any())).thenReturn(path);
        Mockito.when(path.in(Mockito.any(Collection.class))).thenReturn(predicate);

        return entityManager;
    }
}
