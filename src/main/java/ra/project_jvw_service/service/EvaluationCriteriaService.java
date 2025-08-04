package ra.project_jvw_service.service;

import ra.project_jvw_service.model.dto.request.EvaluationCriteriaRequest;
import ra.project_jvw_service.model.dto.response.EvaluationCriteriaResponse;

import java.util.List;

public interface EvaluationCriteriaService {
    List<EvaluationCriteriaResponse> getAllCriteria();
    EvaluationCriteriaResponse getCriterionById(Long id);
    EvaluationCriteriaResponse createCriterion(EvaluationCriteriaRequest dto);
    EvaluationCriteriaResponse updateCriterion(Long id, EvaluationCriteriaRequest dto);
    void deleteCriterion(Long id);
}
