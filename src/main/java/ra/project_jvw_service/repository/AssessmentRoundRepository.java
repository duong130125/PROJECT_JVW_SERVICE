package ra.project_jvw_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_jvw_service.model.entity.AssessmentRound;

import java.util.List;

public interface AssessmentRoundRepository extends JpaRepository<AssessmentRound, Long> {
    List<AssessmentRound> findAllByPhase_PhaseId(Long phase_phaseId);
}
