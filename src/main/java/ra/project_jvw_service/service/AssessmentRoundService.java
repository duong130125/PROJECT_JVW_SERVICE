package ra.project_jvw_service.service;

import ra.project_jvw_service.model.dto.request.AssessmentRoundRequest;
import ra.project_jvw_service.model.dto.response.AssessmentRoundResponse;

import java.util.List;

public interface AssessmentRoundService {
    List<AssessmentRoundResponse> getAll(Long phaseId);
    AssessmentRoundResponse getById(Long id);
    AssessmentRoundResponse create(AssessmentRoundRequest dto);
    AssessmentRoundResponse update(Long id, AssessmentRoundRequest dto);
    void delete(Long id);
}