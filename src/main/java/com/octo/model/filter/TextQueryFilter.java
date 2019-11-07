package com.octo.model.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;

/**
 * Class to extract text filter from the query.
 */
public class TextQueryFilter extends QueryFilter<String> {

    /**
     * Create text filter with field name and default type filter as "text".
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     */
    public TextQueryFilter(final String name, final String value) {
        super(name, value, "text");
    }

    @Override
    public final Predicate getPredicate(final CriteriaBuilder builder, final Expression<String> field) {
        return builder.like(builder.upper(field), StringUtils.upperCase(this.getValue()));
    }
}
