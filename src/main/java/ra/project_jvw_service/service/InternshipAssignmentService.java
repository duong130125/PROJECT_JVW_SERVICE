package ra.project_jvw_service.service;

import ra.project_jvw_service.model.dto.request.InternshipAssignmentRequest;
import ra.project_jvw_service.model.dto.request.InternshipAssignmentStatusUpdateRequest;
import ra.project_jvw_service.model.dto.response.InternshipAssignmentResponse;
import ra.project_jvw_service.model.entity.User;

import java.util.List;

public interface InternshipAssignmentService {
    List<InternshipAssignmentResponse> getAllInternshipAssignment(Long phaseId, User currentUser);
    InternshipAssignmentResponse getByIdInternshipAssignment(Long id, User currentUser);
    InternshipAssignmentResponse createInternshipAssignment(InternshipAssignmentRequest request);
    InternshipAssignmentResponse updateStatusInternshipAssignment(Long id, InternshipAssignmentStatusUpdateRequest request);
}
