package ra.project_jvw_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_jvw_service.model.entity.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
}
