package com.octo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.octo.dao.IDAO;
import com.octo.model.dto.count.MultipleCountDTO;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.PredicateMapper;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.utils.reflect.ClassHasFieldPredicate;

/**
 * Count service.
 *
 * @author Vincent Moittié
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MultipleCountService {

    /**
     * Count DAO.
     */
    @Autowired
    private IDAO<Object[], MultipleCountDTO> multipleCountDAO;
    /**
     * String used to identify exception field.
     */
    private static final String EXCEPTION_FIELD = "field";

    /**
     * Count object in database.
     *
     * @param <T>
     *            Class of entity.
     * @param <Y>
     *            Class of search DTO.
     * @param clazz
     *            Class of entity.
     * @param dto
     *            Count DTO.
     * @param searchDTO
     *            Search DTO.
     * @return List of counted object and their count.
     */
    public <T, Y extends QueryFilter> JsonNode count(final Class<T> clazz, final MultipleCountDTO dto,
            final Y searchDTO) {
        if (CollectionUtils.isEmpty(dto.getFields())) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, EXCEPTION_FIELD);
        }

        if (dto.getFields().stream().anyMatch(java.util.function.Predicate.not(new ClassHasFieldPredicate(clazz)))) {
            throw new GlobalException(ErrorType.UNKNOW_FIELD, EXCEPTION_FIELD);
        }

        return this.execute(clazz, dto, searchDTO);
    }

    /**
     * Execute count request.
     *
     * @param <Z>
     *            Entity to count.
     * @param <Y>
     *            Class of search DTO.
     * @param clazz
     *            Class of entity
     * @param dto
     *            Count DTO.
     * @param searchDTO
     *            Search DTO.
     * @return List of count request result.
     */
    public final <Z, Y extends QueryFilter> ArrayNode execute(final Class<Z> clazz, final MultipleCountDTO dto,
            final Y searchDTO) {
        final CriteriaBuilder builder = this.multipleCountDAO.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        final Root<Z> root = criteria.from(clazz);

        List<Selection<?>> selections = new ArrayList<>();
        dto.getFields().stream().map(root::get).forEach(selections::add);
        selections.add(builder.count(root));
        criteria.multiselect(selections);

        List<Predicate> predicates = new ArrayList<>(
                Arrays.asList(new PredicateMapper<>(clazz, searchDTO).apply(builder, root)));

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));

        List<Expression<?>> groupBy = new ArrayList<>();
        dto.getFields().stream().map(root::get).forEach(groupBy::add);
        selections.add(builder.count(root).alias("count"));
        criteria.groupBy(groupBy);

        final TypedQuery<Object[]> query = this.multipleCountDAO.getEntityManager().createQuery(criteria);

        final ArrayNode list = JsonNodeFactory.instance.arrayNode();

        query.getResultStream().map(objects -> {
            ObjectNode json = JsonNodeFactory.instance.objectNode();

            IntStream.range(0, dto.getFields().size())
                    .forEach(index -> json.put(dto.getFields().get(index), objects[index].toString()));
            json.put("count", objects[dto.getFields().size()].toString());
            return json;
        }).forEach(list::add);

        return list;
    }
}
