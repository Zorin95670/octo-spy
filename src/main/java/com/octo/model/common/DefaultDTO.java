package com.octo.model.common;

import java.util.Collections;
import java.util.List;

import com.octo.utils.json.ToJsonMapper;
import com.octo.utils.predicate.filter.IPredicateFilter;
import com.octo.utils.predicate.filter.IQueryFilter;

/**
 * Default DTO with generic method.
 *
 * @author Vincent Moittié
 *
 */
public abstract class DefaultDTO implements IQueryFilter {

    @Override
    public final String toString() {
        return new ToJsonMapper<>(false).apply(this);
    }

    /**
     * Override this if you need to add more filters.
     */
    @Override
    public <T> List<IPredicateFilter> getFilters(final Class<T> entity) {
        return Collections.emptyList();
    }

    /**
     * Override it to use specific filter.
     */
    @Override
    public boolean isSpecificFilter(final String name) {
        return false;
    }

    /**
     * Override it to use specific filter.
     */
    @Override
    public IPredicateFilter getSpecificFilter(final String name, final String value) {
        return null;
    }
}
