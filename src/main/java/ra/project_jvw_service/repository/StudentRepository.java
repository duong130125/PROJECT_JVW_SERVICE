package ra.project_jvw_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_jvw_service.model.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByMentorId(Integer mentorId);
    boolean existsByStudentCode(String studentCode);
    boolean existsByUserId(Integer userId);
}
