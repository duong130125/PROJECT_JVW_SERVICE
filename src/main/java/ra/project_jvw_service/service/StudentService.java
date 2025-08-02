package ra.project_jvw_service.service;

import org.springframework.security.core.Authentication;
import ra.project_jvw_service.model.dto.request.StudentRequest;
import ra.project_jvw_service.model.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents(Authentication authentication);
    StudentResponse getStudentById(Long id, Authentication authentication);
    StudentResponse createStudent(StudentRequest dto);
    StudentResponse updateStudent(Long id, StudentRequest dto, Authentication authentication);
}
