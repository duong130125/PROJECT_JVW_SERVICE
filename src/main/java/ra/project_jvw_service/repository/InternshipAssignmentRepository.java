package ra.project_jvw_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_jvw_service.model.entity.InternshipAssignment;

import java.util.List;
import java.util.Optional;

public interface InternshipAssignmentRepository extends JpaRepository<InternshipAssignment, Long> {

    List<InternshipAssignment> findAllByMentor_User_UserId(Long userId);
    List<InternshipAssignment> findAllByStudent_User_UserId(Long studentId);
    List<InternshipAssignment> findAllByPhase_PhaseId(Long phaseId);
    List<InternshipAssignment> findAllByMentor_User_UserIdAndPhase_PhaseId(Long mentorUserId, Long phaseId);
    List<InternshipAssignment> findAllByStudent_User_UserIdAndPhase_PhaseId(Long studentUserId, Long phaseId);
    Optional<InternshipAssignment> findByAssignmentIdAndMentor_User_UserId(Long id, Long mentorId);
    Optional<InternshipAssignment> findByAssignmentIdAndStudent_User_UserId(Long assignmentId, Long userId);
    boolean existsByStudent_StudentIdAndPhase_PhaseId(Integer studentId, Integer phaseId);
}
