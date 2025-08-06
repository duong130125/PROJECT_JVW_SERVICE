package ra.project_jvw_service.service;

import ra.project_jvw_service.model.dto.request.AssessmentResultRequest;
import ra.project_jvw_service.model.dto.request.AssessmentResultUpdateRequest;
import ra.project_jvw_service.model.dto.response.AssessmentResultResponse;
import ra.project_jvw_service.model.entity.User;

import java.util.List;

public interface AssessmentResultService {
    List<AssessmentResultResponse> getAllAssessmentResult(Long assignmentId, User currentUser);

    AssessmentResultResponse createAssessmentResult(AssessmentResultRequest request, User currentUser);

    AssessmentResultResponse updateAssessmentResult(Long id, AssessmentResultUpdateRequest request, User currentUser);
}