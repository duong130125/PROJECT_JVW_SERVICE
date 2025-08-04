package ra.project_jvw_service.service;

import ra.project_jvw_service.model.dto.request.InternshipPhaseRequest;
import ra.project_jvw_service.model.dto.response.InternshipPhaseResponse;

import java.util.List;

public interface InternshipPhaseService {
    List<InternshipPhaseResponse> getAllPhases();
    InternshipPhaseResponse getPhaseById(Long id);
    InternshipPhaseResponse createPhase(InternshipPhaseRequest dto);
    InternshipPhaseResponse updatePhase(Long id, InternshipPhaseRequest dto);
    void deletePhase(Long id);
}
