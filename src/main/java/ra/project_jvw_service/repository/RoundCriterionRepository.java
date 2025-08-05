package ra.project_jvw_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_jvw_service.model.entity.RoundCriterion;

import java.util.List;

public interface RoundCriterionRepository extends JpaRepository<RoundCriterion, Integer> {
    List<RoundCriterion> findAllByRound_RoundId(Integer roundId);
}