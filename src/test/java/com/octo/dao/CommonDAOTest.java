package com.octo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.entity.Environment;
import com.octo.model.exception.OctoException;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@Transactional
public class CommonDAOTest {

    @Autowired
    IDAO<Environment> defaultDAO;

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

        Exception exception = null;

        try {
            data = defaultDAO.load((builder, root) -> {
                return builder.equal(root.get("name"), "bad");
            });
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(NoResultException.class, exception.getClass());
    }
}
