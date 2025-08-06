package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ra.project_jvw_service.utils.AssignmentStatus;

@Data
public class InternshipAssignmentStatusUpdateRequest {
    @NotNull
    private AssignmentStatus status;
}
