package ra.project_jvw_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.MentorResponse;
import ra.project_jvw_service.service.MentorService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    //Lấy danh sách tất cả giáo viên hướng dẫn (STUDENT xem thông tin chung)
    @GetMapping
    public ResponseEntity<APIResponse<List<MentorResponse>>> getAllMentors(Authentication authentication) {
        return new ResponseEntity<>(
            new APIResponse<>(true, "Lấy danh sách tất cả giáo viên hướng dẫn ", mentorService.getAllMentors(authentication), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Lấy thông tin chi tiết một giáo viên hướng dẫn theo ID (MENTOR chỉ xem của mình)
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<MentorResponse>> getMentorById(@PathVariable Long id, Authentication authentication) {
        return new ResponseEntity<>(
            new APIResponse<>(true, "Lấy thông tin chi tiết một giáo viên hướng dẫn theo ID", mentorService.getMentorById(id, authentication), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }
}
