package ra.project_jvw_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_jvw_service.model.entity.InternshipPhase;

public interface InternshipPhaseRepository extends JpaRepository<InternshipPhase, Long> {
    boolean existsByPhaseNameIgnoreCase(String phaseName);
}