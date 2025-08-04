package ra.project_jvw_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.project_jvw_service.model.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT ia.student FROM InternshipAssignment ia WHERE ia.mentor.mentorId = :mentorId")
    List<Student> findAllAssignedToMentor(@Param("mentorId") Integer mentorId);

    boolean existsByStudentCode(String studentCode);
    boolean existsByUserId(Integer userId);
}
