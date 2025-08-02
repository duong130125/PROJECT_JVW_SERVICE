package ra.project_jvw_service.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorResponse {
    private Integer mentorId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String department;
    private String academicRank;
}
