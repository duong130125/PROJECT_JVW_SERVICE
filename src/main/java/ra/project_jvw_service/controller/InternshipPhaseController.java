package ra.project_jvw_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_jvw_service.model.dto.request.InternshipPhaseRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.InternshipPhaseResponse;
import ra.project_jvw_service.service.InternshipPhaseService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/internship_phases")
public class InternshipPhaseController {
    @Autowired
    private InternshipPhaseService internshipPhaseService;

    //Lấy danh sách tất cả các giai đoạn thực tập
    @GetMapping
    public ResponseEntity<APIResponse<List<InternshipPhaseResponse>>> getAll() {
        return new ResponseEntity<>(
            new APIResponse<>(
                true, "Lấy danh sách tất cả các giai đoạn thực tập", internshipPhaseService.getAllPhases(), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Lấy thông tin chi tiết một giai đoạn thực tập theo ID
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<InternshipPhaseResponse>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
            new APIResponse<>(
                true, "Lấy thông tin chi tiết một giai đoạn thực tập theo ID", internshipPhaseService.getPhaseById(id), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Tạo một giai đoạn thực tập mới
    @PostMapping
    public ResponseEntity<APIResponse<InternshipPhaseResponse>> create(@RequestBody @Valid InternshipPhaseRequest dto) {
        return new ResponseEntity<>(
            new APIResponse<>(
                true, "Tạo một giai đoạn thực tập mới thành công", internshipPhaseService.createPhase(dto), HttpStatus.CREATED, null, LocalDateTime.now()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<InternshipPhaseResponse>> update(@PathVariable Long id, @RequestBody @Valid InternshipPhaseRequest dto) {
        return new ResponseEntity<>(
            new APIResponse<>(
                true, "Cập nhật thông tin một giai đoạn thực tập thành công", internshipPhaseService.updatePhase(id, dto), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Long id) {
        internshipPhaseService.deletePhase(id);
        APIResponse<String> response = new APIResponse<>(
            true, "Xóa giai đoạn thực tập thành công", null, HttpStatus.OK, null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
