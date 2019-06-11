package com.octo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.entity.Environment;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class EnvironmentDAOTest {

    @Autowired
    private IDAO<Environment> environmentDAO;

    @Test
    public void testFindAll() {
        List<Environment> environments = environmentDAO.findAll();

        assertNotNull(environments);
        assertFalse(environments.isEmpty());
    }

    @Test
    public void testSave() {
        Exception exception = null;

        try {
            this.environmentDAO.save(null);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(UnsupportedOperationException.class, exception.getClass());
    }
}
