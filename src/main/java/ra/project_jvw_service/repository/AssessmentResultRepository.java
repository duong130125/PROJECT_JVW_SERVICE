package ra.project_jvw_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_jvw_service.model.entity.AssessmentResult;

import java.util.List;
import java.util.Optional;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
    Optional<AssessmentResult> findByAssignment_AssignmentIdAndRound_RoundIdAndCriterion_CriterionId(Integer assignmentId, Integer roundId, Integer criterionId);

    List<AssessmentResult> findAllByAssignment_AssignmentId(Long assignmentId);

    List<AssessmentResult> findAllByAssignment_Mentor_UserId(Long mentorUserId);

    List<AssessmentResult> findAllByAssignment_Student_UserId(Long studentUserId);

    List<AssessmentResult> findAllByEvaluatedBy_UserId(Long userId);
}