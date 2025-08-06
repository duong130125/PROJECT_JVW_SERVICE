package ra.project_jvw_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_jvw_service.model.entity.InternshipAssignment;

import java.util.List;
import java.util.Optional;

public interface InternshipAssignmentRepository extends JpaRepository<InternshipAssignment, Long> {
    List<InternshipAssignment> findAllByMentor_UserId(Long mentorId);
    List<InternshipAssignment> findAllByStudent_UserId(Long studentId);
    List<InternshipAssignment> findAllByPhase_PhaseId(Long phaseId);
    List<InternshipAssignment> findAllByMentor_UserIdAndPhase_PhaseId(Long mentorId, Long phaseId);
    List<InternshipAssignment> findAllByStudent_UserIdAndPhase_PhaseId(Long studentId, Long phaseId);
    Optional<InternshipAssignment> findByIdAndMentor_UserId(Long id, Long mentorId);
    Optional<InternshipAssignment> findByIdAndStudent_UserId(Long id, Long studentId);
    boolean existsByStudent_StudentIdAndPhase_PhaseId(Integer studentId, Integer phaseId);
}
