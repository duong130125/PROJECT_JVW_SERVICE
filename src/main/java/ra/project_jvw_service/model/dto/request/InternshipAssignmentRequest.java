package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InternshipAssignmentRequest {
    @NotNull
    private Integer studentId;
    @NotNull
    private Long mentorId;
    @NotNull
    private Long phaseId;
}
