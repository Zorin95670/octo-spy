package com.octo.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.octo.dao.IDAO;
import com.octo.models.dto.count.CountDTO;
import com.octo.models.error.ErrorType;
import com.octo.models.error.GlobalException;
import com.octo.utils.json.ObjectArrayToJsonConsumer;
import com.octo.utils.predicate.PredicateMapper;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.utils.reflect.ClassHasFieldPredicate;
import com.octo.utils.reflect.FieldUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Count service.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CountService {

    /**
     * Count DAO.
     */
    @Autowired
    private IDAO<Object[], CountDTO> countDAO;
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
    public <T, Y extends QueryFilter> JsonNode count(final Class<T> clazz, final CountDTO dto, final Y searchDTO) {
        if (StringUtils.isEmpty(dto.getField())) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, EXCEPTION_FIELD);
        }

        if (!new ClassHasFieldPredicate(clazz).test(dto.getField())) {
            throw new GlobalException(ErrorType.UNKNOW_FIELD, EXCEPTION_FIELD, dto.getField());
        }

        final List<Object[]> list = this.execute(clazz, dto, searchDTO);

        final ObjectNode json = this.initializeJson(dto.getValue());

        list.stream().forEach(new ObjectArrayToJsonConsumer(json));

        return json;
    }

    /**
     * Initialize json with all value as json attribute and zero as json value.
     *
     * @param values
     *            Values, can be split by ','.
     * @return Initialize json.
     */
    public ObjectNode initializeJson(final String values) {
        final ObjectNode json = JsonNodeFactory.instance.objectNode();

        if (StringUtils.isNotBlank(values)) {
            Arrays.stream(values.split(",")).forEach(value -> json.put(value, 0));
        }

        return json;
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
    public final <Z, Y extends QueryFilter> List<Object[]> execute(final Class<Z> clazz, final CountDTO dto,
            final Y searchDTO) {
        final CriteriaBuilder builder = this.countDAO.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        final Root<Z> root = criteria.from(clazz);

        criteria.multiselect(root.get(dto.getField()), builder.count(root.get(dto.getField())));

        List<Predicate> predicates = new ArrayList<>(
                Arrays.asList(new PredicateMapper<>(clazz, searchDTO).apply(builder, root)));

        this.applyWhereClause(clazz, builder, criteria, root, dto, predicates);

        criteria.groupBy(root.get(dto.getField()));

        final TypedQuery<Object[]> query = this.countDAO.getEntityManager().createQuery(criteria);

        final List<Object[]> list = new ArrayList<>();

        query.getResultStream().forEach(list::add);

        return list;
    }

    /**
     * Add predicate to where clause, if the DTO value is not empty.
     *
     * @param clazz
     *            Class to apply where cause.
     * @param builder
     *            CriteriaBuilder.
     * @param criteria
     *            CriteriaQuery.
     * @param root
     *            Root.
     * @param dto
     *            DTO.
     * @param predicates
     *            Predicates to add.
     * @return If predicates is modify return true, otherwise false.
     */
    public boolean applyWhereClause(final Class<?> clazz, final CriteriaBuilder builder,
            final CriteriaQuery<Object[]> criteria, final Root<?> root, final CountDTO dto,
            final List<Predicate> predicates) {
        if (StringUtils.isBlank(dto.getValue())) {
            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
            return false;
        }

        if (!dto.getValue().contains(",")) {
            predicates.add(builder.equal(root.get(dto.getField()),
                    this.convertValueToObject(clazz, dto.getField(), dto.getValue())));
        } else {
            predicates.add(root.get(dto.getField()).in(Arrays.stream(dto.getValue().split(","))
                    .map(v -> this.convertValueToObject(clazz, dto.getField(), v)).collect(Collectors.toList())));
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));

        return true;
    }

    /**
     * Convert String value to object. If field type in class is a boolean,convert value in boolean then in object.
     *
     * @param clazz
     *            Class to check type of field.
     * @param fieldName
     *            Name of field.
     * @param value
     *            Value to convert.
     * @return Converted value.
     */
    public Object convertValueToObject(final Class<?> clazz, final String fieldName, final String value) {
        Object convertedValue = null;
        final Field field = FieldUtils.getField(clazz, fieldName);

        if (field == null) {
            throw new GlobalException(ErrorType.WRONG_FIELD, EXCEPTION_FIELD, fieldName);
        }

        if (value == null) {
            return null;
        }

        if (field.getType().equals(boolean.class)) {
            convertedValue = BooleanUtils.toBooleanObject(value);
        } else {
            convertedValue = value;
        }

        if (convertedValue == null) {
            throw new GlobalException(ErrorType.WRONG_FIELD, "value", value);
        }

        return convertedValue;
    }
}
