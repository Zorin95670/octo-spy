package com.octo.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.model.common.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;
import java.util.Optional;

/**
 * Class to manage query option for controller.
 *
 * @author Vincent Moittié
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class QueryFilter {

    /**
     * The current page.
     */
    @QueryParam("page")
    @Min(0)
    private Integer page = 0;

    /**
     * The maximum size of resources.
     */
    @QueryParam("count")
    @Min(1)
    @Max(Constants.MAXIMUM_RESOURCE_SIZE)
    private Integer count = Constants.DEFAULT_RESOURCE_SIZE;

    /**
     * Name of field to sort.
     */
    @QueryParam("order")
    private String order;

    /**
     * Type of sort. Can be 'asc' or 'desc'.
     */
    @QueryParam("sort")
    @Pattern(regexp = "asc|desc")
    private String sort;

    private Sort getOrderBy() {
        if (sort == null) {
            return null;
        }

        if ("asc".equalsIgnoreCase(sort)) {
            return Sort.by(Sort.Direction.ASC, order);
        }
        return Sort.by(Sort.Direction.DESC, order);
    }

    /**
     * Create pagination from query parameters.
     *
     * @return Page options.
     */
    public final Pageable getPagination() {
        Sort sortOption = getOrderBy();
        int pageNumber = Optional.ofNullable(this.page).orElse(0);
        int countNumber = Optional.ofNullable(this.count).orElse(Constants.DEFAULT_RESOURCE_SIZE);

        if (sortOption != null) {
            return PageRequest.of(pageNumber, countNumber, sortOption);
        }
        return PageRequest.of(pageNumber, countNumber);
    }
}
