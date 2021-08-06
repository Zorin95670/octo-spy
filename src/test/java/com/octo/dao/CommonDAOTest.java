package com.octo.dao;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.octo.helpers.EntityHelpers;
import com.octo.helpers.EntityTestSearch;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@Transactional
class CommonDAOTest {

    @Autowired
    IDAO<EntityHelpers, QueryFilter> dao;

    @Test
    void testGetType() {
        assertEquals(EntityHelpers.class, this.dao.getType());
    }

    @BeforeEach
    void cleanDB() {
        final EntityTestSearch filter = new EntityTestSearch();
        filter.setId("not_null");
        this.dao.deleteAll(filter);
    }

    @Test
    void testSaveAndDelete() {
        final EntityHelpers entityToSave = new EntityHelpers();
        entityToSave.setName("testSaveAndDelete");

        final EntityHelpers entity = this.dao.save(entityToSave);

        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertEquals("testSaveAndDelete", entity.getName());

        this.dao.delete(entity);

        assertNull(this.dao.loadById(entity.getId()));
    }

    @Test
    void testCount() {
        Long count = this.dao.count(null);

        assertNotNull(count);

        final EntityHelpers entity = new EntityHelpers();
        entity.setName("testCount");
        this.dao.save(entity);
        EntityTestSearch search = new EntityTestSearch();
        search.setName("testCount");
        count = this.dao.count(search);

        assertNotNull(count);
    }

    @Test
    void testLoadById() {
        final EntityHelpers EntityTestToSave = new EntityHelpers();
        EntityTestToSave.setName("testLoadById");

        final EntityHelpers EntityTest = this.dao.save(EntityTestToSave);

        final EntityHelpers load = this.dao.loadById(EntityTest.getId());

        assertNotNull(load);
        assertNotNull(load.getId());
        assertEquals("testLoadById", load.getName());

    }

    @Test
    void testLoadEntityById() {
        GlobalException exception = null;
        try {
            this.dao.loadEntityById(null);
        } catch (final GlobalException hre) {
            exception = hre;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("id", exception.getError().getField());

        exception = null;
        try {
            this.dao.loadEntityById(-1L, "test");
        } catch (final GlobalException hre) {
            exception = hre;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("test", exception.getError().getField());

        EntityHelpers entity = new EntityHelpers();
        entity.setName("testLoadEntityById");
        entity = this.dao.save(entity);
        exception = null;
        try {
            this.dao.loadEntityById(entity.getId());
        } catch (final GlobalException hre) {
            exception = hre;
        }

        assertNull(exception);
    }

    @Test
    void testLoadEntityByIdWithLock() {
        GlobalException exception = null;
        try {
            this.dao.loadEntityByIdWithLock(null);
        } catch (final GlobalException hre) {
            exception = hre;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("id", exception.getError().getField());

        exception = null;
        try {
            this.dao.loadEntityByIdWithLock(-1L, "id");
        } catch (final GlobalException hre) {
            exception = hre;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("id", exception.getError().getField());
    }

    @Test
    void testFindWithAscOrder() {
        GlobalException exception = null;
        final EntityTestSearch entity = new EntityTestSearch();
        entity.setCount(5);
        entity.setPage(0);
        entity.setOrder("id");
        entity.setSort("asc");
        entity.setName("testFindWithAscOrder");

        List<EntityHelpers> list = null;
        List<EntityHelpers> expected = new ArrayList<>();
        EntityHelpers entity1 = new EntityHelpers();
        entity1.setName("testFindWithAscOrder");
        entity1 = this.dao.save(entity1);
        expected.add(entity1);
        EntityHelpers entity2 = new EntityHelpers();
        entity2.setName("testFindWithAscOrder");
        entity2 = this.dao.save(entity2);
        expected.add(entity2);
        try {
            list = this.dao.find(entity);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(list);
        assertFalse(list.isEmpty());

        assertArrayEquals(expected.stream().map(EntityHelpers::getId).sorted().toArray(),
                list.stream().map(EntityHelpers::getId).toArray());
    }

    @Test
    void testFindWithDescOrder() {
        GlobalException exception = null;
        final EntityTestSearch entity = new EntityTestSearch();
        entity.setCount(5);
        entity.setPage(0);
        entity.setOrder("id");
        entity.setSort("desc");
        entity.setName("testFindWithDescOrder");

        List<EntityHelpers> list = null;
        List<EntityHelpers> expected = new ArrayList<>();
        EntityHelpers entity1 = new EntityHelpers();
        entity1.setName("testFindWithDescOrder");
        entity1 = this.dao.save(entity1);
        expected.add(entity1);
        EntityHelpers entity2 = new EntityHelpers();
        entity2.setName("testFindWithDescOrder");
        entity2 = this.dao.save(entity2);
        expected.add(entity2);
        try {
            list = this.dao.find(entity);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(list);
        assertFalse(list.isEmpty());

        assertArrayEquals(expected.stream().map(EntityHelpers::getId).sorted((a, b) -> b.compareTo(a)).toArray(),
                list.stream().map(EntityHelpers::getId).toArray());
    }

    @Test
    void testLoad() {
        EntityHelpers entity1 = new EntityHelpers();
        entity1.setName("testFindWithFilter");
        entity1 = this.dao.save(entity1);
        EntityHelpers entity2 = new EntityHelpers();
        entity2.setName("testFindWithFilter");
        entity2 = this.dao.save(entity2);
        final EntityTestSearch dto = new EntityTestSearch();
        dto.setId(entity1.getId().toString());
        dto.setCount(5);
        dto.setPage(0);
        Optional<EntityHelpers> opt = this.dao.load(dto);

        assertTrue(opt.isPresent());
    }

    @Test
    void testLoadWithNullDTO() {
        Optional<EntityHelpers> opt = this.dao.load(null);
        assertFalse(opt.isPresent());
        opt = this.dao.loadWithLock(null);
        assertFalse(opt.isPresent());
    }

    @Test
    void testFindWithOutFilter() {
        EntityHelpers entity1 = new EntityHelpers();
        entity1.setName("testFindWithFilter");
        entity1 = this.dao.save(entity1);
        EntityHelpers entity2 = new EntityHelpers();
        entity2.setName("testFindWithFilter");
        entity2 = this.dao.save(entity2);
        final List<EntityHelpers> list = this.dao.find(null);

        assertNotNull(list);
    }

    @Test
    void testFindWithoutPagination() {
        final EntityTestSearch dto = new EntityTestSearch();
        Long count = this.dao.count(dto);
        dto.setCount(5);
        dto.setPage(0);
        List<EntityHelpers> list = this.dao.find(dto, false);
        assertNotNull(list);
        assertEquals(Long.valueOf(count), Long.valueOf(list.size()));

        dto.setCount(2);
        dto.setPage(5);
        count = this.dao.count(dto);
        list = this.dao.find(dto, true);
        assertNotNull(list);
        assertEquals(Long.valueOf(count), Long.valueOf(list.size()));

        dto.setCount(0);
        dto.setPage(5);
        count = this.dao.count(dto);
        list = this.dao.find(dto, false);
        assertNotNull(list);
        assertEquals(Long.valueOf(count), Long.valueOf(list.size()));
    }

    /*
     * Test getEntityOrder.
     */
    @Test
    void testGetEntityOrderBadOrder() {
        final EntityTestSearch dto = new EntityTestSearch();
        dto.setOrder("bad");
        dto.setSort("desc");

        GlobalException exception = null;
        try {
            this.dao.find(dto);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.WRONG_ORDER.getMessage(), exception.getError().getMessage());
        assertEquals("order", exception.getError().getField());
        assertEquals("bad", exception.getError().getValue());
    }

    @Test
    void testGetEntityOrderBadSort() {
        final EntityTestSearch entity = new EntityTestSearch();
        entity.setOrder("name");
        entity.setSort("bad");

        GlobalException exception = null;
        try {
            this.dao.find(entity, null);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.WRONG_SORT.getMessage(), exception.getError().getMessage());
        assertEquals("sort", exception.getError().getField());
        assertEquals("bad", exception.getError().getValue());
    }

    @Test
    void testDeleteAllWithoutPredicates() {
        GlobalException exception = null;
        try {
            this.dao.deleteAll(null);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.DELETE_WITHOUT_WHERE.getMessage(), exception.getError().getMessage());
        assertEquals(ErrorType.DELETE_WITHOUT_WHERE.getStatus(), exception.getStatus());
        assertEquals("filter", exception.getError().getField());

        exception = null;
        try {
            this.dao.deleteAll(new EntityTestSearch());
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.DELETE_WITHOUT_WHERE.getMessage(), exception.getError().getMessage());
        assertEquals(ErrorType.DELETE_WITHOUT_WHERE.getStatus(), exception.getStatus());
        assertEquals("predicates", exception.getError().getField());
    }

    @Test
    void testDeleteAllWithPredicates() {
        EntityHelpers entity1 = new EntityHelpers();
        entity1.setName("test delete all");
        EntityHelpers entity2 = new EntityHelpers();
        entity2.setName("test delete all");

        EntityTestSearch dto = new EntityTestSearch();
        dto.setName("test delete all");
        GlobalException exception = null;
        try {
            this.dao.deleteAll(dto);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNull(exception);

        assertEquals(Long.valueOf(0), this.dao.count(dto));
    }

    @Test
    void testUpdateProperty() {
        EntityHelpers entity = new EntityHelpers();
        entity.setName("testUpdateProperty");
        entity = this.dao.save(entity);

        assertEquals(1, this.dao.updateProperty(null, "name", "testUpdateProperty1"));

        EntityTestSearch dto = new EntityTestSearch();
        dto.setId(entity.getId().toString());

        assertEquals(1, this.dao.updateProperty(dto, "name", "testUpdateProperty2"));
    }
}
