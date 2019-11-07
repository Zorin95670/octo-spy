package com.octo.dao.filter.deployment;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.entity.Deployment;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class PreviousDeploymentFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void test() {
        Deployment deployment = new Deployment();
        deployment.setProject(new Project());
        deployment.setEnvironment(new Environment());

        PreviousDeploymentFilter filter = new PreviousDeploymentFilter(deployment);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Deployment> query = builder.createQuery(Deployment.class);
        Root<Deployment> root = query.from(Deployment.class);

        assertNotNull(filter.apply(builder, root));
    }
}
