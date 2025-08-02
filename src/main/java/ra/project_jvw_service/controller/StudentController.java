package ra.project_jvw_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.project_jvw_service.model.dto.request.StudentRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.StudentResponse;
import ra.project_jvw_service.service.StudentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    //Lấy danh sách tất cả sinh viên (MENTOR chỉ xem sinh viên được phân công)
    @GetMapping
    public ResponseEntity<APIResponse<List<StudentResponse>>> getAllStudents(Authentication authentication) {
        return new ResponseEntity<>(
            new APIResponse<>(true, "Lấy danh sách tất cả sinh viên", studentService.getAllStudents(authentication), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Lấy thông tin chi tiết một sinh viên theo ID (STUDENT chỉ xem của mình)
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<StudentResponse>> getStudentById(@PathVariable Long id, Authentication authentication) {
        return new ResponseEntity<>(
            new APIResponse<>(true, "Lấy thông tin chi tiết một sinh viên theo ID", studentService.getStudentById(id, authentication), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Tạo thông tin sinh viên mới (liên kết với User có role STUDENT)
    @PostMapping
    public ResponseEntity<APIResponse<StudentResponse>> createStudent(@RequestBody @Valid StudentRequest dto) {
        return new ResponseEntity<>(
            new APIResponse<>(true, "Tạo thông tin sinh viên mới thành công", studentService.createStudent(dto), HttpStatus.CREATED, null, LocalDateTime.now()), HttpStatus.CREATED);
    }

    //Cập nhật thông tin chi tiết sinh viên (STUDENT chỉ cập nhật của mình)
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<StudentResponse>> updateStudent(@PathVariable Long id,
                                                                         @RequestBody @Valid StudentRequest dto,
                                                                         Authentication authentication) {
        return new ResponseEntity<>(
            new APIResponse<>(true, "Cập nhật thông tin chi tiết sinh viên", studentService.updateStudent(id, dto, authentication), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }
}
