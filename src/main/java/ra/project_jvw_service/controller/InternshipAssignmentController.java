package ra.project_jvw_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.project_jvw_service.model.dto.request.InternshipAssignmentRequest;
import ra.project_jvw_service.model.dto.request.InternshipAssignmentStatusUpdateRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.InternshipAssignmentResponse;
import ra.project_jvw_service.model.entity.User;
import ra.project_jvw_service.repository.UserRepository;
import ra.project_jvw_service.service.InternshipAssignmentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/internship_assignments")
public class InternshipAssignmentController {
    @Autowired
    private InternshipAssignmentService assignmentService;

    @Autowired
    private UserRepository userRepository;

    // Lấy danh sách phân công thực tập (lọc theo role + phaseId)
    @GetMapping
    public ResponseEntity<APIResponse<List<InternshipAssignmentResponse>>> getAll(
            @RequestParam(required = false) Long phaseId,
            Authentication authentication) {
        User currentUser = userRepository.findByUsername(authentication.getName())
                .filter(User::getIsActive)
                .orElseThrow(() -> new RuntimeException("Tài khoản không hợp lệ hoặc bị khóa"));

        return new ResponseEntity<>(new APIResponse<>(true, "Lấy danh sách phân công thực tập thành công",
            assignmentService.getAllInternshipAssignment(phaseId, currentUser), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    // Lấy chi tiết một phân công thực tập theo ID (lọc theo quyền và user_id)
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<InternshipAssignmentResponse>> getById(
            @PathVariable Long id,
            Authentication authentication) {
        User currentUser = userRepository.findByUsername(authentication.getName())
                .filter(User::getIsActive)
                .orElseThrow(() -> new RuntimeException("Tài khoản không hợp lệ hoặc bị khóa"));

        return new ResponseEntity<>(new APIResponse<>(true, "Lấy chi tiết một phân công thực tập theo ID",
            assignmentService.getByIdInternshipAssignment(id, currentUser), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Tạo phân công thực tập mới (gán sinh viên cho giáo viên trong giai đoạn)
    @PostMapping
    public ResponseEntity<APIResponse<InternshipAssignmentResponse>> create(
            @RequestBody @Valid InternshipAssignmentRequest request) {
        return new ResponseEntity<>(new APIResponse<>(true, "Tạo phân công thực tập mới thành công",
            assignmentService.createInternshipAssignment(request), HttpStatus.CREATED, null, LocalDateTime.now()), HttpStatus.CREATED);
    }

    //Cập nhật trạng thái của phân công thực tập (PENDING, IN_PROGRESS, ...)
    @PutMapping("/{id}/status")
    public ResponseEntity<APIResponse<InternshipAssignmentResponse>> updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid InternshipAssignmentStatusUpdateRequest request) {

        return new ResponseEntity<>(new APIResponse<>(true, "Cập nhật trạng thái của phân công thực tập thành công",
            assignmentService.updateStatusInternshipAssignment(id, request), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

}
