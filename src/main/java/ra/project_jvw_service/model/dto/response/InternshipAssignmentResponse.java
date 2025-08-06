package ra.project_jvw_service.model.dto.response;

import lombok.Builder;
import lombok.Data;
import ra.project_jvw_service.utils.AssignmentStatus;

@Data
@Builder
public class InternshipAssignmentResponse {
    private Integer assignmentId;
    private Integer studentId;
    private String studentCode;
    private Integer mentorId;
    private String department;
    private String academicRank;
    private Integer phaseId;
    private String phaseName;
    private AssignmentStatus status;
}