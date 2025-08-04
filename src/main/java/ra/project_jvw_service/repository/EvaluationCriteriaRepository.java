package ra.project_jvw_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_jvw_service.model.entity.EvaluationCriterion;

public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriterion, Long> {
    boolean existsByCriterionNameIgnoreCase(String criterionName);
}