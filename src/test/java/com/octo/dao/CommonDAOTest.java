package com.octo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.dao.filter.common.EqualsFilter;
import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.entity.Environment;
import com.octo.model.exception.OctoException;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@Transactional
public class CommonDAOTest {

    @Autowired
    IDAO<Environment, EnvironmentDTO> defaultDAO;

    @Test
    public void testGetType() {
        assertEquals(Environment.class, defaultDAO.getType());
    }

    @Test
    public void testFindAll() {
        Exception exception = null;
        try {
            defaultDAO.findAll();
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(UnsupportedOperationException.class, exception.getClass());
    }

    @Test
    public void testSaveAndLoad() throws OctoException {
        Environment entity = new Environment();
        entity.setName("Test");

        entity = defaultDAO.save(entity);
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertEquals("Test", entity.getName());

        // Test load by id
        Environment data = defaultDAO.loadById(entity.getId());
        assertNotNull(data);
        assertEquals(entity.getId(), data.getId());
        assertEquals(entity.getName(), data.getName());

        data = defaultDAO.loadById(666L);
        assertNull(data);

        // Test load by criteria
        data = defaultDAO.load((builder, root) -> {
            return builder.equal(root.get("name"), "QA");
        });
        assertNotNull(data);
        assertNotNull(data.getId());
        assertEquals("QA", data.getName());
        data = defaultDAO.load((builder, root) -> {
            return builder.equal(root.get("name"), "bad");
        });

        assertNull(data);
    }

    @Test
    public void testDetele() {
        Environment entity = new Environment();
        entity.setName("Test delete");

        entity = defaultDAO.save(entity);
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertEquals("Test delete", entity.getName());

        defaultDAO.delete(entity);

        assertNull(defaultDAO.loadById(entity.getId()));
    }

    @Test
    public void testFind() {
        List<Environment> environments = this.defaultDAO.find(null, null);

        assertNotNull(environments);
        assertNotEquals(0, environments.size());

        environments = this.defaultDAO.find(new EnvironmentDTO(), (builder, root) -> {
            return new Predicate[] { new EqualsFilter<Environment>("id", -1L).apply(builder, root) };
        });

        assertNotNull(environments);
        assertEquals(0, environments.size());
    }
}
