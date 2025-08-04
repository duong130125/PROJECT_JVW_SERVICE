package ra.project_jvw_service.service;

import org.springframework.security.core.Authentication;
import ra.project_jvw_service.model.dto.request.MentorRequest;
import ra.project_jvw_service.model.dto.response.MentorResponse;

import java.util.List;

public interface MentorService {
    List<MentorResponse> getAllMentors(Authentication auth);
    MentorResponse getMentorById(Long id, Authentication auth);
    MentorResponse createMentor(MentorRequest request);
    MentorResponse updateMentor(Long id, MentorRequest request, Authentication auth);
}