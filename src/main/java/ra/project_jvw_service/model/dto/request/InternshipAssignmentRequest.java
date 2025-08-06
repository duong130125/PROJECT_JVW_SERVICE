package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InternshipAssignmentRequest {
    @NotNull(message = "Student ID không được để trống")
    private Integer studentId;
    @NotNull(message = "Mentor ID không được để trống")
    private Long mentorId;
    @NotNull(message = "Phase ID không được để trống")
    private Long phaseId;
}
