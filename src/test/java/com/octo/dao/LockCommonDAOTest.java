package com.octo.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.helpers.EntityHelpers;
import com.octo.helpers.EntityService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class LockCommonDAOTest {

    @Autowired
    EntityService service;

    @Test
    public void testLoadEntityByIdWithLock() {
        EntityHelpers entity = service.save();
        entity = service.loadEntityByIdWithLock(entity.getId());
        assertNotNull(entity);
    }

    @Test
    public void testFindWithLock() {
        List<EntityHelpers> list = service.findWithLock();
        assertNotNull(list);
    }

    @Test
    public void testLoadWithLock() {
        Optional<EntityHelpers> opt = service.loadWithLock();
        assertNotNull(opt);
    }
}
