package ra.project_jvw_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.project_jvw_service.model.dto.request.AssessmentResultRequest;
import ra.project_jvw_service.model.dto.request.AssessmentResultUpdateRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.AssessmentResultResponse;
import ra.project_jvw_service.model.entity.User;
import ra.project_jvw_service.repository.UserRepository;
import ra.project_jvw_service.service.AssessmentResultService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/assessment_results")
@RequiredArgsConstructor
public class AssessmentResultController {

    private final AssessmentResultService assessmentResultService;
    private final UserRepository userRepository;

    // GET: ADMIN, MENTOR, STUDENT - Lấy kết quả đánh giá (tùy theo quyền)
    @GetMapping
    public ResponseEntity<APIResponse<List<AssessmentResultResponse>>> getAll(
            @RequestParam(required = false) Long assignmentId,
            Authentication authentication
    ) {
        User currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        return new ResponseEntity<>(
            new APIResponse<>(true, "Lấy danh sách kết quả đánh giá chi tiết", assessmentResultService.getAllAssessmentResult(assignmentId, currentUser),
                HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    // POST: MENTOR - Tạo kết quả đánh giá mới
    @PostMapping
    public ResponseEntity<APIResponse<AssessmentResultResponse>> create(
            @RequestBody @Valid AssessmentResultRequest request,
            Authentication authentication
    ) {
        User currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        return new ResponseEntity<>(
            new APIResponse<>(true, "Tạo/Thêm kết quả đánh giá chi tiết cho sinh viên thành công", assessmentResultService.createAssessmentResult(request, currentUser),
                HttpStatus.CREATED, null, LocalDateTime.now()), HttpStatus.CREATED);
    }

    // PUT: MENTOR - Cập nhật kết quả đánh giá đã tạo
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<AssessmentResultResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid AssessmentResultUpdateRequest request,
            Authentication authentication
    ) {
        User currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        return new ResponseEntity<>(
            new APIResponse<>(true, "Cập nhật kết quả đánh giá chi tiết thành công", assessmentResultService.updateAssessmentResult(id, request, currentUser),
                HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }
}
