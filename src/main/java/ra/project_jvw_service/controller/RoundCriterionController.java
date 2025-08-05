package ra.project_jvw_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.project_jvw_service.model.dto.request.RoundCriterionRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.RoundCriterionResponse;
import ra.project_jvw_service.service.RoundCriterionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/round_criteria")
public class RoundCriterionController {
    @Autowired
    private RoundCriterionService roundCriterionService;

    //Lấy danh sách các tiêu chí trong một đợt đánh giá cụ thể
    @GetMapping
    public ResponseEntity<APIResponse<List<RoundCriterionResponse>>> getAll(@RequestParam Integer roundId) {
        return new ResponseEntity<>(
            new APIResponse<>(
                true, "Lấy danh sách các tiêu chí trong một đợt đánh giá cụ thể",
                    roundCriterionService.getAllRoundCriterion(roundId), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Lấy chi tiết một tiêu chí trong đợt đánh giá
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<RoundCriterionResponse>> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(
            new APIResponse<>(
                true, "Lấy chi tiết một tiêu chí trong đợt đánh giá", roundCriterionService.getByIdRoundCriterion(id),
                    HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Thêm một tiêu chí vào đợt đánh giá với trọng số
    @PostMapping
    public ResponseEntity<APIResponse<RoundCriterionResponse>> create(@RequestBody @Valid RoundCriterionRequest dto) {
        return new ResponseEntity<>(
            new APIResponse<>(
                true, "Thêm một tiêu chí vào đợt đánh giá với trọng số", roundCriterionService.createRoundCriterion(dto),
                    HttpStatus.CREATED, null, LocalDateTime.now()), HttpStatus.CREATED);
    }

    //Cập nhật trọng số của tiêu chí trong đợt đánh giá
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<RoundCriterionResponse>> update(@PathVariable Integer id, @RequestBody @Valid RoundCriterionRequest dto) {
        return new ResponseEntity<>(
            new APIResponse<>(
                true,  "Cập nhật trọng số của tiêu chí trong đợt đánh giá", roundCriterionService.updateRoundCriterion(id, dto),
               HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    // Xóa một tiêu chí khỏi đợt đánh giá
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Integer id) {
        roundCriterionService.deleteRoundCriterion(id);
        APIResponse<String> response = new APIResponse<>(
                true, "Xóa một tiêu chí khỏi đợt đánh giá thành công", null, HttpStatus.OK, null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}