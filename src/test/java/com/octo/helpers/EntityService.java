package com.octo.helpers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.utils.predicate.filter.QueryFilter;

@Service
@Transactional
public class EntityService {

    @Autowired
    IDAO<EntityHelpers, QueryFilter> dao;

    public EntityHelpers save() {
        EntityHelpers entity = new EntityHelpers();
        return dao.save(entity);
    }

    public EntityHelpers save(String name) {
        EntityHelpers entity = new EntityHelpers();
        entity.setName(name);
        return dao.save(entity);
    }

    public EntityHelpers loadEntityByIdWithLock(final Object id) {
        return dao.loadEntityByIdWithLock(id);
    }

    public List<EntityHelpers> findWithLock() {
        return dao.findWithLock(null);
    }

    public Optional<EntityHelpers> loadWithLock() {
        return dao.loadWithLock(new QueryFilter());
    }

    public EntityHelpers loadEntityByIdWithLock(final Object id, final String fieldName) {
        return dao.loadEntityByIdWithLock(id, fieldName);
    }
}
