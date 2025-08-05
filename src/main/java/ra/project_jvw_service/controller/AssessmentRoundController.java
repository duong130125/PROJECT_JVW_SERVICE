package ra.project_jvw_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_jvw_service.model.dto.request.AssessmentRoundRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.AssessmentRoundResponse;
import ra.project_jvw_service.service.AssessmentRoundService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/assessment_rounds")
public class AssessmentRoundController {
    @Autowired
    private AssessmentRoundService assessmentRoundService;

    //Lấy danh sách tất cả các đợt đánh giá (có thể lọc theo phase_id)
    @GetMapping
    public ResponseEntity<APIResponse<List<AssessmentRoundResponse>>> getAll(@RequestParam(required = false) Long phaseId) {
        return new ResponseEntity<>(
            new APIResponse<>(
                true, "Lấy danh sách tất cả các đợt đánh giá", assessmentRoundService.getAll(phaseId), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Lấy thông tin chi tiết một đợt đánh giá theo ID
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<AssessmentRoundResponse>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
            new APIResponse<>(
                true, "Lấy thông tin chi tiết một đợt đánh giá theo ID", assessmentRoundService.getById(id), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Tạo một đợt đánh giá mới (kèm danh sách criterion_id và weight)
    @PostMapping
    public ResponseEntity<APIResponse<AssessmentRoundResponse>> create(@RequestBody @Valid AssessmentRoundRequest dto) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        true, "Tạo một đợt đánh giá mới thành công", assessmentRoundService.create(dto), HttpStatus.CREATED, null, LocalDateTime.now()), HttpStatus.CREATED);
    }

    //Cập nhật thông tin một đợt đánh giá
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<AssessmentRoundResponse>> update(@PathVariable Long id, @RequestBody @Valid AssessmentRoundRequest dto) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        true, "Cập nhật thông tin một đợt đánh giá thành công", assessmentRoundService.update(id, dto), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Xóa một đợt đánh giá theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Long id) {
        assessmentRoundService.delete(id);
        APIResponse<String> response = new APIResponse<>(
                true, "Xóa một đợt đánh giá thành công", null, HttpStatus.OK, null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}