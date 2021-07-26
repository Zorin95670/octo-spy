package com.octo.models.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Resource to store list of resources and total of this.
 *
 * @param <T>
 *            Resource's type.
 */
public class Resource<T> {

    /**
     * Partial content HTTP status.
     */
    private static final int STATUS_PARTIAL_CONTENT = 206;
    /**
     * Ok HTTP status.
     */
    private static final int STATUS_OK = 200;

    /**
     * Total of resources.
     */
    private Long total;
    /**
     * The current page.
     */
    private int page;

    /**
     * The maximum size of resources.
     */
    private int count;
    /**
     * List of resources.
     */
    private List<T> resources;

    /**
     * Initialize with total and resources list.
     *
     * @param total
     *            Total of resources.
     * @param resources
     *            List of resources.
     * @param page
     *            Index of page to set.
     * @param count
     *            Maximum of object to return.
     */
    public Resource(final Long total, final List<T> resources, final int page, final int count) {
        this.setTotal(total);
        this.setResources(resources);
        this.setPage(page);
        this.setCount(count);
    }

    /**
     * Get total of resource. Total is independent of size of list. It assume that this total is equals or superior of
     * list's size.
     *
     * @return Total of resource.
     */
    public Long getTotal() {
        return this.total;
    }

    /**
     * Set total of list.
     *
     * @param total
     *            Number.
     */
    public void setTotal(final Long total) {
        this.total = total;
    }

    /**
     * Get resource's list.
     *
     * @return Ressource's list.
     */
    public List<T> getResources() {
        return this.resources;
    }

    /**
     * Set resource's list.
     *
     * @param resources
     *            Resource's list.
     */
    public void setResources(final List<T> resources) {
        this.resources = resources;
    }

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
     * Get associated status.
     *
     * @return 200 or 206.
     */
    @JsonIgnore
    public int getStatus() {
        if (this.total > this.getResources().size()) {
            return STATUS_PARTIAL_CONTENT;
        }
        return STATUS_OK;
    }
}
