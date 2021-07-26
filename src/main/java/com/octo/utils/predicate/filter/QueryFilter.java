package com.octo.utils.predicate.filter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import com.octo.model.common.DefaultDTO;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.filter.FilterType.Type;
import com.octo.utils.reflect.FieldUtils;

/**
 * Class to manage query option for controller.
 */
public class QueryFilter extends DefaultDTO {

    /**
     * The current page.
     */
    @QueryParam("page")
    private int page;

    /**
     * The maximum size of resources.
     */
    @QueryParam("count")
    private int count;

    /**
     * Name of field to sort.
     */
    @QueryParam("order")
    private String order;

    /**
     * Type of sort. Can be 'asc' or 'desc'.
     */
    @QueryParam("sort")
    private String sort;

    /**
     * Get current page.
     *
     * @return Current page.
     */
    public int getPage() {
        return this.page;
    }

    /**
     * Set current page.
     *
     * @param page
     *            Current page.
     */
    public void setPage(final int page) {
        this.page = page;
    }

    /**
     * Get maximum size of resources.
     *
     * @return Count.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Set maximum size of resources.
     *
     * @param count
     *            Maximum size of resources.
     */
    public void setCount(final int count) {
        this.count = count;
    }

    /**
     * Get name of field that must be ordering.
     *
     * @return Name of field.
     */
    public String getOrder() {
        return this.order;
    }

    /**
     * Set name of field that must be ordering.
     *
     * @param order
     *            Name of field.
     */
    public void setOrder(final String order) {
        this.order = order;
    }

    /**
     * Get type of sort for field's order.
     *
     * @return Type of sort.
     */
    public String getSort() {
        return this.sort;
    }

    /**
     * Set type of sort for field's order.
     *
     * @param sort
     *            Type of sort.
     */
    public void setSort(final String sort) {
        this.sort = sort;
    }

    /**
     * Verify and set valid value for page and count.
     *
     * @param defaultApiCount
     *            Default value for API max result size.
     * @param maximumApiCount
     *            Maximum value for API max result size.
     */
    public void validate(final int defaultApiCount, final int maximumApiCount) {
        if (this.getPage() < 0) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "page", Integer.toString(this.getPage()));
        }
        if (this.getCount() < 0) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "count", Integer.toString(this.getPage()));
        }

        if (this.getCount() == 0) {
            this.setCount(defaultApiCount);
        } else if (this.getCount() > maximumApiCount) {
            this.setCount(maximumApiCount);
        }
    }

    /**
     * Override this if you need to add more filters.
     */
    @Override
    public <T> List<IPredicateFilter> getFilters(final Class<T> entity) {
        final List<IPredicateFilter> filters = new ArrayList<>();
        final List<Field> fields = FieldUtils.getFields(this.getClass());

        fields.stream().filter(field -> field.isAnnotationPresent(FilterType.class)).forEach(field -> {
            final String name = field.getName();
            final String value = this.getFieldValue(field);
            final FilterType filterType = field.getAnnotation(FilterType.class);

            IPredicateFilter filter;
            if (isSpecificFilter(name)) {
                filter = getSpecificFilter(name, value);
            } else if (Type.DATE.equals(filterType.type())) {
                filter = new DatePredicateFilter(name, value);
            } else if (Type.NUMBER.equals(filterType.type())) {
                filter = new NumberPredicateFilter(name, value);
            } else if (Type.BOOLEAN.equals(filterType.type())) {
                filter = new BooleanPredicateFilter(name, value);
            } else if (Type.ARRAY.equals(filterType.type())) {
                filter = new ArrayPredicateFilter(name, value);
            } else {
                filter = new TextPredicateFilter(name, value);
            }

            if (filter.extract()) {
                filters.add(filter);
            }
        });

        return filters;
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
