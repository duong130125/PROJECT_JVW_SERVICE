package ra.project_jvw_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_jvw_service.model.dto.request.EvaluationCriteriaRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.EvaluationCriteriaResponse;
import ra.project_jvw_service.service.EvaluationCriteriaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/evaluation_criteria")
public class EvaluationCriterionController {

    @Autowired
    private EvaluationCriteriaService evaluationCriteriaService;

    //Lấy danh sách tất cả các tiêu chí đánh giá
    @GetMapping
    public ResponseEntity<APIResponse<List<EvaluationCriteriaResponse>>> getAll() {
        return new ResponseEntity<>(
            new APIResponse<>(
        true, "Lấy danh sách tất cả các tiêu chí đánh giá", evaluationCriteriaService.getAllCriteria(), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Lấy thông tin chi tiết một tiêu chí đánh giá theo ID
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<EvaluationCriteriaResponse>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
            new APIResponse<>(
        true, "Lấy thông tin chi tiết một tiêu chí đánh giá theo ID", evaluationCriteriaService.getCriterionById(id), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Tạo mới một tiêu chí đánh giá
    @PostMapping
    public ResponseEntity<APIResponse<EvaluationCriteriaResponse>> create(@RequestBody @Valid EvaluationCriteriaRequest dto) {
        return new ResponseEntity<>(
            new APIResponse<>(
        true, "Tạo mới một tiêu chí đánh giá thành công", evaluationCriteriaService.createCriterion(dto), HttpStatus.CREATED, null, LocalDateTime.now()), HttpStatus.CREATED);
    }

    //Cập nhật thông tin một tiêu chí đánh giá
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<EvaluationCriteriaResponse>> update(@PathVariable Long id, @RequestBody @Valid EvaluationCriteriaRequest dto) {
        return new ResponseEntity<>(
            new APIResponse<>(
        true, "Cập nhật thông tin tiêu chí đánh giá thành công", evaluationCriteriaService.updateCriterion(id, dto), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Xóa một tiêu chí đánh giá theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Long id) {
        evaluationCriteriaService.deleteCriterion(id);
        APIResponse<String> response = new APIResponse<>(
            true, "Xóa tiêu chí đánh giá thành công", null, HttpStatus.OK, null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
