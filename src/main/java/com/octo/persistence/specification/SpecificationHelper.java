package com.octo.persistence.specification;

import com.octo.persistence.specification.filter.BooleanPredicateFilter;
import com.octo.persistence.specification.filter.DatePredicateFilter;
import com.octo.persistence.specification.filter.FilterType;
import com.octo.persistence.specification.filter.FilterType.Type;
import com.octo.persistence.specification.filter.IPredicateFilter;
import com.octo.persistence.specification.filter.NumberPredicateFilter;
import com.octo.persistence.specification.filter.TextPredicateFilter;
import com.octo.persistence.specification.filter.TokenPredicateFilter;
import com.octo.utils.reflect.FieldUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generate predicates from a map of filters.
 *
 * @param <T> Entity class.
 */
public class SpecificationHelper<T> implements Specification<T> {

    /**
     * Filter's map.
     */
    @Setter
    private Map<String, String> filters;

    /**
     * Entity class.
     */
    @Getter
    @Setter
    private Class<T> entityClass;

    /**
     * Construct specification.
     *
     * @param entityClass Entity class.
     * @param filters Filter's map..
     */
    public SpecificationHelper(final Class<T> entityClass, final Map<String, String> filters) {
        setEntityClass(entityClass);
        setFilters(filters);
    }


    @Override
    public final Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
                                       final CriteriaBuilder builder) {
        return builder.and(this.getFilters().stream()
                .map(filter -> filter.getPredicate(builder, root, query))
                .toArray(Predicate[]::new));
    }

    final List<IPredicateFilter> getFilters() {
        final List<IPredicateFilter> filterList = new ArrayList<>();
        final List<Field> fields = FieldUtils.getFields(this.getEntityClass());

        fields.stream().filter(field -> field.isAnnotationPresent(FilterType.class)).forEach(field -> {
            final String name = field.getName();
            if (!this.filters.containsKey(name)) {
                return;
            }
            final String value = this.filters.get(name);
            final FilterType filterType = field.getAnnotation(FilterType.class);

            IPredicateFilter filter;
            if (Type.DATE.equals(filterType.type())) {
                filter = new DatePredicateFilter(name, value);
            } else if (Type.NUMBER.equals(filterType.type())) {
                filter = new NumberPredicateFilter(name, value);
            } else if (Type.BOOLEAN.equals(filterType.type())) {
                filter = new BooleanPredicateFilter(name, value);
            } else if (Type.TOKEN.equals(filterType.type())) {
                filter = new TokenPredicateFilter(name, value);
            } else {
                filter = new TextPredicateFilter(name, value);
            }

            if (filter.extract()) {
                filterList.add(filter);
            }
        });

        return filterList;
    }
}
