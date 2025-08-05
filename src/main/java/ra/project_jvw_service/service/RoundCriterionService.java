package ra.project_jvw_service.service;

import ra.project_jvw_service.model.dto.request.RoundCriterionRequest;
import ra.project_jvw_service.model.dto.response.RoundCriterionResponse;

import java.util.List;

public interface RoundCriterionService {
    List<RoundCriterionResponse> getAllRoundCriterion(Integer roundId);
    RoundCriterionResponse getByIdRoundCriterion(Integer id);
    RoundCriterionResponse createRoundCriterion(RoundCriterionRequest dto);
    RoundCriterionResponse updateRoundCriterion(Integer id, RoundCriterionRequest dto);
    void deleteRoundCriterion(Integer id);
}