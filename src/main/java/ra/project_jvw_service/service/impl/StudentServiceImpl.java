package ra.project_jvw_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ra.project_jvw_service.model.dto.request.StudentRequest;
import ra.project_jvw_service.model.dto.response.StudentResponse;
import ra.project_jvw_service.model.entity.Student;
import ra.project_jvw_service.model.entity.User;
import ra.project_jvw_service.repository.StudentRepository;
import ra.project_jvw_service.repository.UserRepository;
import ra.project_jvw_service.service.StudentService;
import ra.project_jvw_service.utils.Role;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<StudentResponse> getAllStudents(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        List<Student> students;

        if (user.getRole() == Role.MENTOR) {
            students = studentRepository.findAllAssignedToMentor(user.getUserId());
        } else if (user.getRole() == Role.ADMIN) {
            students = studentRepository.findAll();
        } else {
            throw new RuntimeException("Bạn không có quyền xem danh sách sinh viên");
        }

        return students.stream().map(this::toDTO).toList();
    }

    @Override
    public StudentResponse getStudentById(Long id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        Student student = studentRepository.findById(id).orElseThrow();
        if (user.getRole() == Role.STUDENT && !student.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Bạn chỉ có thể xem thông tin của chính mình");
        }
        return  toDTO(student);
    }

    @Override
    public StudentResponse createStudent(StudentRequest dto) {
        User user = userRepository.findById(Long.valueOf(dto.getUserId()))
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        if (user.getRole() != Role.STUDENT) {
            throw new IllegalArgumentException("User không có role STUDENT");
        }

        if (studentRepository.existsByUserId(dto.getUserId())) {
            throw new IllegalArgumentException("User đã có thông tin sinh viên");
        }
        if (studentRepository.existsByStudentCode(dto.getStudentCode())) {
            throw new IllegalArgumentException("Mã sinh viên đã tồn tại");
        }
        Student student = Student.builder()
                .studentCode(dto.getStudentCode())
                .major(dto.getMajor())
                .dateOfBirth(dto.getDateOfBirth())
                .address(dto.getAddress())
                .user(user)
                .build();

        Student savedStudent = studentRepository.save(student);
        return toDTO(savedStudent);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest dto, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        Student student = studentRepository.findById(id).orElseThrow();

        if (user.getRole() == Role.STUDENT && !student.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Bạn chỉ được phép cập nhật thông tin của mình");
        }

        student.setStudentCode(dto.getStudentCode());
        student.setMajor(dto.getMajor());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setAddress(dto.getAddress());

        return toDTO(studentRepository.save(student));
    }


    private StudentResponse toDTO(Student student) {
        StudentResponse response = new StudentResponse();
        response.setStudentId(student.getStudentId());
        response.setStudentCode(student.getStudentCode());
        response.setMajor(student.getMajor());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setAddress(student.getAddress());

        User user = student.getUser();
        if (user != null) {
            response.setFullName(user.getFullName());
            response.setEmail(user.getEmail());
            response.setPhoneNumber(user.getPhoneNumber());
        }

        return response;
    }
}
