package com.octo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.DeploymentReportView;
import com.octo.persistence.repository.DeploymentReportViewRepository;
import com.octo.persistence.specification.SpecificationHelper;
import com.octo.utils.reflect.ClassHasFieldPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Implementation of deployment service.
 */
@Service
@Transactional
public class DeploymentReportViewServiceImpl implements DeploymentReportViewService {

    /**
     * Deployment view's repository.
     */
    @Autowired
    private DeploymentReportViewRepository deploymentReportViewRepository;

    @Override
    public JsonNode count(final Map<String, String> filters, final List<String> fields) {
        var specification = new SpecificationHelper<>(DeploymentReportView.class, filters);

        fields.stream().filter(Predicate.not(new ClassHasFieldPredicate(DeploymentReportView.class))).forEach(field -> {
            throw new GlobalException(ErrorType.UNKNOWN_FIELD, "field", field);
        });

        return deploymentReportViewRepository.count(DeploymentReportView.class, specification, fields);
    }
}
